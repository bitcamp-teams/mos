// auth/login.html 모달 구현
import auth from "../api/auth/auth.js";
import * as util from "../util/util.js"


const index = {
    init() {
        const _this = this;

        $(document).on("click", ".btn-open-modal", async () => {
            _this.modalOpen();
        })

        $(document).ready(function () {
            let showModal = $('#isLoginFrm #showModal').val();
            if (showModal) {
                $('#signUpModal').modal('show')
            }
            $('select').select2({
                theme: 'bootstrap4',
                allowClear: true
            });
            _this.validation();
            _this.signUp();
        });

    },
    modalOpen() {
        auth.modalHtml().then(async res => {
            const htmlContent = res.data.body.innerHTML;
            $('#loginModalContent').html(htmlContent);
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
            console.log(params)
            auth.signUp(params).then(res => {
                Swal.fire({
                    icon: 'success',
                    title: '가입이 완료되었습니다!',
                    text: ``
                });
                $('.close').click();
                $('.modal-backdrop').remove();
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
    }
}

index.init();



