package com.mos.global.auth.handler.response;

import java.util.Map;

public class GithubLoginResponseHandler extends LoginResponseHandler {
  public GithubLoginResponseHandler(String response) {
    super(response);
  }

  private Map<String, Object> getProfile() {
    return (Map<String, Object>) getResponse().get("email");
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
