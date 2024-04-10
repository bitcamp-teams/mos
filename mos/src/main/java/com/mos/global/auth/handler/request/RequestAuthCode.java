package com.mos.global.auth.handler.request;

import com.mos.global.auth.handler.LoginApiProvider;
import com.mos.global.auth.handler.OAuthRequestParam;

import static com.mos.global.auth.handler.LoginApiProvider.GITHUB;
import static com.mos.global.auth.handler.LoginApiProvider.KAKAO;

public class RequestAuthCode extends RequestAttributes {

  private void getBody(LoginApiProvider provider, String code) {
    if (provider.equals(KAKAO)) {
      authenticate(provider, OAuthRequestParam.KAKAO_AUTH_URI.getParam(), code);
    } else if (provider.equals(GITHUB)) {
      authenticate(provider, "https://github.com/login/oauth/access_token", code);
    }
  }

  public RequestAuthCode(LoginApiProvider provider, String code) {
    getBody(provider, code);
  }

  @Override
  public String getAccessToken() {
    return (String) getResponse().get("access_token");
  }

}
