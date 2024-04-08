package com.mos.global.auth.handler;

import com.mos.global.auth.dto.KaKaoRequest;
import com.mos.global.auth.exception.LoginApiException;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;


public class KakaoLoginRequestHandler implements LoginRequestHandler {
  private final static String KAKAO_API_URI = "https://kapi.kakao.com";

  @Override
  public LoginResponseHandler getUserInfo(RestTemplate restTemplate, String code) {
    var requestEntity =
        RequestEntity.post(KAKAO_API_URI + "/v2/user/me")
            .header("Authorization", getBearerToken(code))
            .accept(MediaType.APPLICATION_FORM_URLENCODED).acceptCharset(StandardCharsets.UTF_8)
            .build();


    var response = restTemplate.exchange(requestEntity, String.class);

    if (!response.getStatusCode().is2xxSuccessful()) {
      throw new LoginApiException("네이버 oauth 로그인 실패");
    }
    return new KakaoLoginResponseHandler(response.getBody());
  }

  private String getBearerToken(String code) {
    String token = new KaKaoRequest(code).getAccessToken();

    if (token.startsWith("Bearer"))
      return token;
    else
      return "Bearer" + token;
  }



}
