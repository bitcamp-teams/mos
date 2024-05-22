//package com.mos.global.auth.storage;
//
//import java.util.Arrays;
//import java.util.Optional;
//
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
//@Service
////@RequiredArgsConstructor
////@ConfigurationProperties(prefix = "github")
////@ConstructorBinding
//public class GithubOAuthService {
//
//  private static final Log log = LogFactory.getLog(GithubOAuthService.class);
//
//  private final String clientId;
//
//  private final String clientSecret;
//
//  public GithubOAuthService(
//      @Value("${github.clientId}")String clientId,
//      @Value("${github.clientSecret}")String clientSecret) {
//    this.clientId = clientId;
//    this.clientSecret = clientSecret;
//  }
//
//  @Autowired
//  private WebClient webClient;
//
//  private String requestAccessToken(MultiValueMap<String, String> formData) {
//    return webClient.post()
//        .uri("https://github.com/login/oauth/access_token")
//        .header("Accept", "application/json")
//        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//        .body(BodyInserters.fromFormData(formData))
//        .retrieve()
//        .bodyToMono(String.class)
//        .block();
//  }
//
//
//  public Optional<String> getAccessToken(String code){
//    MultiValueMap<String, String> formData = setFormData(code);
//
//    String tokenBeforeParsing = requestAccessToken(formData);
//
//    String tokenParser = "{\"access_token\":\"";
//    String tokenAfterParsing = parsingResponse(tokenBeforeParsing, tokenParser);
//    log.debug(String.format("Github accessToken = " + tokenAfterParsing));
//
//    return Optional.ofNullable(getEmail(tokenAfterParsing)).orElse(null);
//  }
//
//  private MultiValueMap<String, String> setFormData(String code) {
//    MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
//    formData.add("client_id", clientId);
//    formData.add("client_secret", clientSecret);
//    formData.add("code", code);
//    return formData;
//  }
//
//
//  private String parsingResponse(String str, String parser) {
//    String[] tokens = str.split(",");
//    //반복문 사용
//    //for (String part : tokens) {
//    //  System.out.println("part =" + part);
//    //  if (part.startsWith(parser)) {
//    //    int partLastIdx = part.length()-1;
//    //    return (part.substring(parser.length(), partLastIdx));
//    //  }
//
//    // 스트림 사용
//    return Arrays.stream(tokens)           // 필터링 할 배열
//        .filter(f -> f.startsWith(parser))// 필터링 조건에 부합하는 객체 반환
//        .map(part -> part.substring(parser.length(), part.length() - 1)) // 객체에 substring 조건을 적용
//        .findFirst()                       // 위 조건을 통과한 첫 객체
//        .orElse(null);               // 없으면 null
//  }
//
//  private String requestUserEmail(String token) {
//    return webClient.get()
//        .uri("https://api.github.com/user/emails")
//        .header("Authorization", "token " + token)
//        .retrieve()
//        .bodyToMono(String.class)
//        .block();
//  }
//
//  public Optional<String> getEmail(String token) {
//
//    String emailBeforeParsing = requestUserEmail(token);
//
//    String emailParser = "[{\"email\":\"";
//    if (emailBeforeParsing == null) {
//      return Optional.empty();
//    }
//    String emailAfterParsing = parsingResponse(emailBeforeParsing, emailParser);
//    return Optional.ofNullable(emailAfterParsing);
//  }
//}
