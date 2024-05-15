// asyncComment

// api 서버에 요청/응답 받아서 class="commentList" 만들기
function getComments() {
  let targetElement = $('.commentList');
  targetElement.html("");
  let apiUrl = '/api/comment/wiki/' + wikiNo;

  $.ajax({
    method: 'GET',
    url: apiUrl,
    success: function(result) {
      result.forEach(function(res) {
        let commentElement = $('<div>').addClass('card');
        let commentHeader = $('<div>').addClass('card-header d-flex justify-content-between');
        let commentBody = $('<div>').addClass('card-body').text(res.content);

        let username = res.username || '비회원';
        let date = new Date(res.createdDate);

        const options = { year: 'numeric', month: 'long', day: 'numeric', hour: 'numeric', minute: 'numeric', second: 'numeric' };
        const formattedDate = date.toLocaleString('ko-KR', options);

        let headerLeft = $('<div>').addClass('font-weight-bold').text(username);
        let headerRight = $('<div>').addClass('text-muted').text(formattedDate);

        // 수정 및 삭제 버튼 추가
        let editButton = $('<button>').addClass('btn btn-sm btn-outline-secondary mr-1')
        .html('<i class="fas fa-edit"></i>');
        let deleteButton = $('<button>').addClass('btn btn-sm btn-outline-danger')
        .html('<i class="fas fa-trash-alt"></i>');
        let replyButton = $('<button>').addClass('btn btn-sm btn-outline-primary')
        .html('<i class="fas fa-reply"></i>');

        console.log(loginUser);
        // 로그인한 사용자와 댓글 작성자가 같은 경우에만 수정/삭제 버튼 표시
        if (loginUser.userName === res.username) {
          headerRight.append(editButton, deleteButton);
        }
        headerRight.append(replyButton);

        commentHeader.append(headerLeft, headerRight);
        commentBody.addClass('bg-light border-light shadow-sm p-3');

        commentElement.append(commentHeader, commentBody);
        targetElement.append(commentElement);
      });
    },
    error: function(xhr, status, error) {
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

    let commentData = {
      memberNo: Number(loginUser.memberNo),
      wikiNo: Number(wikiNo),
      content: content
    };

    console.log(commentData);

    $.ajax({
      type: 'POST',
      url: '/api/comment/wiki',
      data: JSON.stringify(commentData),
      contentType: 'application/json',
      success: function (response) {
        // 성공적으로 댓글이 등록된 경우
        $('#commentContent').val(''); // 입력칸 초기화
        getComments(); // 댓글 리스트 갱신
      },
      error: function (xhr, status, error) {
        console.log(error);
        Swal.fire('댓글 등록에 실패했습니다.');
      }
    });
  });
}