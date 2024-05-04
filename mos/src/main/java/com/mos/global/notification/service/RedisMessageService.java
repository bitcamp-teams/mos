package com.mos.global.notification.service;

import com.mos.domain.notify.dto.NotifyDto;
import com.mos.global.notification.RedisSubscriber;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

import static com.mos.global.notification.ChannelPrefix.*;

@RequiredArgsConstructor
@Service
public class RedisMessageService {

    private final RedisMessageListenerContainer container;
    private final RedisSubscriber subscriber; // 따로 구현한 Subscriber
    private final RedisOperations<String, NotifyDto> redisTemplate;

    // 채널 구독
    public void subscribe(String channel) {
        container.addMessageListener(subscriber, ChannelTopic.of(getChannelName(channel)));
    }

    // 이벤트 발행
    public void publish(String channel, NotifyDto notifyDto) {
        redisTemplate.convertAndSend(getChannelName(channel), notifyDto);
    }

    // 구독 삭제
    public void removeSubscribe(String channel) {
        container.removeMessageListener(subscriber, ChannelTopic.of(getChannelName(channel)));
    }

    private String getChannelName(String id) {
        return COMMENT_CHANNEL_PREFIX.getPrefix() + id;
    }
}
