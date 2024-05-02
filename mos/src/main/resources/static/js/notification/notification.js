const index = {
    // 초기 페이지 번호 설정
    currentPage: 0,
    totalPages: 0,
    unreadCount: 0,
    startTime: '',
    popup: $('#popupNotifications'),
    notificationsContainer: $('.notifications'),
    init() {
        const _this = this;

        _this.getUnreadNotiCount();

        $(document).ready(function () {
            $('.notifications').scroll(function () {
                // 팝업 내부에서 끝에 도달했는지 확인
                if (_this.notificationsContainer.height() + _this.notificationsContainer.scrollTop() >= _this.notificationsContainer.prop('scrollHeight')) {
                    // 현재 페이지가 총 페이지 수보다 작은 경우에만 데이터 추가 로드
                    if (_this.currentPage < _this.totalPages) {
                        _this.fetchNotificationsWithPage(_this.currentPage, true); // 여기에 true를 추가합니다.
                    }
                }
            });
        });

        $('#notificationBtn').on('click', function (e) {
            e.preventDefault()
            if ($('.dropdown-menu').is(":hidden")){
                _this.fetchNotifications();
            }
        });

    },
    fetchNotifications() {
        const _this = this;
        // 초기 페이지 번호 설정
        _this.currentPage = 0;
        _this.totalPages = 0;
        _this.notificationsContainer.scrollTop(0);

        // 새로운 데이터를 불러오기 위해 fetchNotificationsWithPage 함수를 호출
        _this.fetchNotificationsWithPage(_this.currentPage);
    },
    fetchNotificationsWithPage(page, append = false) {
        const _this = this;
        console.log("fetchNotifications 함수가 호출되었습니다. 페이지 번호:", page);
        fetch(`/notify/list?page=${page}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(notifyListJson => {
                console.log(notifyListJson);
                _this.totalPages = notifyListJson.totalPages; // 총 페이지 수 업데이트
                _this.currentPage++; // 현재 페이지 업데이트
                _this.startTime = new Date();
                _this.displayNotifications(notifyListJson, append); // append 인자를 전달
            })
            .catch(error => {
                console.error('There has been a problem with your fetch operation:', error);
            });
    },
    displayNotifications(notifyListJson, append = false) {
        const _this = this;
        console.log("displayNotifications 함수가 호출되었습니다.");
        const notifyList = notifyListJson.content; // JSON.parse를 호출한 결과에서 content에 접근

        if (!append) {
            _this.notificationsContainer.html(''); // 기존에 표시된 알림을 클리어
        }

        if (notifyList.length === 0) {
            if (!append) {
                $('span.dropdown-header').text('알림이 없습니다.');
                $('span.badge.badge-warning.navbar-badge').text(0).show();
            }
        } else {
            // 노티 갯수 표시
            // if (!_this.exist) {
            //     const $totalCnt = $('<span class="dropdown-item dropdown-header popup-header-sticky disabled"></span>').text(`${_this.unreadCount} Notifications`);
            //     _this.notificationsContainer.prepend($totalCnt);
            //     _this.exist = true;
            // }
            $.each(notifyList, (index, item) => {
                const $divider = $('<div class="dropdown-divider"></div>');
                // 링크 설정
                const $a = $('<a>', {
                    'class': 'dropdown-item d-flex',
                    'href': `${item.link}`
                });
                $a.on('click', () => {
                    _this.sendReadRequest(item);
                });

                // 메시지 텍스트 설정
                const $i = $('<i id="notiIcon" class="fas fa-solid fa-comment mr-2"></i>')
                    .html(`<span class="ml-3 font-weight-light">${item.message}</span>`)
                    .hover(function () {
                        $(this).css('color', 'blue');
                    }, function () {
                        $(this).css('color', 'black');
                    });

                // TODO : 경과시간 출력
                const $time = $('<span class="float-right text-muted text-sm">0 분전</span>')

                let link = $a.append($i).append($time);
                _this.notificationsContainer.append($divider).append(link);
            });
            _this.popup.show();
        }
    },
    sendReadRequest(notify) {
        console.log("클릭 이벤트 발생");
        const requestBody = JSON.stringify({id: notify.id});

        console.log(requestBody);
        fetch('/notify/update', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: requestBody,
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                console.log(data);
                // 서버로부터의 응답 처리
            })
            .catch(error => {
                console.error('There has been a problem with your fetch operation:', error);
            });
    },
    getUnreadNotiCount() {
        const _this = this;
        fetch(`/api/v1/notifications`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(res => {
                _this.unreadCount = res.unreadCount
                $('span.badge.badge-warning.navbar-badge').text(`${_this.unreadCount}`).show();
                $('.popup-header').text(`${_this.unreadCount} Notifications`);
            })
            .catch(error => {
                console.error('There has been a problem with your fetch operation:', error);
            });
    },
    updateTimeElapsed(startTime) {
        const $timeSpan = $('span.float-right.text-muted.text-sm');

        function update() {
            const currentTime = new Date();
            const elapsedTime = currentTime - startTime; // 경과 시간을 밀리초로 계산
            const minutes = Math.floor(elapsedTime / 60000); // 분 단위로 변환
            let seconds = Math.floor((elapsedTime % 60000) / 1000); // 초 단위로 변환

            // 표시 형식을 맞추기 위해 10보다 작은 숫자 앞에 0을 추가
            seconds = seconds < 10 ? '0' + seconds : seconds;

            // 경과 시간을 <span> 태그에 표시
            $timeSpan.text(minutes + ' mins ' + seconds + ' secs');

            // 1초마다 함수를 다시 호출하여 시간을 업데이트
            setTimeout(update, 1000);
        }
        update();
    }

}

index.init();

