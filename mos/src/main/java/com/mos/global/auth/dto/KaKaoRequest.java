package com.mos.global.auth.dto;

import com.amazonaws.Response;
import com.google.gson.GsonBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;


public class KaKaoRequest extends RequestAttributes {
  private final String grantType = "authorization_code";

  @Value("${kakao.redirect.url}")
  private String RedirectUrl;

  @Value("${kakao.client.id}")
  private String KAKAO_CLIENT_ID;

  @Value("${kakao.client.secret}")
  private String KAKAO_CLIENT_SECRET;

  @Value("${kakao.redirect.url}")
  private String KAKAO_REDIRECT_URL;

  private String code;

  private final static String KAKAO_AUTH_URI = "https://kauth.kakao.com";

  private static String body;

  static {
    RequestEntity<String> request = RequestEntity.post(KAKAO_AUTH_URI + "/oauth/token")
        .accept(MediaType.APPLICATION_FORM_URLENCODED).acceptCharset(StandardCharsets.UTF_8)
        .body(new GsonBuilder().create().toJson(KaKaoRequest.class));

    body = new RestTemplate().exchange(request, String.class).getBody();
  }

  public KaKaoRequest(String code) {
    super(body);
    this.code = code;
  }

  @Override
  public String getAccessToken() {
    return (String) getResponse().get("access_token");
  }

  @Override
  public String toString() {
    return new GsonBuilder().serializeNulls().create().toJson(this);
  }
}
