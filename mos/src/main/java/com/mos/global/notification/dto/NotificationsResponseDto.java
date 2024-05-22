package com.mos.global.notification.dto;

import com.mos.domain.notify.dto.NotificationDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationsResponseDto {
  private List<NotificationDto> notifications;
  private int unreadCount;

  public static NotificationsResponseDto of(List<NotificationDto> notifications, int count) {
    return new NotificationsResponseDto(notifications, count);
  }
}
