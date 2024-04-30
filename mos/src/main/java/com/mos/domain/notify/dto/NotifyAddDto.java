package com.mos.domain.notify.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class NotifyAddDto {
  private String message;
  private int recipientId;
  private String link;
}
