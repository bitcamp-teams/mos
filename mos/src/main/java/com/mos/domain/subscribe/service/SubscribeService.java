package com.mos.domain.subscribe.service;

import com.mos.domain.subscribe.repository.EmitterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class SubscribeService {

  private final EmitterRepository emitterRepository;
  private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;

  public SseEmitter subscribe(int memberNo, String lastEventId) {
    String id = memberNo + "_" + System.currentTimeMillis();

    SseEmitter emitter = emitterRepository.save(id, new SseEmitter(DEFAULT_TIMEOUT));
    emitter.onCompletion(() -> emitterRepository.deleteById(id));
    emitter.onTimeout(() -> emitterRepository.deleteById(id));

    sendToClient(emitter, id, "EventStream Created. [memberNo=" + memberNo + "]");

    if (!lastEventId.isEmpty()) {
      Map<String, Object> events = emitterRepository.findAllEventCacheStartWithId(String.valueOf(memberNo));
      events.entrySet().stream()
          .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
          .forEach(entry -> sendToClient(emitter, entry.getKey(), entry.getValue()));
    }
    return emitter;
  }


  private void sendToClient(SseEmitter emitter, String id, Object data) {
    try {
      emitter.send(SseEmitter.event()
          .id(id)
          .name("sse")
          .data(data));
    } catch (IOException e) {
      emitterRepository.deleteById(id);
      throw new RuntimeException("sse 오류!");
    }
  }

}
