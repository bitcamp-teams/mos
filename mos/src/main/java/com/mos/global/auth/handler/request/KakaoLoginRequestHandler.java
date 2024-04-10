package com.mos.global.auth.handler.request;

import com.mos.global.auth.handler.OAuthRequestParam;
import com.mos.global.auth.handler.response.KakaoLoginResponseHandler;
import com.mos.global.auth.handler.response.LoginResponseHandler;
import com.mos.global.auth.exception.LoginApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

import static com.mos.global.auth.handler.LoginApiProvider.KAKAO;

@RequiredArgsConstructor
public class KakaoLoginRequestHandler implements LoginRequestHandler {

  @Override
  public LoginResponseHandler getUserInfo(RestTemplate restTemplate, String code) {
    var requestEntity =
        RequestEntity.post(OAuthRequestParam.KAKAO_API_URI.getParam())
            .header("Authorization", getBearerToken(code))
            .contentType(MediaType.APPLICATION_FORM_URLENCODED).acceptCharset(StandardCharsets.UTF_8)
            .accept(MediaType.APPLICATION_JSON)
            .build();

    var response = restTemplate.exchange(requestEntity, String.class);

    if (!response.getStatusCode().is2xxSuccessful()) {
      throw new LoginApiException("카카오 oauth 로그인 실패");
    }
    return new KakaoLoginResponseHandler(response.getBody());
  }

  private String getBearerToken(String code) {
    String token = new RequestAuthCode(KAKAO, code).getAccessToken();

    if (token.startsWith("Bearer"))
      return token;
    else
      return "Bearer " + token;
  }

}
