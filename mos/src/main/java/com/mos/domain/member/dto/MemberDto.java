package com.mos.domain.member.dto;

import java.io.Serializable;
import java.sql.Date;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MemberDto implements Serializable {

  private int no;
  private String email;
  private String userName;
  private Date createDate;
  private Date updateDate;
  private String stat;
  private int score;
  private String biography;
  private String photo;
  private String belong;
  private String jobGroup;
  private String socialLink;
  private String filePath;
  private String location;
  private String platform;

}
