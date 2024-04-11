package com.mos.global.auth.handler.request;

import com.mos.global.auth.handler.LoginRequestHandler;
import com.mos.global.auth.handler.OAuthRequestParam;
import com.mos.global.auth.handler.RequestAuthCode;
import com.mos.global.auth.handler.response.KakaoLoginResponseHandler;
import com.mos.global.auth.handler.response.LoginResponseHandler;
import com.mos.global.auth.exception.LoginApiException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;

import static com.mos.global.auth.handler.LoginApiProvider.KAKAO;

@Getter
@RequiredArgsConstructor
public class KakaoLoginRequestHandler implements LoginRequestHandler {

  private String token;

  @Override
  public LoginResponseHandler getUserInfo(WebClient webClient, String code) {
    String userInfo = webClient.post()
        .uri(OAuthRequestParam.KAKAO_API_URI.getParam() + "/v2/user/me")
        .header("Authorization", getBearerToken(code))
        .retrieve()
        .bodyToMono(String.class)
        .block();

    return new KakaoLoginResponseHandler(userInfo, token);
  }

  private String getBearerToken(String code) {
    token = new RequestAuthCode(KAKAO, code).getAccessToken();

    if (token.startsWith("Bearer"))
      return token;
    else
      return "Bearer " + token;
  }

}
