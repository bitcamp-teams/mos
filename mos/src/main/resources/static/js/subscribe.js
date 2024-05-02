// 로그인 상태일 때만 호출됨
const memberNo = $('#isLoginFrm #memberNoInput').val();
$(document).ready(function () {
    if (memberNo !== '') {
        const eventSource = new EventSource('/api/v1/subscribe/' + memberNo);
        eventSource.addEventListener("sse", event => {
            const data = JSON.parse(event.data);

            const Toast = Swal.mixin({
                toast: true,
                position: 'top-end',
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

        });
    }
});
