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
            const htmlContent = await res.data.body.innerHTML;
            $('#loginModalContent').html(htmlContent);
        })
    },
    signUp() {
        let currentStep = 1;
        const totalSteps = $('.signup-step').length;


        $('.btn-next').on('click', function () {
            const Toast = Swal.mixin({
                toast: true,
                position: 'top-end',
                showConfirmButton: false,
                timer: 3000
            });
            if (currentStep < totalSteps) {
                // 닉네임 중복확인
                let nickname = $('#signUpModal #userName').val().trim();
                if (nickname !== '') {
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
                }
            }
        })

        $('.btn-prev').on('click', function () {
            if (1 < currentStep) {
                $(`#step${currentStep}`).hide();
                currentStep--;
                $(`#step${currentStep}`).show();
            }
        })

        // TODO : submit 버튼 보이기!
        // if (currentStep === totalSteps) {
        //     $('.btn-next').hide();
        //     $('button[type=submit]').show();
        // }


        // TODO : axios 전송
        $('.btn-submit').on('click', function () {
            var email = $('#email').val();
            var password = $('#password').val();
            var username = $('#username').val();
        });
    },
    validation() {
        $.validator.setDefaults({
            submitHandler: function () {
                alert("정상적으로 가입되었습니다");
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
    }
    // loginProcess(el) {
    //     const id = $(el).closest('a').attr('id');
    //     const form = $('#callBackUrlFrm');
    //     const obj = form.find('input');
    //     let url = '';
    //
    //     obj.each(function () {
    //         if ($(this).attr('id') && id.includes($(this).attr('id'))) {
    //             url = this.value;
    //         }
    //     });
    //
    //     // 각 플랫폼에 맞춰 로그인 진행
    //     util[`${id}`](url)
    //
    //     // windowOpenHandler("loginPopup", url)
    // },
}

index.init();



