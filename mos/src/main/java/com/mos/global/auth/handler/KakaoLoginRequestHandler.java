package com.mos.global.auth.handler;

import com.mos.global.auth.dto.KaKaoRequest;
import com.mos.global.auth.dto.RequestParam;
import com.mos.global.auth.exception.LoginApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
public class KakaoLoginRequestHandler implements LoginRequestHandler {

  @Override
  public LoginResponseHandler getUserInfo(RestTemplate restTemplate, String code) {
    var requestEntity =
        RequestEntity.post(RequestParam.KAKAO_API_URI.getParam() + "/v2/user/me")
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
    String token = new KaKaoRequest(code).getAccessToken();

    if (token.startsWith("Bearer"))
      return token;
    else
      return "Bearer " + token;
  }

}
