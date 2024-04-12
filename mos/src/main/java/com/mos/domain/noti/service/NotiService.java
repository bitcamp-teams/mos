package com.mos.domain.noti.service;

import com.mos.domain.noti.dto.NotiAddDto;
import com.mos.domain.noti.repository.NotiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotiService {

  private final NotiRepository notiRepository;

  public void send(NotiAddDto notiDto) {

    notiRepository.add(notiDto);
  }
}
