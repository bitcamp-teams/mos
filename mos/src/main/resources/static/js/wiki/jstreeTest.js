//RESTful
const url = new URL(window.location.href)
const urlParams = url.searchParams;
const getListUrl = "/api/wiki?studyNo=" + urlParams.get('studyNo');
const updateListOrderUrl = "/api/wiki";
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
//여기서부터 차례로 이벤트 리스너를 등록한다.
//클릭으로 인한 선택 등, select 대상이 변경되었을 때 발생하는 이벤트
.on('changed.jstree', function (e, data) {
  console.log(e, data);
})
//이동이 완료되었을 때 move_node 이벤트가 발생한다.
//potion, old_position을 반환하므로 순서를 DB에 저장할 수 있다.
.on('move_node.jstree', function (e, data) {
  console.log(e, data);
})
//rename_node는 노드의 이름을 변경했을 때(text 변경 시) 발생하는 이벤트다.
.on('rename_node.jstree', function (e, data) {
  console.log(e, data);
})
//새로 노드를 만들었을 때, create_node 이벤트가 발생한다.
.on('create_node.jstree', function (e, data) {
  console.log(e, data);
  $.post('/api/wiki', function (data) {

  })

});

//#addNode btn에 대해, 선택된 node와 같은 depth에 node를 추가하고,
//해당 노드의 정보를 가지고 DB에 추가를 요청한다.
$('#add').on('click', function (e, data) {
  console.log(e, data);
  var selectedNodeIds = $('#wikiTree').jstree('get_selected', true);
  var singleSelectedNodeId = selectedNodeIds[0];
  console.log(singleSelectedNodeId);
  $.ajax({
        method: 'post',
        url: '/api/wiki',
        contentType: 'application/json',
        data: JSON.stringify(
            {"studyNo": 1, "parent": 63, "text": "새로운 아이템 추가!", "position": "3"}),
        success: function (res) {
          //성공적으로 응답을 받았을 때
          console.log(res);
          console.log('success');
        },
        //에러 처리가 필요하면 여기서
        error: function (res) {
          console.log(res);
        }
      }
  );
});

$('#patch').on('click', function (e, data) {
  console.log('내가 버튼을 눌렀다!');
  console.log(e, data);
  $.ajax({
        method: 'patch',
        url: '/api/wiki',
        data: {id: "1", parent: "#"},
        success: function (res) {
          //성공적으로 응답을 받았을 때
          console.log(res);
          console.log('success');
        },
        //에러 처리가 필요하면 여기서
        error: function (res) {
          console.log(res);
        }
      }
  );
});

$('#delete').on('click', function (e, data) {
  console.log('내가 버튼을 눌렀다!');
  console.log(e, data);
  $.ajax({
        method: 'delete',
        url: '/api/wiki',
        data: {id: "1", parent: "#"},
        success: function (res) {
          //성공적으로 응답을 받았을 때
          console.log(res);
          console.log('success');
        },
        //에러 처리가 필요하면 여기서
        error: function (res) {
          console.log(res);
        }
      }
  );
});
