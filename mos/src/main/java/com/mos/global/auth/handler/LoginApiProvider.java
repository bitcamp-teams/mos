package com.mos.global.auth.handler;

import com.mos.global.auth.dto.RequestData;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Getter
public enum LoginApiProvider implements LoginRequestHandler {

  //    GITHUB(new GithubLoginRequestHandler()),
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
