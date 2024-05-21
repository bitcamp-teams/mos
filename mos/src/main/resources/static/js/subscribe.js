let index = {
    memberNo: $('#isLoginFrm #memberNoInput').val(),
    eventSource: null,

    init() {
        const _this = this;

        $(function () {
            if (_this.memberNo !== '' && _this.memberNo !== null) {
                _this.checkSubscription();
            }
        });
    },

    checkSubscription() {
        if (!localStorage.getItem('isSubscribed')) {
            this.subscribe();
        } else {
            this.initializeEventSource();
        }
    },

    subscribe() {
        const _this = this;
        _this.initializeEventSource();
        localStorage.setItem('isSubscribed', 'Y');
    },

    initializeEventSource() {
        const _this = this;
        if (_this.eventSource === null) {
            _this.eventSource = new EventSource('/api/v1/subscribe/' + _this.memberNo);

            _this.eventSource.addEventListener("sse", event => {
                try {
                    const data = JSON.parse(event.data);
                    const Toast = Swal.mixin({
                        toast: true,
                        // position: 'bottom-start',
                        showConfirmButton: false,
                        timer: 3000,
                        timerProgressBar: true,
                        didOpen: (toast) => {
                            toast.addEventListener('mouseenter', Swal.stopTimer)
                            toast.addEventListener('mouseleave', Swal.resumeTimer)
                        }
                    });

                    Toast.fire({
                        icon: 'success',
                        title: `${data.message}`,
                        customClass: {
                            popup: 'swal2-random-position',
                            title: 'custom-swal-title',
                            content: 'custom-swal-text'
                        },
                    });
                } catch (error) {
                    // sse 최초 연결시 더미데이터는 따로 처리하지 않음
                }
            });
        }
    }
};

index.init();
