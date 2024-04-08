package com.mos.global.auth.handler;

import org.springframework.web.client.RestTemplate;

public enum LoginApiProvider implements LoginRequestHandler {


//    NAVER(new NaverLoginRequestHandler()),
    KAKAO(new KakaoLoginRequestHandler());

    private final LoginRequestHandler strategy;

    LoginApiProvider(LoginRequestHandler strategy) {
        this.strategy = strategy;
    }

    @Override
    public LoginResponseHandler getUserInfo(RestTemplate restTemplate, String token) {
        return this.strategy.getUserInfo(restTemplate, token);
    }
}
