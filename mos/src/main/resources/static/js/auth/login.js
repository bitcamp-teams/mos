// auth/form.html 모달 구현
import auth from "../api/auth/auth.js";


const index = {
    init() {
        const _this = this;

        $(document).on("click", "#openLoginModal", () => {
            _this.modalOpen();
        })

        $(document).ready(function () {
            let $loginModal = $('#loginModal');
            // 모달 애니메이션 및 sse subscribe
            _this.modalHandler($loginModal);

            let showModal = $('#isLoginFrm #showModal').val();
            if (showModal) {
                $('#signUpModal').modal('show');
            }
            $('select').select2({
                theme: 'bootstrap4',
                allowClear: true
            });
            _this.validation();
            _this.signUp();
        });

        // sse 연결 종료
        $(document).on('click', '#logoutBtn', _this.handleLogout.bind(_this));

        window.addEventListener('unload', _this.handleUnload.bind(_this));

    },
    modalOpen() {
        const _this = this;
        auth.modalHtml().then(res => {
            const htmlContent = res.data.body.innerHTML;
            $('#loginModalContent').html(htmlContent);
        }).then(value => {

        })
    },
    signUp() {
        const _this = this;
        let currentStep = 1;
        const totalSteps = $('.signup-step').length;
        const Toast = Swal.mixin({
            toast: true,
            position: 'top-end',
            showConfirmButton: false,
            timer: 3000
        });

        $('.btn-next').on('click', function () {
            if (currentStep < totalSteps) {
                let nickname = $('#signUpModal #userName').val().trim();
                if (nickname !== '') {
                    // 닉네임 중복확인
                    auth.findByEmail({userName: nickname}).then(res => {
                        if (res.data.errorCode == null) {
                            $(`#step${currentStep}`).hide();
                            currentStep++;
                            $(`#step${currentStep}`).show();
                        } else {
                            if (res.data.errorCode === '-80') {
                                Toast.fire({
                                    icon: 'error',
                                    title: '필수값이 없습니다.',
                                    text: ``
                                });
                            } else if (res.data.errorCode === '-100') {
                                Toast.fire({
                                    icon: 'error',
                                    title: '닉네임이 중복됩니다. 다시 입력해주세요.',
                                    text: ``
                                });
                            }
                            $('#userName').focus();
                        }
                    });
                } else {
                    Toast.fire({
                        icon: 'error',
                        title: '닉네임을 입력해주세요',
                        text: ``
                    });
                }
            }
            _this.updateSubmitButtonVisibility(currentStep, totalSteps)
        })

        $('.btn-prev').on('click', function () {
            if (1 < currentStep) {
                $(`#step${currentStep}`).hide();
                currentStep--;
                $(`#step${currentStep}`).show();
            }
            _this.updateSubmitButtonVisibility(currentStep, totalSteps)
        })

        // axios 전송
        $('.btn-submit').on('click', function () {
            let params = JSON.stringify($('#signupFrm').serializeObject());
            auth.signUp(params).then(res => {
                let resultData = res.data.resultData;
                if (resultData.memberNo != null) {
                    Swal.fire({
                        icon: 'success',
                        title: '가입이 완료되었습니다!',
                        text: ''
                    }).then(result => {
                        if (result.isConfirmed) {
                            location.reload();
                        }
                    });
                    $('.close').click();
                    $('.modal-backdrop').remove();
                }
            }).catch(reason => {
                Toast.fire({
                    icon: 'error',
                    title: '등록실패!.',
                    text: `${reason}`
                });
            })
        });
    },
    validation() {
        const Toast = Swal.mixin({
            toast: true,
            position: 'top-end',
            showConfirmButton: false,
            timer: 3000
        });
        $.validator.setDefaults({
            submitHandler: function () {
                Toast.fire({
                    icon: 'success',
                    title: '정상적으로 등록되었습니다.',
                    text: ``
                });
            }
        });
        $('#signupFrm').validate({
            rules: {
                userName: {
                    required: true,
                    minlength: 3
                },
            },
            messages: {
                userName: {
                    required: "닉네임을 입력해주세요",
                    minlength: "닉네임은 3글자 이상입니다"
                }
            },
            errorElement: 'span',
            errorPlacement: function (error, element) {
                error.addClass('invalid-feedback');
                element.closest('.form-group').append(error);
            },
            highlight: function (element, errorClass, validClass) {
                $(element).addClass('is-invalid');
            },
            unhighlight: function (element, errorClass, validClass) {
                $(element).removeClass('is-invalid');
            }
        });
    },
    updateSubmitButtonVisibility(currentStep, totalSteps) {
        console.log(currentStep, totalSteps)
        if (currentStep === totalSteps) {
            $('.btn-next').hide();
            $('button[type=submit]').show();
        } else {
            $('.btn-next').show();
            $('button[type=submit]').hide();
        }
    },
    modalHandler(modal) {
        console.log($(`[data-login-provider]`));
        Popper.createPopper(button, tooltip, {
            placement: 'top',
            modifiers: [
                {
                    name: 'offset',
                    options: {
                        offset: ({placement, reference, popper}) => {
                            if (placement === 'top') {
                                return [0, popper.height / 2];
                            } else {
                                return [];
                            }
                        },
                    },
                },
            ],
        }).update();
        $('#tooltipM').show();


        // modal.on('show.bs.modal', function (e) {
        //     const modal = $(this);
        //     modal.find('.modal-dialog').css('transform', 'translateY(-100%)');
        // });
        //
        // // 모달이 완전히 열린 후 애니메이션을 정상 위치로
        // modal.on('shown.bs.modal', function (e) {
        //     const modal = $(this);
        //     modal.find('.modal-dialog').css('transform', '');
        // });

        modal.on('show.bs.modal', function (e) {
            const modal = $(this);
            modal.find('.modal-dialog').css('opacity', 0); // 초기에 투명도를 0으로 설정합니다.
        });

        // 모달이 완전히 열린 후 애니메이션을 정상 위치로
        modal.on('shown.bs.modal', function (e) {
            const modal = $(this);
            modal.find('.modal-dialog').animate({ opacity: 1 }, 400); // 400ms 동안 fade in 애니메이션을 적용합니다.
        });
    },
    handleLogout() {
        this.logout()
    },
    handleUnload() {
        this.logout()
    },
    logout() {
        // localStorage 'isSubscribed' 값 제거
        localStorage.removeItem('isSubscribed');

        // 로그아웃 처리
        auth.logout().then(res => {
            if (res.status === 200) {
                location.href = '/'
            }
        });
    }
}

index.init();



