package com.mos.global.auth.handler;

import com.mos.global.auth.handler.response.LoginResponseHandler;
import org.springframework.web.client.RestTemplate;

public interface LoginRequestHandler {

    LoginResponseHandler getUserInfo(RestTemplate restTemplate, String token);


}

