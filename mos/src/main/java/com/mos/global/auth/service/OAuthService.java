package com.mos.global.auth.service;

import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public interface OAuthService {

  Optional<String> getAccessToken(String code);

  String getEmail(String token);
}
