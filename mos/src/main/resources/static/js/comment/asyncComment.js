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

//댓글 입력 폼 생성하고 submit 동작 정의하기
function createComment() {
  // TODO: 로그인 여부 확인

  // 댓글 등록 버튼 클릭 이벤트 핸들러
  $('#submitComment').click(function () {
    let content = $('#commentContent').val().trim();
    if (content === '') {
      Swal.fire('댓글 내용을 입력해주세요.');
      return;
    }

    let data = {
      memberNo: 32,
      wikiNo: wikiNo,
      content: content
    };

    $.ajax({
      type: 'POST',
      url: '/api/comment/wiki',
      data: JSON.stringify(data),
      contentType: 'application/json',
      success: function (response) {
        // 성공적으로 댓글이 등록된 경우
        $('#commentContent').val(''); // 입력칸 초기화
        getComments(); // 댓글 리스트 갱신
      },
      error: function (xhr, status, error) {
        console.log(error);
        alert('댓글 등록에 실패했습니다.');
      }
    });
  });
}