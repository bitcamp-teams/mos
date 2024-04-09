package com.mos.global.auth.handler;

import org.springframework.web.client.RestTemplate;

public interface LoginRequestHandler {

    LoginResponseHandler getUserInfo(RestTemplate restTemplate, String token);


}

