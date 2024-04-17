package com.mos.global.auth.handler;

import com.mos.global.auth.handler.request.GithubLoginRequestHandler;
import com.mos.global.auth.handler.request.GoogleLoginRequestHandler;
import com.mos.global.auth.handler.request.KakaoLoginRequestHandler;
import com.mos.global.auth.handler.response.LoginResponseHandler;
import lombok.Getter;
import org.springframework.web.reactive.function.client.WebClient;

@Getter
public enum LoginApiProvider implements LoginRequestHandler {
  GOOGLE(new GoogleLoginRequestHandler()),
  GITHUB(new GithubLoginRequestHandler()),
  KAKAO(new KakaoLoginRequestHandler());

  private final LoginRequestHandler strategy;

  LoginApiProvider(LoginRequestHandler strategy) {
    this.strategy = strategy;
  }

  @Override
  public LoginResponseHandler getUserInfo(WebClient webClient, String code) {
    return this.strategy.getUserInfo(webClient, code);
  }
}
