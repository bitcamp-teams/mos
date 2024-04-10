package com.mos.global.auth.handler.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mos.global.auth.handler.LoginApiProvider;
import com.mos.global.auth.handler.OAuthRequestParam;
import lombok.Getter;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Getter
public abstract class RequestAttributes {
  private Map<String, Object> response;

  private void setResponse(String response) {
    try {
      var om = new ObjectMapper();
      this.response = om.readValue(response, Map.class);
    } catch (JsonProcessingException ex) {
      throw new IllegalArgumentException("요청 파라미터 오류");
    }
  }

  protected void authenticate(LoginApiProvider provider, String uri, String code) {
    var request = RequestEntity.post(uri)
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .acceptCharset(StandardCharsets.UTF_8)
        .accept(MediaType.APPLICATION_JSON)
        .body(merge(provider, code));

    setResponse(new RestTemplate().exchange(request, String.class).getBody());
  }

  private MultiValueMap<String, String> merge(LoginApiProvider provider, String code) {
    MultiValueMap<String, String> enumMap = new LinkedMultiValueMap<>();
    enumMap.add("code", code);
    for (OAuthRequestParam value : OAuthRequestParam.values()) {
      if (value.getKey() != null) {  /* serialize 제외 : KAKAO_API_URI, KAKAO_AUTH_URI */
        getParam(provider, value, enumMap);
      }
    }
    return enumMap;
  }

  private void getParam(LoginApiProvider provider, OAuthRequestParam value, MultiValueMap<String, String> enumMap) {
    // 카카오, 깃헙, 구글에 따라 요청 파라미터 선별
    boolean check = value.name().startsWith(provider.name());
    if (check) {
      enumMap.add(value.getKey(), value.getParam());
    }
  }

  public abstract String getAccessToken();

}
