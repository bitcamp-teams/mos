package com.mos.domain.member.dto;

import java.io.Serializable;
import java.sql.Date;

import lombok.*;

import javax.validation.constraints.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MemberDto implements Serializable {

  private int memberNo;
  private String email;
  @NotBlank
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
