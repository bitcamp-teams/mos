package com.mos.global.notification.service;

import com.mos.domain.comment.dto.StudyCommentDto;
import com.mos.domain.notify.dto.NotificationDto;
import com.mos.domain.notify.dto.NotifyDto;
import com.mos.global.notification.dto.NotificationsResponseDto;
import com.mos.global.notification.event.CommentAndAuthorIdEvent;
import com.mos.global.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class NotificationService {

  private final NotificationRepository notificationRepository;
  private final SseEmitterService sseEmitterService;
  private final RedisMessageService redisMessageService;


  @Transactional(propagation = Propagation.REQUIRES_NEW)
  @TransactionalEventListener
  public void send(CommentAndAuthorIdEvent commentAndAuthorIdEvent) {
    final StudyCommentDto studyCommentDto = commentAndAuthorIdEvent.getStudyCommentDto();
    final int memberNo = studyCommentDto.getNotiReceiver();
    NotifyDto notification = createNotification(memberNo, studyCommentDto);
    notificationRepository.save(notification);
    final String id = String.valueOf(notification.getRecipientId());

    redisMessageService.publish(id, notification);
  }



  public SseEmitter subscribe(int memberNo, String lastEventId) {
    String channel = String.valueOf(memberNo);
    SseEmitter emitter = sseEmitterService.createEmitter(channel);
    //초반 연결용 메시지 (sse 최초 연결 시 503 에러 방지)
    sseEmitterService.sendToClient(emitter, channel, "EventStream Created. [memberNo=" + channel + "]");

    redisMessageService.subscribe(channel);

    checkEmitterStatus(emitter, channel);

    return emitter;
  }

  @Transactional(readOnly = true)
  public NotificationsResponseDto findAllByMemberIdAndUnread(int memberNo) {
    List<NotificationDto> notifyList = notificationRepository.findAllByMemberIdAndUnread(memberNo);
    int unreadCount = notifyList.size();
    return NotificationsResponseDto.of(notifyList, unreadCount);
  }

  public void readNotification(int id) {
    NotificationDto notifyDto = notificationRepository.findById(id);
    if (notifyDto != null) {
      notificationRepository.updateReadById(id);
    }
  }

  public void readAllNotifications(int memberNo) {
    notificationRepository.updateNotificationRead(memberNo);
  }


  private void checkEmitterStatus(final SseEmitter emitter, final String id) {
    emitter.onCompletion(() -> {
      sseEmitterService.deleteEmitter(id);
      redisMessageService.removeSubscribe(id);
    });
    emitter.onTimeout(() -> {
      sseEmitterService.deleteEmitter(id);
      emitter.complete();
    });
    emitter.onError(throwable -> emitter.complete());
  }

  private NotifyDto createNotification(final int memberNo, final StudyCommentDto studyCommentDto) {
    return NotifyDto.builder()
        .recipientId(memberNo)
        .link(createUrl(studyCommentDto))
        .message(createMessage(studyCommentDto))
        .build();
  }

  private String createUrl(final StudyCommentDto studyCommentDto) {
    int studyNo = studyCommentDto.getStudyNo();
    return "/study/view?studyNo=" + studyNo;
  }

  private String createMessage(final StudyCommentDto studyCommentDto) {
    String title = studyCommentDto.getTitle();
    return title + "글에 댓글이 달렸습니다!";
  }
}
