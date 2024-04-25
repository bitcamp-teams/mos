package com.mos.domain.notify.dto;

import lombok.Data;

@Data
public class NotifyDto {

  private int id;
  private int recipientId;
  private String message;
  private String link;
  private boolean read;
}
