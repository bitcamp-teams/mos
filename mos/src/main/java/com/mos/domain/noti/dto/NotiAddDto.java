package com.mos.domain.noti.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class NotiAddDto {
  private String message;
  private int recipientId;
  private String link;
}
