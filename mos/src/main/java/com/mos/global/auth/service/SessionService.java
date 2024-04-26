package com.mos.global.auth.service;

import com.mos.global.auth.repository.MosSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionService {
  private final MosSessionRepository sessionRepository;

  public int findAllSession() {

    return sessionRepository.countAll();
  }
}
