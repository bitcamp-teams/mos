package com.mos.domain.member.dto;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MemberDto {

  private int memberNo;
  private String email;
  private String userName;
  private Date createdDate;
  private Date updatedDate;
  private String stat;
  private int score;
  private String biography;
  private String photo;
  private String belong;
  private String career;
  private String jobGroup;
  private String socialLink;
  private String filePath;
  private String location;
  private String platform;

}
