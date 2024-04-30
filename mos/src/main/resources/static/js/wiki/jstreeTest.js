//RESTful
const url = new URL(window.location.href)
const urlParams = url.searchParams;
const studyNo = urlParams.get('studyNo');
const getListUrl = "/api/wiki?studyNo=" + studyNo;
const updateListOrderUrl = "/api/wiki";
const patchUrl = "/api/wiki"
var count = 1;
var updateListTextUrl = "/api/wiki";
var updateContentUrl = "/api/wiki";
var deleteWikiUrl = "/api/wiki";
var addWikiUrl = "/api/wiki";

var tree = $('#wikiTree');
tree.jstree({
  core: {
    multiple: true,
    themes: {
      "responsive": true,
      "variant": "large",

    },
    check_callback: true,
    data: {
      //이렇게 하면 알아서 json 을 ajax로 가져와서 data로 쓴다.
      url: getListUrl,
      dataType: "json"
    }
  },
  plugins: ['wholerow', 'state', 'dnd', 'types', 'contextmenu', 'unique']
  //순서 저장하는 기능을 'sort' 플러그인으로 구현 가능할지도..
})
.on('delete_node.jstree', function (e, data) {
  console.log(e, data);
  deleteSingleNode(data);
})
//여기서부터 차례로 이벤트 리스너를 등록한다.
//클릭으로 인한 선택 등, select 대상이 변경되었을 때 발생하는 이벤트
.on('changed.jstree', function (e, data) {
  console.log(e, data);
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
});

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
        contentType: 'application/json',
        data: node,
        success: function (res) {
          // 변경은 DB 정합성이 유지된다.
        },
        error: function (res) {
          // 문제가 발생한 경우에만 데이터 동기가 깨진 것이므로 트리를 다시 그린다.
          window.alert('오류가 발생했습니다. 데이터를 저장하지 못했습니다.')
          console.log(res);
          tree.jstree('refresh');
        }
      }
  );
}

//최상단인 경우에는 wiki_no(id)를 받아와야 하므로 별도의 함수를 만들어야 함

function saveSingleNode(data) {
  let selectedNodeIds = $('#wikiTree').jstree('get_selected', true);
  let singleSelectedNodeId = selectedNodeIds[0];
  let node = JSON.stringify(
      {
        "studyNo": studyNo,
        //최상단 노드 # 인 경우 0으로 직렬화함
        "parent": singleSelectedNodeId.id === '#' ? 0
            : singleSelectedNodeId.id,
        "text": data.node.text,
        // "position":
        "position": data.node.position
      })
  console.log('saved node', singleSelectedNodeId);
  $.ajax({
        method: 'post',
        url: '/api/wiki',
        contentType: 'application/json',
        data: node,
        success: function (res) {
          //db에서 autoincrement된 id를 받아온다.
          tree.jstree('refresh');
        },
        error: function (res) {
          // 문제가 발생한 경우에만 데이터 동기가 깨진 것이므로 트리를 다시 그린다.
          window.alert('오류가 발생했습니다. 데이터를 저장하지 못했습니다.')
          console.log(res);
          tree.jstree('refresh');
        }
      }
  );
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
    question = data.node.text + " 노드는 " + data.node.children.length
        + "개의 자식이 있습니다.\n정말 삭제하시겠습니까?\n이 작업은 복구가 불가능합니다."
    confirm = window.confirm(question);
  } else {
    question = data.node.text + " 노드를 정말 삭제하시겠습니까?\n이 작업은 복구가 불가능합니다."
    confirm = window.confirm(question);
  }
  if (confirm) {
    $.ajax({
          method: 'DELETE',
          url: patchUrl,
          contentType: 'application/json',
          data: node,
          success: function (res) {
            // 변경은 DB 정합성이 유지된다.
          },
          error: function (res) {
            // 문제가 발생한 경우에만 데이터 동기가 깨진 것이므로 트리를 다시 그린다.
            window.alert('오류가 발생했습니다. 데이터를 저장하지 못했습니다.')
            console.log(res);
            tree.jstree('refresh');
          }
        }
    );
  } else {
    tree.jstree('refresh');
  }

}