package com.mos.global.auth.dto;

import com.mos.domain.member.dto.MemberDto;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class KakaoDto {

  private long id;
  private String email;
  private String nickname;
  private MemberDto member;

}