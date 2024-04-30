package com.mos.domain.notify.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotifyAddDto {
  private String message;
  private int recipientId;
  private String link;
}
