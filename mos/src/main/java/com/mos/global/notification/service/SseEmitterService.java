package com.mos.global.notification.service;

import com.mos.domain.notify.dto.NotifyDto;
import com.mos.global.notification.repository.EmitterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Service
public class SseEmitterService {

  private final EmitterRepository emitterRepository;
  private static final long DEFAULT_TIMEOUT = 30L * 1000 * 60;


  public SseEmitter createEmitter(String key) {
    return emitterRepository.save(key, new SseEmitter(DEFAULT_TIMEOUT));
  }

  public void deleteEmitter(String key) {
    emitterRepository.deleteById(key);
  }

  public void sendNotificationToClient(String key, NotifyDto notifyDto) {
    emitterRepository.findById(key)
        .ifPresent(emitter -> sendToClient(emitter, key, notifyDto));
  }


  public void sendToClient(SseEmitter emitter, String id, Object data) {
    try {
      emitter.send(SseEmitter.event()
          .id(id)
          .name("sse")
          .data(data));
    } catch (IOException e) {
      log.error("SSE 연결이 올바르지 않습니다. 해당 memberID={}", id);
      emitterRepository.deleteById(id);
    }
  }





}
