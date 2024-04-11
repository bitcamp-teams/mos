package com.mos.global.auth.service;

import com.mos.global.auth.dto.KakaoDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class KakaoService {

  private static final Log log = LogFactory.getLog(KakaoService.class);

  private final WebClient webClient;
  private final String kakaoClientId;
  private final String kakaoClientSecret;
  private final String kakaoRedirectUrl;
  private final static String KAKAO_AUTH_URI = "https://kauth.kakao.com";
  private final static String KAKAO_API_URI = "https://kapi.kakao.com";

  @Autowired
  public KakaoService(
      WebClient webClient,
      @Value("${kakao.client.id}")
      String kakaoClientId,
      @Value("${kakao.client.secret}")
      String kakaoClientSecret,
      @Value("${kakao.redirect.url}")
      String kakaoRedirectUrl) {
    this.webClient = webClient;
    this.kakaoClientId = kakaoClientId;
    this.kakaoClientSecret = kakaoClientSecret;
    this.kakaoRedirectUrl = kakaoRedirectUrl;
  }

  public String getKakaoLogin() {
    return KAKAO_AUTH_URI + "/oauth/authorize"
        + "?client_id=" + kakaoClientId
        + "&redirect_uri=" + kakaoRedirectUrl
        + "&response_type=code";
  }

  public KakaoDto getKakaoInfo(String code) throws Exception {
    if (code == null) throw new Exception("Failed get authorization code");

    String accessToken = "";
    String refreshToken = "";

    try {

      MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
      params.add("grant_type"   , "authorization_code");
      params.add("client_id"    , kakaoClientId);
      params.add("client_secret", kakaoClientSecret);
      params.add("code"         , code);
      params.add("redirect_uri" , kakaoRedirectUrl);

      String webClientToken = webClient.post()
          .uri(KAKAO_AUTH_URI + "/oauth/token")
          .header("Accept", "application/json")
//          .accept(MediaType.APPLICATION_JSON)
          .contentType(MediaType.APPLICATION_FORM_URLENCODED)
          .body(BodyInserters.fromFormData(params))
          .retrieve()
          .bodyToMono(String.class)
          .block();

      System.out.println("webClientToken = " + webClientToken);
//      ResponseEntity<String> response = webClient.post()
//          KAKAO_AUTH_URI + "/oauth/token",
//          HttpMethod.POST,
//          httpEntity,
//          String.class
//      );

      JSONParser jsonParser = new JSONParser();
      JSONObject jsonObj = (JSONObject) jsonParser.parse(webClientToken);

      accessToken  = (String) jsonObj.get("access_token");
      refreshToken = (String) jsonObj.get("refresh_token");
      System.out.println("jsonObj = " + jsonObj);
    } catch (Exception e) {
      throw new Exception("API call failed");
    }

    return getUserInfoWithToken(accessToken);
  }

  private KakaoDto getUserInfoWithToken(String accessToken) throws Exception {
    //HttpHeader 생성
//    HttpHeaders headers = new HttpHeaders();
//    headers.add("Authorization", "Bearer " + accessToken);
//    headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

    //HttpHeader 담기
//    RestTemplate rt = new RestTemplate();
//    HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(headers);
//    ResponseEntity<String> response = rt.exchange(
//        KAKAO_API_URI + "/v2/user/me",
//        HttpMethod.POST,
//        httpEntity,
//        String.class
//    );

    String userInfo = webClient.get()
        .uri(KAKAO_API_URI + "/v2/user/me")
        .header("Authorization", "Bearer " + accessToken)
        .retrieve()
        .bodyToMono(String.class)
        .block();

    System.out.println("userInfo = " + userInfo);

    //Response 데이터 파싱
    JSONParser jsonParser = new JSONParser();
    JSONObject jsonObj    = (JSONObject) jsonParser.parse(userInfo);
    JSONObject account = (JSONObject) jsonObj.get("kakao_account");
    JSONObject profile = (JSONObject) account.get("profile");

    System.out.println("profile = " + profile);
    long id = (long) jsonObj.get("id");
    String email = String.valueOf(account.get("email"));
    String nickname = String.valueOf(profile.get("nickname"));

    return KakaoDto.builder()
        .id(id)
        .email(email)
        .nickname(nickname).build();
  }
}
