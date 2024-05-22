package com.mos.global.auth.handler.response;

import java.util.Map;

public class NaverLoginResponseHandler extends LoginResponseHandler {
  public NaverLoginResponseHandler(String response, String token) {
    super(response, token);
  }

  private Map<String, Object> getProfile() {
    return (Map<String, Object>) getResponse().get("response");
  }

  @Override
  public String getEmail() {
    return (String) getProfile().get("email");
  }

  @Override
  public String getName() {
    return (String) getProfile().get("name");
  }

  @Override
  public String getPassword() {
    return "";
  }

  @Override
  public String getId() {
    return "";
  }
}
