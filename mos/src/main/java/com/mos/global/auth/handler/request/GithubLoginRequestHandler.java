package com.mos.global.auth.handler.request;

import com.mos.global.auth.exception.LoginApiException;
import com.mos.global.auth.handler.LoginRequestHandler;
import com.mos.global.auth.handler.RequestAuthCode;
import com.mos.global.auth.handler.response.GithubLoginResponseHandler;
import com.mos.global.auth.handler.response.LoginResponseHandler;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

import static com.mos.global.auth.handler.LoginApiProvider.GITHUB;


public class GithubLoginRequestHandler implements LoginRequestHandler {

  @Override
  public LoginResponseHandler getUserInfo(RestTemplate restTemplate, String code) {
    var requestEntity =
        RequestEntity.post("https://api.github.com/user")
            .header("Authorization", getBearerToken(code))
            .contentType(MediaType.APPLICATION_FORM_URLENCODED).acceptCharset(StandardCharsets.UTF_8)
            .accept(MediaType.APPLICATION_JSON)
            .build();


    var response = restTemplate.exchange(requestEntity, String.class);

    if (!response.getStatusCode().is2xxSuccessful()) {
      throw new LoginApiException("깃허브 oauth 로그인 실패");
    }
    return new GithubLoginResponseHandler(response.getBody());
  }

  private String getBearerToken(String code) {
    String token = new RequestAuthCode(GITHUB, code).getAccessToken();
    // github 은 앞에 "token " 붙여야됨
    if (token.startsWith("Bearer"))
      return "token " + token;
    else
      return "Bearer token " + token;
  }

}
