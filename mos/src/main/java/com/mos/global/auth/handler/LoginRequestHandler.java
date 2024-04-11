package com.mos.global.auth.handler;

import com.mos.global.auth.handler.response.LoginResponseHandler;
import org.springframework.web.reactive.function.client.WebClient;

public interface LoginRequestHandler {

    LoginResponseHandler getUserInfo(WebClient webClient, String token);


}

