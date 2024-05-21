let index = {
    memberNo: $('#isLoginFrm #memberNoInput').val(),
    init() {
        const _this = this;

        if (_this.memberNo !== '') {
            _this.subscribe();
        }

    },
    subscribe() {
        if (!window.EventSource) {
            alert("The browser doesn't support EventSource.");
            return;
        }

        const _this = this;
        const lastEventId = localStorage.getItem('lastEventId');
        let eventSource = new EventSource('/api/v1/subscribe/' + _this.memberNo + (lastEventId ? '?lastEventId=' + lastEventId : ''));
        console.log('sub!');
        localStorage.setItem('isSubscribed', 'Y');

        eventSource.addEventListener("sse", event => {
            localStorage.setItem('lastEventId', event.lastEventId);
            try {
                const data = JSON.parse(event.data);
                const Toast = Swal.mixin({
                    toast: true,
                    // position: 'top-end',
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
                    title: `${data.message}`,
                    customClass: {
                        popup: 'swal2-random-position',
                        title: 'custom-swal-title',
                        content: 'custom-swal-text'
                    }
                })
            } catch (error) {
                // sse 최초 연결시 더미데이터는 따로 처리하지 않음
            }
        });

        eventSource.onerror = function (event) {
            if (event.readyState === EventSource.CLOSED) {
                console.log('Connection closed');
            } else {
                console.error('Error:', event);
            }
        };
    },
}
index.init();
