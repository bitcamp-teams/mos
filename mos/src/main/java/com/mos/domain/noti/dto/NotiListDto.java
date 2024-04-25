package com.mos.domain.noti.dto;

import lombok.Getter;

@Getter
public class NotiListDto {
  private int id;
  private int recipientId;
  private String message;
  private String link;
}
