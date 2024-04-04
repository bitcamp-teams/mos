package com.mos.domain.member.dto;

import java.sql.Date;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class MemberDto {

  private int no;
  private String email;
  private String userName;
  private Date createDate;
  private Date updateDate;
  private String status;
  private int score;
  private String biography;
  private String belong;
  private String jogGroup;
  private String socialLink;
  private String filePath;
  private String location;
}
