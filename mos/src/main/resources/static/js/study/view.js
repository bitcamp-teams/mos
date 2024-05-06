//스터디 신청 버튼에 대한 이벤트 리스너
$('#submitApplyBtn').click(function() {
  var applyMsg = $('#applyMsg').val();
  var studyNo = $('#studyNo').val();

  // AJAX 요청을 통해 서버로 데이터 전송
  $.ajax({
    url: '/study/applyStudy', // 수정된 URL 입력
    type: 'POST',
    data: {
      applyMsg: applyMsg,
      studyNo: studyNo
    },
    success: function(response) {
      // 성공적으로 처리된 경우
      console.log(response);
      $('#applyModal').modal('hide'); // 모달창 닫기
      // 필요한 경우 추가 작업 수행
    },
    error: function(error) {
      // 오류 발생 시 처리
      console.error(error);
    }
  });
});

//toast ui - viewer 초기화
var content = document.querySelector('[introduction]').getAttribute('introduction');
const viewer = toastui.Editor.factory({
  el: document.querySelector('#viewer'),
  viewer: true,
  initialValue: content
});
//----------------------------------------------------------
//댓글관련
document.addEventListener('DOMContentLoaded', function () {
  document.getElementById('submitBtn').addEventListener('click', function (e) {
    e.preventDefault(); // 기본 폼 제출 방지

    // 댓글 폼 데이터 수집
    var commentForm = document.getElementById('commentForm');
    var formData = new FormData(commentForm);

    // 댓글 데이터 서버에 전송
    fetch(commentForm.getAttribute('action'), {
      method: 'POST',
      body: formData
    })
    .then(response => {
      if (response.ok) {
        return response.json();
      } else {
        throw new Error('댓글 등록 실패');
      }
    })
    .then(data => {
      console.log('댓글 등록 성공:', data);
      // window.location.reload(); // 페이지 새로고침
    })
  });
});

function editComment(element, commentNo, studyNo) {
  var commentContent = document.getElementById('comment-' + commentNo);
  var commentInput = document.getElementById('comment-input-' + commentNo);
  var buttonGroup = document.getElementById('button-group-' + commentNo);

  commentContent.style.display = 'none';
  commentInput.style.display = 'inline';
  buttonGroup.style.display = 'inline';
}

function saveComment(commentNo, studyNo) {
  var commentInput = document.getElementById('comment-input-' + commentNo);
  var newContent = commentInput.value;

  // TODO: 서버에 업데이트 요청 보내기
  // ...

  location.reload(); // 페이지 새로고침
}

function cancelEdit(commentNo) {
  var commentContent = document.getElementById('comment-' + commentNo);
  var commentInput = document.getElementById('comment-input-' + commentNo);
  var buttonGroup = document.getElementById('button-group-' + commentNo);

  commentContent.style.display = 'inline';
  commentInput.style.display = 'none';
  buttonGroup.style.display = 'none';
}