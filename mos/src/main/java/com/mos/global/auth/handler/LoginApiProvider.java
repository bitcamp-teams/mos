package com.mos.global.auth.handler;

import com.mos.global.auth.handler.request.GithubLoginRequestHandler;
import com.mos.global.auth.handler.request.KakaoLoginRequestHandler;
import com.mos.global.auth.handler.request.LoginRequestHandler;
import com.mos.global.auth.handler.response.LoginResponseHandler;
import lombok.Getter;
import org.springframework.web.client.RestTemplate;

@Getter
public enum LoginApiProvider implements LoginRequestHandler {
//  GOOGLE(new GoogleLoginRequestHandler),
  GITHUB(new GithubLoginRequestHandler()),
  KAKAO(new KakaoLoginRequestHandler());

  private final LoginRequestHandler strategy;

  LoginApiProvider(LoginRequestHandler strategy) {
    this.strategy = strategy;
  }

  @Override
  public LoginResponseHandler getUserInfo(RestTemplate restTemplate, String code) {
    return this.strategy.getUserInfo(restTemplate, code);
  }
}
