package com.mos.domain.notify.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotifyDto {

  private int id;
  private int recipientId;
  private String message;
  private String link;
  private boolean read;
}
