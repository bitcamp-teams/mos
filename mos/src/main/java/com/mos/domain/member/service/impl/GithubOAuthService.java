package com.mos.domain.member.service.impl;

import java.io.IOException;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class GithubOAuthService {

  private static final Log log = LogFactory.getLog(GithubOAuthService.class);
  @Value("${spring.security.oauth2.client.registration.github.clientId}")
  private String clientId;
  @Value("${spring.security.oauth2.client.registration.github.clientSecret}")
  private String clientSecret;

  @Autowired
  private WebClient webClient;

  public String getAccessToken(String code) throws IOException{
    MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
    formData.add("client_id", clientId);
    formData.add("client_secret", clientSecret);
    formData.add("code", code);

    String accessToken= webClient.post()
        .uri("https://github.com/login/oauth/access_token")
        .header("Accept", "application/json")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .body(BodyInserters.fromFormData(formData))
        .retrieve()
        .bodyToMono(String.class)
        .block();

    String tokenParser = "{\"access_token\":\"";
    if (accessToken == null) {
      log.debug(String.format("accessToken 가져오기 실패"));
      return null;
    }
    String resultToken = parsingResponse(accessToken, ",", tokenParser);
    log.debug(String.format("Github accessToken = " + accessToken));
    return getEmail(resultToken);
  }

  public String parsingResponse(String str, String split, String parser)throws IOException {
    String[] tokens = str.split(split);
    //반복문 사용
    //for (String part : tokens) {
    //  System.out.println("part =" + part);
    //  if (part.startsWith(parser)) {
    //    int partLastIdx = part.length()-1;
    //    return (part.substring(parser.length(), partLastIdx));
    //  }

    // 스트림 사용
    return Arrays.stream(tokens)           // 필터링 할 배열
        .filter(f -> f.startsWith(parser))// 필터링 조건에 부합하는 객체 반환
        .map(part -> part.substring(parser.length(), part.length() - 1)) // 객체에 substring 조건을 적용
        .findFirst()                       // 위 조건을 통과한 첫 객체
        .orElse(null);               // 없으면 null
  }

  public String getEmail(String token) throws IOException{

    OkHttpClient client = new OkHttpClient();

    Request request = new Request.Builder()
        .url("https://api.github.com/user/emails")
        .header("Authorization", "token " + token)
        .build();

    try (Response response = client.newCall(request).execute()) {
        String responseBody = response.body().string();
        String emailParser = "[{\"email\":\"";
        String email =  parsingResponse(responseBody, ",", emailParser);
        log.debug(String.format("email = " + email));
        return email;
    }
  }
}
