package com.mos.global.auth.service;

import com.mos.global.auth.repository.MosSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SessionService {
  private final MosSessionRepository sessionRepository;

  @Transactional(readOnly = true)
  public int findAllSession() {
    return sessionRepository.countAll();
  }
}
