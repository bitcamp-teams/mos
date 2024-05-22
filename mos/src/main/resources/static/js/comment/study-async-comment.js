// asyncComment

// api 서버에 요청/응답 받아서 class="commentList" 만들기
function getComments() {
  let targetElement = $('.commentList');
  targetElement.html("");
  let apiUrl = '/api/comment/study/' + studyNoDeleted;

  $.ajax({
    method: 'GET',
    url: apiUrl,
    success: function (result) {

      // 받은 리스트에서 구조화를 client side에서 실행한다.
      const hierarchyComments = buildHierarchy(result);
      const flattenedComments = flattenHierarchy(hierarchyComments);

      flattenedComments.forEach(function (res) {

        let commentElement = $('<div>').addClass('card');
        if (res.parentCommentNo != 0) {
          commentElement.addClass('reply');
        }

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

        // console.log(loginUser);
        // 로그인한 사용자와 댓글 작성자가 같은 경우에만 수정/삭제 버튼 표시

        if (loginUser != null && (loginUser.userName === res.username)) {
          headerRight.append(editButton, deleteButton);
        }

        headerRight.append(replyButton);

        commentHeader.append(headerLeft, headerRight);
        commentBody.addClass('bg-light border-light shadow-sm p-3');

        commentElement.append(commentHeader, commentBody);
        targetElement.append(commentElement);

        editButton.click(function () {
          // 편집 버튼 숨기기
          editButton.css('display', 'none');
          let commentNo = $(this).attr('comment-no');

          // 댓글 내용을 textarea로 변경
          let textarea = $('<textarea>').addClass('form-control').val(
              res.content);
          commentBody.html(textarea);

          // 저장 버튼 추가
          let saveButton = $('<button>').addClass(
              'save-btn btn btn-sm btn-primary m-1').text('저장');
          headerRight.append(saveButton);

          // 저장 버튼 클릭 이벤트 처리
          saveButton.click(function () {
            // 수정된 내용 저장 로직 구현
            let updatedContent = textarea.val();
            let updatedComment = {
              commentNo: Number(commentNo),
              content: updatedContent,
            }
            $.ajax({
              method: 'PATCH',
              url: '/api/comment/study/',
              contentType: 'application/json',
              data: JSON.stringify(updatedComment),
              success: function (res) {
                commentBody.text(updatedContent);
                headerRight.find('.save-btn').remove();
              },
              error: function (err) {
                Swal.fire('수정에 문제가 있습니다.');
              }
            })

            // 편집 버튼 다시 표시
            editButton.css('display', 'inline-block');
          });
        });

        // 삭제 버튼 클릭 이벤트 처리
        deleteButton.click(function () {
          let commentNo = $(this).attr('comment-no'); // 댓글 고유 번호 가져오기
          console.log(commentNo);
          Swal.fire({
            title: '댓글 삭제',
            text: '정말 삭제하시겠습니까?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#d33',
            cancelButtonColor: '#3085d6',
            confirmButtonText: '삭제',
            cancelButtonText: '취소'
          }).then((result) => {
            if (result.isConfirmed) {
              // 서버로 삭제 요청 보내기
              $.ajax({
                method: 'DELETE',
                url: '/api/comment/study/' + commentNo,
                success: function (result) {
                  // 화면에서 해당 댓글 요소 제거
                  commentElement.remove();
                  Swal.fire(
                      '삭제 완료',
                      '댓글이 성공적으로 삭제되었습니다.',
                      'success'
                  );
                },
                error: function (xhr, status, error) {
                  console.log(error);
                  Swal.fire(
                      '삭제 실패',
                      '댓글 삭제에 실패했습니다.',
                      'error'
                  );
                }
              });
            }
          });
        });

        // 대댓글 버튼 클릭 이벤트 처리
        replyButton.click(function () {
          // 버튼 감추기
          replyButton.css('display', 'none');

          //속성값 위임전에 미리 뽑아두기
          let commentNo = $(this).attr('comment-no');

          // 대댓글 입력 폼 생성
          let replyForm = $('<div>').addClass('reply-form my-3');
          let replyTextarea = $('<textarea>').addClass('form-control mb-2');
          let replySubmitBtn = $('<button>').addClass(
              'btn btn-primary btn-sm').text('대댓글 작성');

          replyForm.append(replyTextarea, replySubmitBtn);
          commentElement.append(replyForm);

          // 대댓글 작성 버튼 클릭 이벤트 처리
          replySubmitBtn.click(function () {
            let replyContent = replyTextarea.val();
            // 서버로 대댓글 작성 요청 보내기
            $.ajax({
              method: 'POST',
              url: '/api/comment/study',
              contentType: 'application/json',
              data: JSON.stringify({
                memberNo: loginUser.memberNo,
                studyNo: studyNoDeleted,
                parentCommentNo: Number(commentNo),
                content: replyContent,

              }),
              success: function () {
                getComments(); // 댓글 리스트 갱신
              },
              error: function () {
                Swal.fire('뭔가 문제가 있어요...');
              }

            })

            // 대댓글 입력 폼 제거
            replyForm.remove();
          });
        });

      });
    },
    error: function (xhr, status, error) {
      console.log(error);
    }
  });
}

//댓글 입력 폼 생성하고 submit 동작 정의하기
// 댓글 등록 버튼 클릭 이벤트 핸들러
$('#submitComment').click(function () {
  let content = $('#commentContent').val().trim();
  if (loginUser === undefined || loginUser === null) {
    Swal.fire('댓글은 로그인 하셔야 작성할 수 있어요!');
    return;
  }
  if (content === '') {
    Swal.fire('댓글 내용을 입력해주세요.');
    return;
  }

  let commentData = {
    memberNo: Number(loginUser.memberNo),
    studyNo: Number(studyNoDeleted),
    content: content,
    notiReceiver: $('#notiReceiver').val(),
    title: $('#studyTitle').text(),
  };

  console.log(commentData);

  $.ajax({
    type: 'POST',
    url: '/api/comment/study',
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

// 계층 구조로 변환하는 함수
function buildHierarchy(comments) {
  const hierarchy = [];
  const childrenMap = new Map();

  // 각 댓글을 parentCommentNo를 키로 하여 맵에 저장
  for (const comment of comments) {
    childrenMap.set(comment.commentNo, []);
    const parent = comments.find(c => c.commentNo === comment.parentCommentNo);
    if (parent) {
      const children = childrenMap.get(parent.commentNo);
      children.push(comment);
    } else {
      hierarchy.push(comment);
    }
  }

  // 재귀 함수로 계층 구조 생성
  function buildChildren(comment) {
    const children = childrenMap.get(comment.commentNo);
    if (children) {
      comment.children = children.map(child => {
        return buildChildren(child);
      });
    }
    return comment;
  }

  return hierarchy.map(comment => buildChildren(comment));
}

// 계층 구조를 평평하게 펼치는 함수
function flattenHierarchy(hierarchy) {
  const flattenedComments = [];

  function flattenChildren(comments, level) {
    for (const comment of comments) {
      flattenedComments.push({...comment, level});
      if (comment.children) {
        flattenChildren(comment.children, level + 1);
      }
    }
  }

  flattenChildren(hierarchy, 0);
  return flattenedComments;
}

getComments();
