// auth/login.html 모달 구현
import auth from "../api/auth/auth.js";

const index = {
    init() {
        const _this = this;

        $(document).on("click", ".btn-open-modal", async () => {
            _this.modalOpen();
        })
    },
    modalOpen() {
        auth.modalHtml().then(async res => {
            const htmlContent = await res.data.body.innerHTML;
            $('#loginModalContent').html(htmlContent);
        })
    },

}

index.init();



