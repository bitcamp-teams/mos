// asyncComment

// api 서버에 요청/응답 받아서 class="commentList" 만들기
function getComments() {
  let targetElement = $('.commentList');
  targetElement.html("");
  let apiUrl = '/api/comment/wiki/' + wikiNo;

  $.ajax({
    method: 'GET',
    url: apiUrl,
    success: function (result) {
      result.forEach(function (res) {
        let commentElement = $('<div>').addClass('card');
        let commentHeader = $('<div>').addClass(
            'card-header d-flex justify-content-between');
        let commentBody = $('<div>').addClass('card-body').text(res.content);

        let username = res.username || '비회원';
        let date = new Date(res.createdDate);

        const options = {
          year: 'numeric',
          month: 'long',
          day: 'numeric',
          hour: 'numeric',
          minute: 'numeric',
          second: 'numeric'
        };
        const formattedDate = date.toLocaleString('ko-KR', options);

        let headerLeft = $('<div>').addClass('font-weight-bold').text(username);
        let headerRight = $('<div>').addClass('text-muted').text(formattedDate);

        // 수정, 삭제, 답글 버튼 추가하고, 속성으로 commentNo를 붙여준다.
        let editButton = $('<button>').addClass(
            'edit-btn btn btn-sm btn-outline-secondary m-1')
        .html('<i class="fas fa-edit"></i>').attr('comment-no', res.commentNo);
        let deleteButton = $('<button>').addClass(
            'delete-btn btn btn-sm btn-outline-danger m-1')
        .html('<i class="fas fa-trash-alt"></i>').attr('comment-no',
            res.commentNo);
        let replyButton = $('<button>').addClass(
            'reply-btn btn btn-sm btn-outline-primary m-1')
        .html('<i class="fas fa-reply"></i>').attr('comment-no', res.commentNo);

        console.log(loginUser);
        // 로그인한 사용자와 댓글 작성자가 같은 경우에만 수정/삭제 버튼 표시
        // TODO : 댓글에서 대댓글, 수정, 삭제 기능 구현
        // if (loginUser.userName === res.username) {
        //   headerRight.append(editButton, deleteButton);
        // }
        // headerRight.append(replyButton);

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
  // 댓글 등록 버튼 클릭 이벤트 핸들러
  $('#submitComment').click(function () {
    let content = $('#commentContent').val().trim();
    if(loginUser === undefined || loginUser === null) {
      Swal.fire('댓글은 로그인 하셔야 작성할 수 있어요!');
      return;
    }
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

// 수정, 삭제, 답글에 대한 이벤트 리스너 설정하기
// 수정
// 수정 버튼 클릭 이벤트 핸들러
$(document).ready(function () {
  $('.edit-btn').on('click', function () {
    console.log('hihi');
    const commentId = $(this).data('comment-no');
    const commentDiv = $(`div[data-comment-id="${commentId}"]`);
    const commentText = commentDiv.text();
    console.log('edit-btn clicked, the id is '+commentId);

    // div 태그를 textarea로 변경
    const textarea = $('<textarea>').addClass('form-control').val(commentText);
    commentDiv.replaceWith(textarea);

    // 저장 버튼 추가
    const saveButton = $('<button>').addClass('btn btn-sm btn-success ml-1')
    .html('<i class="fas fa-save"></i> 저장')
    .on('click', function () {
      const updatedText = textarea.val();
      const updatedComment = $('<div>').text(updatedText).attr('data-comment-id',
          commentId);
      textarea.replaceWith(updatedComment);

      // 수정, 삭제, 답글 버튼 다시 추가
      const editButton = $('<button>').addClass(
          'btn btn-sm btn-outline-secondary mr-1 edit')
      .html('<i class="fas fa-edit"></i>')
      .attr('data-comment-id', commentId);
      const deleteButton = $('<button>').addClass(
          'btn btn-sm btn-outline-danger mr-1')
      .html('<i class="fas fa-trash-alt"></i>')
      .attr('data-comment-id', commentId);
      const replyButton = $('<button>').addClass('btn btn-sm btn-outline-primary')
      .html('<i class="fas fa-reply"></i>')
      .attr('data-comment-id', commentId);

      updatedComment.append(editButton, deleteButton, replyButton);
    });

    commentDiv.after(saveButton);
  });

  $('.delete-btn').on('click', function () {
    const commentNo = $(this).data('commentNo');
    console.log('delete')
  })

  $('.reply-btn').on('click', function () {
    const commentNo = $(this).data('commentNo');
    console.log('reply')
  })
})
