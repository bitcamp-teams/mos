// 로그인 상태일 때만 호출됨

let index = {
  memberNo: $('#isLoginFrm #memberNoInput').val(),
  init() {
    const _this = this;

    $(function () {
      if (localStorage.getItem('isSubscribed') === 'Y') {
        return; // 이미 구독중이면 중복 요청 안함
      }

      if (_this.memberNo !== '' && localStorage.getItem('isSubscribed')
          !== 'Y') {
        _this.subscribe();
      }
    });

  },
  subscribe() {
    console.log('sub!')
    const _this = this;
    const eventSource = new EventSource('/api/v1/subscribe/' + _this.memberNo);
    localStorage.setItem('isSubscribed', 'Y');

    eventSource.addEventListener("sse", event => {
      try {
        const data = JSON.parse(event.data);
        const Toast = Swal.mixin({
          toast: true,
          position: 'top',
          showConfirmButton: false,
          timer: 3000,
          timerProgressBar: true,
          didOpen: (toast) => {
            toast.addEventListener('mouseenter', Swal.stopTimer)
            toast.addEventListener('mouseleave', Swal.resumeTimer)
          }
        })

        Toast.fire({
          icon: 'success',
          title: `${data.message}`
        })
      } catch (error) {
        // sse 최초 연결시 더미데이터는 따로 처리하지 않음
      }

    });

  },

}

index.init();
