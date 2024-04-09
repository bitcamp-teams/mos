package com.mos.global.auth.dto;

import com.google.gson.GsonBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

public class KaKaoRequest extends RequestAttributes {

  private static String body;

  private static synchronized String getBody(String code) {
    if (body == null) {
      var request = RequestEntity.post(RequestParam.KAKAO_AUTH_URI.getParam() + "/oauth/token")
          .contentType(MediaType.APPLICATION_FORM_URLENCODED).acceptCharset(StandardCharsets.UTF_8)
          .accept(MediaType.APPLICATION_JSON)
          .body(merge(code));

      body = new RestTemplate().exchange(request, String.class).getBody();
    }
    return body;
  }

  private static MultiValueMap<String, String> merge(String code) {
    MultiValueMap<String, String> enumMap = new LinkedMultiValueMap<>();
    enumMap.add("code", code);
    for (RequestParam value : RequestParam.values()) {
      if (value.getKey() != null)
        enumMap.add(value.getKey(), value.getParam());
    }
    /* serialize 제외 : KAKAO_API_URI, KAKAO_AUTH_URI */
    return enumMap;
  }

  public KaKaoRequest(String code) {
    super(getBody(code));
  }

  @Override
  public String getAccessToken() {
    return (String) getResponse().get("access_token");
  }

}
