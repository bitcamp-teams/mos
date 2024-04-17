package com.mos.global.auth.handler;

import static com.mos.global.auth.handler.LoginApiProvider.*;

public class RequestAuthCode extends RequestAttributes {

  private void getBody(LoginApiProvider provider, String code) {
    if (provider.equals(KAKAO)) {
      authenticate(provider, OAuthRequestParam.KAKAO_AUTH_URI.getParam() + "/oauth/token", code);
    } else if (provider.equals(GITHUB)) {
      authenticate(provider, "https://github.com/login/oauth/access_token", code);
    } else if (provider.equals(GOOGLE)) {
      authenticate(provider, "https://oauth2.googleapis.com/token", code);
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
