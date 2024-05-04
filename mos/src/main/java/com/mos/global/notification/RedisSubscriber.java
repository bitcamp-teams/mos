package com.mos.global.notification;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mos.domain.notify.dto.NotifyDto;
import com.mos.global.notification.service.SseEmitterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.mos.global.notification.ChannelPrefix.*;

@Slf4j
@RequiredArgsConstructor
@Component
public class RedisSubscriber implements MessageListener {

    private final ObjectMapper objectMapper;
    private final SseEmitterService sseEmitterService;

    @Override
    public void onMessage(Message message, byte[] pattern) {
      String channel = new String(message.getChannel())
              .substring(COMMENT_CHANNEL_PREFIX.getPrefix().length());

      NotifyDto notificationDto = serialize(message);

      // 클라이언트에게 event 데이터 전송
      sseEmitterService.sendNotificationToClient(channel, notificationDto);
    }

    private NotifyDto serialize(final Message message) {
        try {
            return this.objectMapper.readValue(message.getBody(), NotifyDto.class);
        } catch (IOException e) {
            throw new RuntimeException(String.format("유효하지 않은 reids message입니다. message= {%s}", message));
        }
    }
}
