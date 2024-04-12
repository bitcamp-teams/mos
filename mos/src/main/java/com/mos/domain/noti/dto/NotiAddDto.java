package com.mos.domain.noti.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotiAddDto {
  private String message;
  private int senderId;
  private int recipientId;
  private String link;
}
