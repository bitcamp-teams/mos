package com.mos.global.auth.handler.response;

import java.util.Map;

public class GithubLoginResponseHandler extends LoginResponseHandler {
  public GithubLoginResponseHandler(String response, String token) {
    super(response, token);
  }

  private Map<String, Object> getProfile() {
    return getResponse();
  }

  @Override
  public String getEmail() {
    return (String) getProfile().get("email");
  }

  @Override
  public String getName() {
    return "";
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
