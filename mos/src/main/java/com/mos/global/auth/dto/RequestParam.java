package com.mos.global.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RequestParam {
  /* KAKAO */
  KAKAO_AUTHORIZATION_CODE("grant_type","authorization_code"),
  KAKAO_API_URI(null, RequestData.KAKAO_API_URI),
  KAKAO_CLIENT_ID("client_id", RequestData.KAKAO_CLIENT_ID),
  KAKAO_CLIENT_SECRET("client_secret", RequestData.KAKAO_CLIENT_SECRET),
  KAKAO_REDIRECT_URL("redirect_uri", RequestData.KAKAO_REDIRECT_URL),
  KAKAO_AUTH_URI(null, RequestData.KAKAO_AUTH_URI);

  private final String key;
  private final String param;
}
