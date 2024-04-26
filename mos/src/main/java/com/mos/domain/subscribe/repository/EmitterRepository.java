package com.mos.domain.subscribe.repository;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Component
public class EmitterRepository {

  private final RedisTemplate<String, SseEmitter> redisTemplate;

  public EmitterRepository(RedisTemplate<String, SseEmitter> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  public SseEmitter save(String id, SseEmitter sseEmitter) {
    redisTemplate.opsForValue().set(id, sseEmitter);
    return sseEmitter;
  }

  public void deleteById(String id) {
    redisTemplate.delete(id);
  };

  public Map<String, Object> findAllEventCacheStartWithId(String memberNo) {
    Set<String> keys = redisTemplate.keys(memberNo + "*");
    Map<String, Object> result = new HashMap<>();
    for (String key : Objects.requireNonNull(keys)) {
      result.put(key, redisTemplate.opsForValue().get(key));
    }
    return result;
  };

}
