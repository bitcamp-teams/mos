const memberNo = $('#isLoginFrm #memberNoInput').val();
$(document).ready(function () {
    debugger
    if (memberNo !== '') {
        const eventSource = new EventSource('/subscribe/' + memberNo);
        eventSource.addEventListener("sse", event => {
            console.log(event.data);

            const data = JSON.parse(event.data);
            (async () => {
                const showNotification = () => {
                    const notification = new Notification('알림', {
                        body: data.content
                    });

                    setTimeout(() => {
                        notification.close();
                    }, 10 * 1000);

                    notification.addEventListener('click', () => {
                        window.open(data.url, '_blank');
                    })
                }

                let granted = false;

                if (Notification.permission === 'granted') {
                    granted = true;
                } else if (Notification.permission !== 'denied') {
                    let permission = await Notification.requestPermission();
                    granted = permission === 'granted';
                }

                if (granted) {
                    showNotification();
                }
            })();
        });
    }
});
