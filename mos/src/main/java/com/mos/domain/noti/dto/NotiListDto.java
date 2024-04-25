package com.mos.domain.noti.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
public class NotiListDto {
  private int id;
  private int recipientId;
  private String message;
  private String link;
//  private long offset;
//  private int pageSize;
}
