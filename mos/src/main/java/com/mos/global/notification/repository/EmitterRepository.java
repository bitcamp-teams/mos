package com.mos.global.notification.repository;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class EmitterRepository {
  public final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();
//  private final Map<String, Object> eventCache = new ConcurrentHashMap<>();

  public SseEmitter save(String id, SseEmitter sseEmitter) {
    emitters.put(id, sseEmitter);
    return sseEmitter;
  }

//  public void saveEventCache(String id, Object event) {
//    eventCache.put(id, event);
//  }

  public Map<String, SseEmitter> findAllStartWithById(String id) {
    return emitters.entrySet().stream()
        .filter(entry -> entry.getKey().startsWith(id))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }

//  public Map<String, Object> findAllEventCacheStartWithId(String id) {
//    return eventCache.entrySet().stream()
//        .filter(entry -> entry.getKey().startsWith(id))
//        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
//  }

  public Optional<SseEmitter> findById(String memberId) {
    return Optional.ofNullable(emitters.get(memberId));
  }

  public void deleteAllStartWithId(String id) {
    emitters.forEach(
        (key, emitter) -> {
          if (key.startsWith(id)) {
            emitters.remove(key);
          }
        }
    );
  }

  public void deleteById(String id) {
    emitters.remove(id);
  }

//  public void deleteAllEventCacheStartWithId(String id) {
//    eventCache.forEach(
//        (key, data) -> {
//          if (key.startsWith(id)) {
//            eventCache.remove(key);
//          }
//        }
//    );
//  }

}
