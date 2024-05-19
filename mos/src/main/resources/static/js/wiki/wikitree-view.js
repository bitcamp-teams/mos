//RESTful
const tree = $('#wikiTree');
var url = new URL(window.location.href)
var urlParams = url.searchParams;
var studyNo = urlParams.get('studyNo');
var wikiNo = urlParams.get('wikiNo');

if (wikiNo == null) {
  $('#contentEditLink').attr('href',
      '/wiki/edit?studyNo=' + studyNo);
}

const getListUrl = "/api/wiki?studyNo=" + studyNo;
const updateListOrderUrl = "/api/wiki";
const patchUrl = "/api/wiki"
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
    "plugins": ['types', 'unique',
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
    //console.log(e, data);
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
      //data-th-src="@{'https://kr.object.ncloudstorage.com/mos/member/' + *{photo}}"

      //https://4l8fsxs62676.edge.naverncp.com/hudgg8JEM5/member/e9514b16-acd7-4d0b-811d-bf30de70ac3f?type=f&w=80&h=80&ttype=jpg

      //https://4l8fsxs62676.edge.naverncp.com/hudgg8JEM5/member/55c69e50-1284-4364-a306-0a2d6eb9118b?type=f&w=80&h=80&ttype=jpg


      let profileImageUrl ;
          if(nodeContent.photo==null) {
            profileImageUrl = '/img/user2-160x160.jpg';
          } else {
            profileImageUrl = 'https://kr.object.ncloudstorage.com/mos/member/' + nodeContent.photo;
          }
      $('#profile-image').attr('src', profileImageUrl);
      $('#author').html(nodeContent.username);
      $('#title').html(nodeContent.title);
      $('#contentEditLink').attr('href',
          '/wiki/edit?studyNo=' + studyNo + '&wikiNo=' + nodeContent.wikiNo);
      $('#content').attr('content', nodeContent.content);
      $('#contentLikes').html(nodeContent.likes);
      $('#createdDate').html(nodeContent.createdDate);
      isLiked(nodeContent.wikiNo);
      return nodeContent;
    })
    .then(function (nodeContent) {
      if (nodeContent.content == null) {
        nodeContent.content = '아직 작성되지 않은 위키입니다.';
      }
      const {Editor} = toastui;
      const {codeSyntaxHighlight} = Editor.plugin;
      const viewer = Editor.factory({
        el: document.querySelector('#viewer'),
        viewer: true,
        initialValue: nodeContent.content,
        plugins: [[codeSyntaxHighlight, {highlighter: Prism}]],
      });
      history.pushState(null, null,
          '/wiki/view?studyNo=' + nodeContent.studyNo + '&wikiNo='
          + nodeContent.wikiNo);
      refreshUrl();

      return viewer;
    })
    .then(function (viewer) {
      //프로미스가 이행되었다면 ToC를 생성한다.
      // let tocTarget = $('.toastui-editor-contents');
      let tocTarget = $('#viewer');

      //ToC를 만들 객체를 선택한다. id=toc
      var navSelector = '#toc';
      var $myNav = $(navSelector);
      //선택한 객체에 ToC가 생성되어 있을 수 있으므로 초기화한다.
      $myNav.html("");

      //ToC를 만든다.
      Toc.init($myNav);

      //스크롤링 감지할 수 있도록 속성값을 넣어준다.
      // tocTarget.attr("data-spy", "scroll")
      // tocTarget.attr("data-target", "#toc")
      var body = $('body');
      body.attr("data-spy", "scroll");
      body.attr("data-target", "#toc")

      $(body).scrollspy({
        target: $myNav,
      });
    })
    .then(function () {
      getComments();
    })
  })
  .on('ready.jstree', function (e, data) {
    if (wikiNo != undefined) {
      // tree.jstree('deselect_all');
      tree.jstree('select_node', wikiNo);
    }
  });
});

// 좋아요 상태 가져오기
function isLiked(wikiNo) {
  fetch('/api/wiki/isLiked?wikiNo=' + wikiNo)
  .then(response => response.json())
  .then(isLiked => {
    const heartIcon = document.querySelector('.fas.fa-heart');

    // isLiked 값에 따라 초기 스타일 설정
    if (isLiked === 1) {
      heartIcon.classList.add('liked');
    } else {
      heartIcon.classList.remove('liked');
    }

  })
  .catch(error => {
    console.error('Error fetching isLiked:', error);
  });
}

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

function toggleLike(element) {
  const isLiked = element.classList.contains('liked');

  fetch('/wiki/like/toggleLike', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({wikiNo: wikiNo})
  })
  .then(response => {
    if (!response.ok) {
      if (response.status === 400) {
        // Bad Request 에러 처리
        window.location.href = '/auth/login'; // 로그인 페이지로 리다이렉트
      } else {
        throw new Error('Network response was not ok');
      }
    }
    return response.json();
  })
  .then(data => {
    // 서버 응답에 따른 처리
    console.log(data.message);

    // isLiked 값에 따라 하트 아이콘 스타일 변경
    if (isLiked) {
      element.classList.remove('liked');
    } else {
      element.classList.add('liked');
    }

    // 좋아요 수 업데이트
    $('#contentLikes').html(data.likesCount); // 좋아요 수 업데이트

    // 여기서 좋아요 수가 실시간으로 반영되었음을 확인할 수 있습니다.
  })
  .catch(error => {
    console.error('Error toggling like:', error);
  });
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