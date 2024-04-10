package com.mos.global.auth.service;

import com.mos.global.auth.handler.OAuthRequestParam;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

@Service
public class KakaoService {

  private static final Log log = LogFactory.getLog(KakaoService.class);

  public String getKakaoLogin() {
    return OAuthRequestParam.KAKAO_AUTH_URI.getParam() + "/oauth/authorize" + "?client_id=" + OAuthRequestParam.KAKAO_CLIENT_ID.getParam() + "&redirect_uri=" + OAuthRequestParam.KAKAO_REDIRECT_URL.getParam() + "&response_type=code";
  }
}
