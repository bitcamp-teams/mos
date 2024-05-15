// asyncComment
// api 서버에 요청/응답 받아서 class="commentList" 만들기

function getComments() {
  let targetElement = $('.commentList');
  targetElement.html("");
  let apiUrl = '/api/comment/wiki/';
  let targetGroup = wikiNo;

  console.log(targetGroup);

  $.ajax({
    method: 'GET',
    url: apiUrl + targetGroup,
    success: function (result) {
      console.log(result);

      result.forEach(function (res) {
        let commentElement = $('<div>').addClass('card');
        let commentHeader = $('<div>').addClass(
            'card-header d-flex justify-content-between');
        let commentBody = $('<div>').addClass('card-body').text(res.content);

        let username = res.username || 'Anonymous';
        let createdDate = res.createdDate || 'Unknown';

        let headerLeft = $('<div>').text(username);
        let headerRight = $('<div>').addClass('text-muted').text(createdDate);

        commentHeader.append(headerLeft, headerRight);
        commentBody.addClass('bg-light border-light shadow-sm p-3');

        commentElement.append(commentHeader, commentBody);
        targetElement.append(commentElement);
      });
    },
    error: function (xhr, status, error) {
      console.log(error);
    }
  });
}