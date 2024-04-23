package com.mos.domain.member.dto;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MemberDto implements Serializable {

  @Serial
  private static final long serialVersionUID = 100L;

  private int memberNo;
  private String email;
  @NotBlank @Size(min = 3)
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
