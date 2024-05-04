package com.mos.global.notification.controller;

import com.mos.domain.member.dto.MemberDto;
import com.mos.global.notification.dto.NotificationsResponseDto;
import com.mos.global.notification.service.NotificationService;
import com.mos.global.auth.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class NotificationController {

  private final NotificationService notificationService;

  @GetMapping(value = "/subscribe/{memberNo}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public SseEmitter subscribe(@PathVariable int memberNo,
      @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "")
      String lastEventId) throws IOException {
    return notificationService.subscribe(memberNo, lastEventId);
  }

  @GetMapping("/notifications")
  public ResponseEntity<NotificationsResponseDto> notifications(@LoginUser MemberDto loginUser) {
    return ResponseEntity.ok()
        .body(notificationService.findAllByMemberIdAndUnread(loginUser.getMemberNo()));
  }

  @PutMapping("/notifications/{id}")
  public ResponseEntity<Void> readNotification(@PathVariable int id) {
    notificationService.readNotification(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/notifications/all")
  public ResponseEntity<Void> readAllNotifications(@LoginUser MemberDto loginUser) {
    notificationService.readAllNotifications(loginUser.getMemberNo());
    return ResponseEntity.noContent().build();
  }

}
