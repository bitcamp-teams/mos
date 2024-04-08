package com.mos.global.auth.dto;

import com.google.gson.GsonBuilder;
import lombok.Builder;
import org.springframework.web.bind.annotation.RequestAttribute;


public class GithubRequest extends RequestAttributes {

  public GithubRequest(String response) {
    super(response);
  }

  @Override
  public String getAccessToken() {
    return null;
  }

  @Override
  public String toString() {
    return new GsonBuilder().serializeNulls().create().toJson(this);
  }
}
