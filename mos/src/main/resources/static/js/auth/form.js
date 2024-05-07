(function () {
    /*<![CDATA[*/
    // 로그인 기록 가져오기
    const recentLogin = JSON.parse(localStorage.getItem('recentLogin')) || {};

    // 최근 로그인 기록 업데이트
    function updateRecentLogin(platform) {
        recentLogin[platform] = new Date().toLocaleString(); // 현재 시간으로 최근 로그인 기록 업데이트
        localStorage.setItem('recentLogin', JSON.stringify(recentLogin)); // 로컬스토리지에 저장
    }

    $(document).on('click', '#kakaoLink', function () {
        updateRecentLogin('kakao');
    });

    $(document).on('click', '#googleLink', function () {
        updateRecentLogin('google');
    });

    $(document).on('click', '#githubLink', function () {
        updateRecentLogin('github');
    });

    $(document).on('click', '#naverLink', function () {
        updateRecentLogin('naver');
    });


    // recentLogin 에 들어있는 oauth 플랫폼 선택
    let recentDate = new Date(0).toLocaleString();
    let loginProvider = '';
    Object.entries(recentLogin).forEach(value => {
        if (recentDate < value[1]) {
            recentDate = value[1];
            loginProvider = value[0];
        }
    });

    $(`#${loginProvider}Link`).attr('data-login-provider', 'Y');

    console.log('lp===============> ', loginProvider)
    const button = document.querySelector(`#${loginProvider}Link`);
    const tooltip = document.querySelector('#tooltipM');

    if (button != null) {
        return {button, tooltip};
    }
    /*]]>*/
})();
