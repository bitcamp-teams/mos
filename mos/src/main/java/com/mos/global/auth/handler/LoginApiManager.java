package com.mos.global.auth.handler;

import com.mos.global.auth.exception.NotSupportAuthenticationProviderException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class LoginApiManager {

  private final Map<String, LoginApiProvider> map = new HashMap<>();

  public LoginApiManager() {
    for (LoginApiProvider provider : LoginApiProvider.values()) {
      map.put(provider.name(), provider);
    }
  }

  public LoginApiProvider getProvider(String providerName) {
    providerValid(providerName);
    return map.get(providerName);
  }

  private void providerValid(String providerName) {
    if(!map.containsKey(providerName)) throw new NotSupportAuthenticationProviderException();
  }
}
