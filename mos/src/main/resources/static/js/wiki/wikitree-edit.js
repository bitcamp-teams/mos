//RESTful
const tree = $('#wikiTree');
var viewer;
var url = new URL(window.location.href)
var urlParams = url.searchParams;
var studyNo = urlParams.get('studyNo');
var wikiNo = urlParams.get('wikiNo');
const getListUrl = "/api/wiki?studyNo=" + studyNo;
const updateListOrderUrl = "/api/wiki";
const patchUrl = "/api/wiki"
const contentSaveUrl = "/api/wiki/content";
var count = 1;
var updateListTextUrl = "/api/wiki";
var updateContentUrl = "/api/wiki";
var deleteWikiUrl = "/api/wiki";
var addWikiUrl = "/api/wiki";
$(function () {
  tree.jstree({
    core: {
      "multiple": true,
      "animation": {
        "open": 100, // 열릴 때의 애니메이션 속도 (밀리초)
        "close": 100, // 닫힐 때의 애니메이션 속도 (밀리초)
        "easing": "swing" // 애니메이션 효과 설정
      },
      "themes": {
        "dots": true,
        "icons": false,
        "responsive": false,
        "variant": "large",
      },
      "check_callback": true,
      "data": {
        //이렇게 하면 알아서 json 을 ajax로 가져와서 data로 쓴다.
        "url": getListUrl,
        "dataType": "json"
      }
    },
    "types": {
      "default": {
        "icon": "fa fa-folder",
      }
    },
    "plugins": ['dnd', 'types', 'contextmenu', 'unique',
      'sort'],
    //순서 저장하는 기능을 'sort' 플러그인으로 구현 가능할지도..

    //우클릭 컨텍스트 메뉴에서 Edit(cut, copy) 기능은 표시 X
    "contextmenu": {
      'items': function (node) {
        var items = $.jstree.defaults.contextmenu.items();
        items.ccp = false;
        return items;
      }
    },
  })
  .on('delete_node.jstree', function (e, data) {
    console.log(e, data);
    deleteSingleNode(data);
  })
  //여기서부터 차례로 이벤트 리스너를 등록한다.
  //클릭으로 인한 선택 등, select 대상이 변경되었을 때 발생하는 이벤트
  .on('changed.jstree', function (e, data) {
    //기존 내역을 저장한다.
    //select_node 보다 먼저 실행되는 이벤트일 것...
    try {
      saveContent(viewer.getMarkdown())
    } catch (e) {
      //slightly quit...
    }

  })
  //이동이 완료되었을 때 move_node 이벤트가 발생한다.
  //potion, old_position을 반환하므로 순서를 DB에 저장할 수 있다.
  .on('move_node.jstree', function (e, data) {
    console.log(e, data);
    patchSingleNode(data);
  })
  //rename_node는 노드의 이름을 변경했을 때(text 변경 시) 발생하는 이벤트다.
  .on('rename_node.jstree', function (e, data) {
    console.log(e, data);
    patchSingleNode(data);
  })
  //새로 노드를 만들었을 때, create_node 이벤트가 발생한다.
  .on('create_node.jstree', function (e, data) {
    console.log(e, data);
    saveSingleNode(data);
  })
  //노드를 선택했을 때이다. 해당 id(wiki_no) 위키 정보를 요청하고 뷰어를 만든다.
  .on('select_node.jstree', function (e, data) {
    getNodeContent(data)
    .then(nodeContent => {
      // $('#author').html(nodeContent.username);
      // $('#title').html(nodeContent.title);
      $('#contentEditLink').attr('href',
          '/wiki/view?studyNo=' + studyNo + '&wikiNo=' + nodeContent.wikiNo);
      $('#content').attr('content', nodeContent.content);
      return nodeContent;
    })
    .then(function (nodeContent) {
      if (nodeContent.content == null) {
        nodeContent.content = '아직 작성되지 않은 위키입니다.';
      }
      const {Editor} = toastui;
      const {codeSyntaxHighlight} = Editor.plugin;
      viewer = Editor.factory({
        el: document.querySelector('#viewer'),
        //previewStyle: 'vertical',
        viewer: false,
        height: '100%',
        initialValue: nodeContent.content,
        plugins: [[codeSyntaxHighlight, {highlighter: Prism}]],
      });
      history.pushState(null, null,
          '/wiki/edit?studyNo=' + nodeContent.studyNo + '&wikiNo='
          + nodeContent.wikiNo);
      refreshUrl();

      // return viewer;
    })
  })
  .on('ready.jstree', function (e, data) {
    if (wikiNo != undefined) {
      // tree.jstree('deselect_all');
      tree.jstree('select_node', wikiNo);
    }
  });
});

//  사용되는 함수들
function getNodeContent(data) {
  return new Promise((resolve, reject) => {
    $.ajax({
      method: 'GET',
      url: "/api/wiki/" + data.node.id,
      contentType: "application/json; charset=utf-8",
      success: function (res) {
        resolve(res);
      },
      error: function (res) {
        Swal.fire('오류가 발생했습니다. 위키 정보를 불러오지 못했습니다.')
        reject(res);
      }
    });
  });
}

