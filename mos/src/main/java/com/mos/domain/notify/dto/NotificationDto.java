package com.mos.domain.notify.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotificationDto {
  private int notiNo;
  private int memberNo;
  private String message;
  private String link;
}
