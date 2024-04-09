//package com.mos.global.auth.dto;
//
//import com.google.gson.GsonBuilder;
//import lombok.Builder;
//import org.springframework.http.MediaType;
//import org.springframework.http.RequestEntity;
//import org.springframework.web.bind.annotation.RequestAttribute;
//import org.springframework.web.client.RestTemplate;
//
//import java.nio.charset.StandardCharsets;
//
//
//public class GithubRequest extends RequestAttributes {
//
//  private static final String body;
//
//  static {
//    RequestEntity<String> request = RequestEntity.post(KAKAO_AUTH_URI + "/oauth/token")
//        .accept(MediaType.APPLICATION_FORM_URLENCODED).acceptCharset(StandardCharsets.UTF_8)
//        .body(new GsonBuilder().create().toJson(KaKaoRequest.class));
//
//    body = new RestTemplate().exchange(request, String.class).getBody();
//  }
//
//  public GithubRequest(String code) {
//    super(body);
//    this.code = code;
//  }
//
//  @Override
//  public String getAccessToken() {
//    return null;
//  }
//
//  @Override
//  public String toString() {
//    return new GsonBuilder().serializeNulls().create().toJson(this);
//  }
//}
