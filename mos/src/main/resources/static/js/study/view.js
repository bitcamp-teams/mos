//스터디 신청 버튼에 대한 이벤트 리스너
$('#submitApplyBtn').click(function () {
  var applyMsg = $('#applyMsg').val();
  var studyNoAttr = $('#studyNo').val();

  // AJAX 요청을 통해 서버로 데이터 전송
  $.ajax({
    url: '/study/applyStudy', // 수정된 URL 입력
    type: 'POST',
    data: {
      applyMsg: applyMsg,
      studyNo: studyNoAttr
    },
    success: function (response) {
      // 성공적으로 처리된 경우
      console.log(response);
      $('#applyModal').modal('hide'); // 모달창 닫기
      Swal.fire({
        title: '스터디 신청이 완료 되었습니다!',
        text: '스터디장의 승인을 기다려주세요.',
        icon: 'success',
        confirmButtonText: 'OK'
      })
    },
    error: function (error) {
      // 오류 발생 시 처리
      console.error(error);
    }
  });
});

//toast ui - viewer 초기화
var content = document.querySelector('[introduction]').getAttribute(
    'introduction');
const viewer = toastui.Editor.factory({
  el: document.querySelector('#viewer'),
  viewer: true,
  initialValue: content
});