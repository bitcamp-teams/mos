package com.mos.global.auth.config;

import com.mos.global.auth.dto.RequestData;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
  public class WebClientConfig {
    @Bean
    public WebClient webClient() {
      return WebClient.create();
    }
  }

