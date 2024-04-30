package com.mos.global.notification.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mos.domain.comment.dto.StudyCommentDto;
import com.mos.domain.notify.dto.NotifyDto;
import com.mos.global.notification.dto.NotificationsResponseDto;
import com.mos.global.notification.event.CommentAndAuthorIdEvent;
import com.mos.global.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class NotificationService {

  private final RedisOperations<String, NotifyDto> eventRedisOperations;
  private final RedisMessageListenerContainer redisMessageListenerContainer;
  private final NotificationRepository notificationRepository;
  private final ObjectMapper objectMapper;
  private static final long DEFAULT_TIMEOUT = 10L * 1000 * 60;
  private static final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();


  @Transactional(propagation = Propagation.REQUIRES_NEW)
  @TransactionalEventListener
  public void send(CommentAndAuthorIdEvent commentAndAuthorIdEvent) {
    final StudyCommentDto studyCommentDto = commentAndAuthorIdEvent.getStudyCommentDto();
    final int memberNo = studyCommentDto.getNotiReceiver();
    NotifyDto notification = createNotification(memberNo, studyCommentDto);
    notificationRepository.save(notification);
    final String id = String.valueOf(notification.getRecipientId());
    this.eventRedisOperations.convertAndSend(getChannelName(id), notification);
  }

  private NotifyDto createNotification(final int memberNo, final StudyCommentDto studyCommentDto) {
    return NotifyDto.builder()
        .recipientId(memberNo)
        .link(createUrl(studyCommentDto))
        .message(createMessage(studyCommentDto)).build();
  }

  private String createUrl(final StudyCommentDto studyCommentDto) {
    int studyNo = studyCommentDto.getStudyNo();
    return "/study/view?studyNo=" + studyNo;
  }

  private String createMessage(final StudyCommentDto studyCommentDto) {
    String title = studyCommentDto.getTitle();
    return title + "글에 댓글이 달렸습니다!";
  }

  public SseEmitter subscribe(int memberNo) throws IOException {
    final String id = String.valueOf(memberNo);
    final SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
    //초반 연결용 메시지 (sse 최초 연결 시 503 에러 방지)
    emitter.send(SseEmitter.event()
        .id(id)
        .name("sse"));
    emitters.add(emitter);
    // onMessage 구현
    final MessageListener messageListener = (message, pattern) -> {
      final NotifyDto notificationResponse = serialize(message);
      sendToClient(emitter, id, notificationResponse);
    };

    this.redisMessageListenerContainer.addMessageListener(messageListener, ChannelTopic.of(getChannelName(id)));
    checkEmitterStatus(emitter, messageListener);
    return emitter;
  }

  private NotifyDto serialize(final Message message) {
    try {
      return this.objectMapper.readValue(message.getBody(), NotifyDto.class);
    } catch (IOException e) {
      throw new RuntimeException(String.format("유효하지 않은 reids message입니다. message= {%s}", message));
    }
  }


  private void sendToClient(SseEmitter emitter, String id, Object data) {
    try {
      emitter.send(SseEmitter.event()
          .id(id)
          .name("sse")
          .data(data));
    } catch (IOException e) {
      emitters.remove(emitter);
      log.error("SSE 연결이 올바르지 않습니다. 해당 memberID={}", id);
    }
  }

  private void checkEmitterStatus(final SseEmitter emitter, final MessageListener messageListener) {
    emitter.onCompletion(() -> {
      emitters.remove(emitter);
      this.redisMessageListenerContainer.removeMessageListener(messageListener);
    });
    emitter.onTimeout(() -> {
      emitters.remove(emitter);
      this.redisMessageListenerContainer.removeMessageListener(messageListener);
    });
  }

  private String getChannelName(final String memberId) {
    return "comment:noti:member:" + memberId;
  }

  @Transactional(readOnly = true)
  public NotificationsResponseDto findAllByMemberIdAndUnread(int memberNo) {
    List<NotifyDto> notifyList = notificationRepository.findAllByMemberIdAndUnread(memberNo);
    int unreadCount = notifyList.size();
    return NotificationsResponseDto.of(notifyList, unreadCount);
  }

  public void readNotification(int id) {
    NotifyDto notifyDto = notificationRepository.findById(id);
    if (notifyDto != null) {
      notificationRepository.updateReadById(id);
    }
  }

  public void readAllNotifications(int memberNo) {
    notificationRepository.updateNotificationRead(memberNo);
  }
}
