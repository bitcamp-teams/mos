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

//Configuration for jstree
var tree = $('#wikiTree');
console.log(typeof tree);
// tree = jQuery.jstree._reference("#wikiTree");
// console.log(typeof tree);
tree.jstree({
  "plugins": ["wholerow", "checkbox", "dnd"],
  'core': {
    "themes": {
      "variant": "large",
    },
    // "check_callback": true,
    "data": [
      {
        "id": "0", "parente": "#", "text": "로딩중입니다."
      }
    ]
  }
});

setTimeout(function () {
  fetch(getListUrl, {
    method: 'GET',
  })
  .then(response => response.json()) // 응답 데이터를 JSON으로 파싱
  .then(newData => {
    $('#wikiTree').jstree({
      'core': {
        'data': newData
      }
    });
    tree.redraw([full]);
    console.log("I recreated it!!");
  })
  .catch(error => {
    console.error('Error fetching data:', error);
    // 에러 처리 로직 추가
  });
}, 1000);

// $.jstree.defaults.core.themes.variant = "large";
// $.ajax({
//   url: getListUrl,
//   type: "GET",
//   dataType: "json",
//   success: function (result) {
//     listTitle = result;
//     // jstree 규칙 (최상단 노드는 #)
//     listTitle.forEach(function (item) {
//       if (item.parent === 0) {
//         item.parent = '#';
//       }
//     });
//     console.log(listTitle);
//
//   }
// })

