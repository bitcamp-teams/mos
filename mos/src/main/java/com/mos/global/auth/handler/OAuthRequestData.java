package com.mos.global.auth.handler;

import com.google.gson.GsonBuilder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class OAuthRequestData {

  public String code;

  /* KAKAO
  *     kakao.client.id
        kakao.client.secret
        kakao.redirect.url
        kakao.auth.uri
        kakao.api.url
  * */
  public static String KAKAO_API_URI;
  public static String KAKAO_CLIENT_ID;
  public static String KAKAO_CLIENT_SECRET;
  public static String KAKAO_REDIRECT_URL;
  public static String KAKAO_AUTH_URI;
  public static String KAKAO_LOGOUT_REDIRECT_URL;

  public OAuthRequestData setCode(String code) {
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

  @Value("${logout_redirect_uri}")
  public void setKakaoLogoutRedirectUrl(String kakaoLogoutRedirectUrl) {
    KAKAO_LOGOUT_REDIRECT_URL = kakaoLogoutRedirectUrl;
  }
  /* GITHUB
  *     github.clientId
        github.clientSecret
  * */
  public static String GITHUB_CLIENT_ID;
  public static String GITHUB_CLIENT_SECRET;

  @Value("${github.clientId}")
  public void setGithubClientId(String githubClientId) {
    GITHUB_CLIENT_ID = githubClientId;
  }

  @Value("${github.clientSecret}")
  public void setGithubClientSecret(String githubClientSecret) {
    GITHUB_CLIENT_SECRET = githubClientSecret;
  }

  /*
  * GOOGLE
  *     google.clientId
  *     google.clientSecret
  *     google.redirect-uri
  * */
  public static String GOOGLE_CLIENT_ID;
  public static String GOOGLE_CLIENT_SECRET;
  public static String GOOGLE_REDIRECT_URI;

  @Value("${google.clientId}")
  public void setGoogleClientId(String googleClientId) {
    GOOGLE_CLIENT_ID = googleClientId;
  }

  @Value("${google.clientSecret}")
  public void setGoogleClientSecret(String googleClientSecret) {
    GOOGLE_CLIENT_SECRET = googleClientSecret;
  }

  @Value("${google.redirect-uri}")
  public void setGoogleRedirectUri(String googleRedirectUri) {
    GOOGLE_REDIRECT_URI = googleRedirectUri;
  }

  @Override
  public String toString() {
    return new GsonBuilder().create().toJson(this);
  }


}
