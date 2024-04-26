const url = new URL(window.location.href)
const urlParams = url.searchParams;
var listTitle;

const getListUrl = "/wiki/listTitle?studyNo=" + urlParams.get('studyNo');
//TODO: mapping
const updateListOrderUrl = "/wiki/updateListOrder?studyNo=" + urlParams.get(
    'studyNo');
var updateListTextUrl;
var updateContentUrl;
var deleteWikiUrl;
var addWikiUrl;

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
});

$('#addNode').on('click', function(e) {
  fetch(addWikiUrl, {
    method: 'POST',
  })
})

function addWiki () {
  //비동기로 위키를 추가 시킨 다음에 wikiNo를 리턴받자.
  //이건 동기적으로 실행되어야 하므로,
  //then 으로 계속 메서드 체이닝 해야 한다.

  //비동기 위키 추가. 선택한 노드와 동일 위치에 만들어준다. (parentNo 결정가능)
  //then. 받은 winkNo로 jstree에 노드 추가해준다.



}









//
// //Configuration for jstree
// getList(getListUrl);
//
// //get list json data
// function getList(url) {
//   return fetch(url, {
//     method: 'GET',
//     headers: {'Content-Type': 'application/json'}
//   }).then(function (res) {
//     if (!res.ok) {
//       throw new Error('fetch failed!');
//     }
//     return res.json();
//   })
//   .then(data => {
//     data.forEach(item => {
//       if (item.parent == 0) {
//         //make it root
//         item.parent = '#';
//       }
//     })
//     console.log(data)
//     return data;
//   })
// }
//
// //
// function parseParentId(json) {
//
// }
//
// //make jstree


