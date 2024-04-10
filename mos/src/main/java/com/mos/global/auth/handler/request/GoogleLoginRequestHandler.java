package com.mos.global.auth.handler.request;

import com.mos.global.auth.exception.LoginApiException;
import com.mos.global.auth.handler.LoginRequestHandler;
import com.mos.global.auth.handler.RequestAuthCode;
import com.mos.global.auth.handler.response.GithubLoginResponseHandler;
import com.mos.global.auth.handler.response.GoogleLoginResponseHandler;
import com.mos.global.auth.handler.response.LoginResponseHandler;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

import static com.mos.global.auth.handler.LoginApiProvider.GITHUB;
import static com.mos.global.auth.handler.LoginApiProvider.GOOGLE;


public class GoogleLoginRequestHandler implements LoginRequestHandler {

  @Override
  public LoginResponseHandler getUserInfo(RestTemplate restTemplate, String code) {
    var requestEntity =
        RequestEntity.post("https://www.googleapis.com/oauth2/v2/userinfo")
            .header("Authorization", getBearerToken(code))
            .contentType(MediaType.APPLICATION_FORM_URLENCODED).acceptCharset(StandardCharsets.UTF_8)
            .accept(MediaType.APPLICATION_JSON)
            .build();


    var response = restTemplate.exchange(requestEntity, String.class);

    if (!response.getStatusCode().is2xxSuccessful()) {
      throw new LoginApiException("구글 oauth 로그인 실패");
    }
    return new GoogleLoginResponseHandler(response.getBody());
  }

  private String getBearerToken(String code) {
    String token = new RequestAuthCode(GOOGLE, code).getAccessToken();
    if (token.startsWith("Bearer"))
      return token;
    else
      return "Bearer " + token;
  }

}
