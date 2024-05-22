package com.mos.domain.member.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MemberEditDto {

  private Integer memberNo;
  @Email @NotBlank
  private String email;
  @NotBlank
  private String userName;
  private String photo;
  private String career;
  private String belong;
  private String biography;
  private String socialLink;
}
