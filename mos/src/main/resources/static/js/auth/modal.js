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
            if (currentStep < totalSteps) {
                $(`#step${currentStep}`).hide();
                currentStep++;
                $(`#step${currentStep}`).show();
            }
        })

        $('.btn-prev').on('click', function () {
            if (1 < currentStep) {
                $(`#step${currentStep}`).hide();
                currentStep--;
                $(`#step${currentStep}`).show();
            }
        })

        if (currentStep === totalSteps) {
            $('button[type=submit]').show();
        }

        $("#jobGroup").select2();

        $('.btn-submit').on('click', function () {
            var email = $('#email').val();
            var password = $('#password').val();
            var username = $('#username').val();
        });
    },
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



