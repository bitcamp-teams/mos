package com.mos.global.auth.dto;

import com.google.gson.GsonBuilder;
import lombok.Getter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class RequestData implements InitializingBean {
  private static final Log log = LogFactory.getLog(RequestData.class);
  /* KAKAO */
  public static String KAKAO_API_URI;
  public static String KAKAO_CLIENT_ID;
  public static String KAKAO_CLIENT_SECRET;
  public static String KAKAO_REDIRECT_URL;
  public static String KAKAO_AUTH_URI;
  public String code;

  @Override
  public void afterPropertiesSet() throws Exception {
    log.debug(String.format("RequestData: %s", KAKAO_API_URI));
  }

  public RequestData setCode(String code) {
    this.code = code;
    return this;
  }

  @Value("${kakao.api.url}")
  public void setKakaoApiUri(String kakaoApiUri) {
    KAKAO_API_URI = kakaoApiUri;
  }

  @Value("${kakao.client.id}")
  public void setKakaoClientId(String kakaoClientId) {
    KAKAO_CLIENT_ID = kakaoClientId;
  }

  @Value("${kakao.client.secret}")
  public void setKakaoClientSecret(String kakaoClientSecret) {
    KAKAO_CLIENT_SECRET = kakaoClientSecret;
  }

  @Value("${kakao.redirect.url}")
  public void setKakaoRedirectUrl(String kakaoRedirectUrl) {
    KAKAO_REDIRECT_URL = kakaoRedirectUrl;
  }

  @Value("${kakao.auth.uri}")
  public void setKakaoAuthUri(String kakaoAuthUri) {
    KAKAO_AUTH_URI = kakaoAuthUri;
  }

  /* */

  @Override
  public String toString() {
    return new GsonBuilder().create().toJson(this);
  }


}
