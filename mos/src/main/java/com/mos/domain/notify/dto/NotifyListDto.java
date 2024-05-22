package com.mos.domain.notify.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class NotifyListDto {
  private int id;
  private int recipientId;
  private String message;
  private String link;
  private Timestamp createdDate;
}
