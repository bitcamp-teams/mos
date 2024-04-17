//package com.mos.global.auth.service;
//
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
//import java.util.Optional;
//import lombok.RequiredArgsConstructor;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Service;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.reactive.function.BodyInserters;
//import org.springframework.web.reactive.function.client.WebClient;
//
////@RequiredArgsConstructor
//@Service
////@ConfigurationProperties(prefix = "github")
////@ConstructorBinding
//public class GoogleOAuthService implements OAuthService{
//
//  private static final Log log = LogFactory.getLog(GoogleOAuthService.class);
//  @Autowired
//  private WebClient webClient;
//
//  private final String clientId;
//
//  private final String clientSecret;
//
//  private final String redirectUri;
//
//
//  @Autowired
//  public GoogleOAuthService(WebClient webClient,
//      @Value("${google.clientId}") String clientId,
//      @Value("${google.clientSecret}") String clientSecret,
//      @Value("${google.redirect-uri}") String redirectUri) {
//    this.webClient = webClient;
//    this.clientId = clientId;
//    this.clientSecret = clientSecret;
//    this.redirectUri = redirectUri;
//  }
//
//
//
//  private String requestAccessToken(MultiValueMap<String, String> formData) {
//    return webClient.post()
//        .uri("https://oauth2.googleapis.com/token")
//        .header("Accept", "application/json")
//        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//        .body(BodyInserters.fromFormData(formData))
//        .retrieve()
//        .bodyToMono(String.class)
//        .block();
//  }
//
//
//  @Override
//  public Optional<String> getAccessToken(String code){
//    MultiValueMap<String, String> formData = setFormData(code);
//
//    String tokenBeforeParsing = requestAccessToken(formData);
//
//    System.out.println("tokenBeforeParsing = " + tokenBeforeParsing);
//    String tokenAfterParsing = parsingResponse(tokenBeforeParsing, "access_token");
////    log.debug(String.format("Github accessToken = " + tokenAfterParsing));
//
//    System.out.println("tokenAfterParsing = " + tokenAfterParsing);
//    return Optional.ofNullable(getEmail(tokenAfterParsing)).orElse(null);
//  }
//
//  private MultiValueMap<String, String> setFormData(String code) {
//    MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
//    formData.add("client_id", clientId);
//    formData.add("client_secret", clientSecret);
//    formData.add("code", code);
//    formData.add("grant_type", "authorization_code");
//    formData.add("redirect_uri", redirectUri);
//    return formData;
//  }
//
//
//  private String parsingResponse(String response, String key) {
////    String[] tokens = str.split(",");
//    //반복문 사용
//    //for (String part : tokens) {
//    //  System.out.println("part =" + part);
//    //  if (part.startsWith(parser)) {
//    //    int partLastIdx = part.length()-1;
//    //    return (part.substring(parser.length(), partLastIdx));
//    //  }
//
//    // 스트림 사용
////    return Arrays.stream(tokens)           // 필터링 할 배열
////        .filter(f -> f.startsWith(parser))// 필터링 조건에 부합하는 객체 반환
////        .map(part -> part.substring(parser.length(), part.length() - 1)) // 객체에 substring 조건을 적용
////        .findFirst()                       // 위 조건을 통과한 첫 객체
////        .orElse(null);               // 없으면 null
//    JsonObject json = JsonParser.parseString(response).getAsJsonObject();
//    return json.has(key) ? json.get(key).getAsString() : null;
//  }
//
//  private String requestUserEmail(String token) {
//    return webClient.get()
//        .uri("https://www.googleapis.com/oauth2/v2/userinfo")
//        .header("Authorization", "Bearer " + token)
//        .retrieve()
//        .bodyToMono(String.class)
//        .block();
//  }
//
//  @Override
//  public Optional<String> getEmail(String token) {
//
//    String emailBeforeParsing = requestUserEmail(token);
//    System.out.println("emailBeforeParsing = " + emailBeforeParsing);
//
//    if (emailBeforeParsing == null) {
//      return Optional.empty();
//    }
//    String emailAfterParsing = parsingResponse(emailBeforeParsing, "email");
//    System.out.println("emailAfterParsing = " + emailAfterParsing);
//    return Optional.ofNullable(emailAfterParsing);
//  }
//}
//