function patchSingleNode(data) {
  let node = JSON.stringify(
      {
        "studyNo": studyNo,
        "id": data.node.id,
        //최상단 노드 # 인 경우 0으로 직렬화함
        "parent": data.node.parent === '#' ? 0 : data.node.parent,
        "text": data.node.text,
        "position": data.node.position
      })
  $.ajax({
        method: 'PATCH',
        url: patchUrl,
        contentType: "application/json; charset=utf-8",
        data: node,
        success: function (res) {
          // 변경은 DB 정합성이 유지된다.
        },
        error: function (res) {
          // 문제가 발생한 경우에만 데이터 동기가 깨진 것이므로 트리를 다시 그린다.
          Swal.fire('오류가 발생했습니다. 권한있는 사용자로 로그인하셨나요?')
          console.log(res);
          tree.jstree('refresh');
        }
      }
  );
}

//최상단인 경우에는 wiki_no(id)를 받아와야 하므로 별도의 함수를 만들어야 함

function saveSingleNode(data) {
  // let selectedNodeIds = $('#wikiTree').jstree('get_selected', true);
  // let singleSelectedNodeId = selectedNodeIds[0];
  let node = JSON.stringify(
      {
        "studyNo": studyNo,
        //최상단 노드 # 인 경우 0으로 직렬화함
        "parent": data.node.parent === '#' ? 0
            : data.node.parent,
        "text": data.node.text,
        // "position":
        "position": data.node.position
      })

  //여기서 저장하고, refresh하고,
  $.ajax({
    method: 'post',
    url: '/api/wiki',
    contentType: 'application/json',
    data: node
  })
  .then(function (res) {
    //트리 다시 그림 (db에서 만들어진 wiki_no로 id를 갱신해야 하므로...)
    tree.jstree('refresh');
    console.log(res)
    // return tree.jstree('select_node', res.node.id); // 다른 비동기 작업 수행
  })
  .then(function (res) {
    //TODO 생성된 노드에 대해 이름 변경 모드에 진입함
    console.log("success!");
  })
  .catch(function (res) {
    // 문제가 발생한 경우에만 데이터 동기가 깨진 것이므로 트리를 다시 그린다.
    Swal.fire('😭오류가 발생했습니다.\n권한있는 사용자로 로그인 하셨나요?');
    console.log(res);
    tree.jstree('refresh');
  });
}

function deleteSingleNode(data) {
  let node = JSON.stringify(
      //지워졌을 때 같은 depth인 position 도 앞으로 다 땡겨야 한다..
      {
        "studyNo": studyNo,
        "id": data.node.id,
        //최상단 노드 # 인 경우 0으로 직렬화함
        "parent": data.node.parent === '#' ? 0 : data.node.parent,
        "text": data.node.text,
        "position": data.node.position
      })
  var confirm = false;

  if (data.node.children.length !== 0) {
    question = "정말 삭제하시겠습니까? [" + data.node.text + "] 위키는 "
        + data.node.children.length
        + "개의 하위 위키들이 있습니다.\n";

    Swal.fire({
      title: '삭제 경고!',
      text: question,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: '삭제 진행',
      cancelButtonText: '취소'
    }).then((result) => {
      if (result.isConfirmed) {
        // 확인 버튼 클릭 시 실행할 코드
        confirm = true;
        if (confirm) {
          $.ajax({
                method: 'DELETE',
                url: patchUrl,
                contentType: 'application/json',
                data: node,
                success: function (res) {
                  tree.jstree('refresh');
                },
                error: function (res) {
                  // 문제가 발생한 경우에만 데이터 동기가 깨진 것이므로 트리를 다시 그린다.
                  Swal.fire('😭오류가 발생했습니다.\n권한있는 사용자로 로그인 하셨나요?');
                  console.log(res);
                  tree.jstree('refresh');
                  tree.jstree('select_node', data.node.id);
                }
              }
          );
        }
      } else {
        // 취소 버튼 클릭 시 실행할 코드
        confirm = false;
        tree.jstree('refresh');
      }
    });
  } else {
    // question = data.node.text + " 노드를 정말 삭제하시겠습니까?\n이 작업은 복구가 불가능합니다."
    confirm = true;
  }

}

$('#addRootNode').on('click', function (e) {
  tree.jstree("create_node", '#');
});

function refreshUrl() {
  url = new URL(window.location.href)
  urlParams = url.searchParams;
  studyNo = urlParams.get('studyNo');
  wikiNo = urlParams.get('wikiNo');
}

//저장 관련 시작
//자동저장 (사용자 입력이 있는 경우에, 30초마다 실행됨.
let saveTimeout;
const saveInterval = 3000; // 30초

$('body').on('input', () => {
  if (!saveTimeout) {
    saveContent(viewer.getMarkdown());
  }
});

function saveContent(content) {
  clearTimeout(saveTimeout);
  saveTimeout = setTimeout(() => {
    saveTimeout = null; // 타이머 초기화
  }, saveInterval);
  //toastr 알림 위치 설정
  toastr.options = {
    "positionClass": "toast-bottom-right",
    "fadeIn": 300,
    "fadeOut": 1000,
    "timeOut": 1000,
  };

  $.ajax({
    method: 'patch',
    url: contentSaveUrl,
    contentType: 'application/json',
    data: JSON.stringify({
      wikiNo: wikiNo,
      content: content
    }),
    success: function () {
      $('#status').text('Content saved at ' + new Date().toLocaleTimeString());
      toastr.success('자동 저장 완료!');
    },
    error: function (xhr, status, error) {
      $('#status').text('Error saving content: ' + error);
      toastr.error('자동 저장에 실패했어요..😭');
    }
  })
}

//창이 닫히는 경우 저장하도록 함.
window.addEventListener('beforeunload', function (event) {
  //비동기로 저장하는거라 100% 성공한다는 보장은 없음..
  saveContent(viewer.getMarkdown());
  // event.preventDefault();
});

//저장 관련 끝