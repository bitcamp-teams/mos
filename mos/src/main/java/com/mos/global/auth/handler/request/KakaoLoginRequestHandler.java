package com.mos.global.auth.handler.request;

import com.mos.global.auth.handler.LoginRequestHandler;
import com.mos.global.auth.handler.OAuthRequestParam;
import com.mos.global.auth.handler.RequestAuthCode;
import com.mos.global.auth.handler.response.KakaoLoginResponseHandler;
import com.mos.global.auth.handler.response.LoginResponseHandler;
import com.mos.global.auth.exception.LoginApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;

import static com.mos.global.auth.handler.LoginApiProvider.KAKAO;

@RequiredArgsConstructor
public class KakaoLoginRequestHandler implements LoginRequestHandler {

//  @Override
//  public Long logout(WebClient webClient, String token) {
//    return webClient.post()
//        .uri(OAuthRequestParam.KAKAO_LOGOUT_API.getParam())
//        .header("Authorization", getBearerToken(token))
//        .retrieve()
//        .bodyToMono(Long.class)
//        .block();
//  }

  @Override
  public LoginResponseHandler getUserInfo(WebClient webClient, String code) {
    String userInfo = webClient.post()
        .uri(OAuthRequestParam.KAKAO_API_URI.getParam() + "/v2/user/me")
        .header("Authorization", getBearerToken(code))
        .retrieve()
        .bodyToMono(String.class)
        .block();

    return new KakaoLoginResponseHandler(userInfo);
  }

  private String getBearerToken(String code) {
    String token = new RequestAuthCode(KAKAO, code).getAccessToken();

    if (token.startsWith("Bearer"))
      return token;
    else
      return "Bearer " + token;
  }

}
