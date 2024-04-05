package com.mos.domain.member.service;

import com.mos.domain.member.dto.MemberDto;
import com.mos.domain.member.dto.MemberJoinDto;
import com.mos.global.auth.dto.OAuthDto;

public interface MemberService {

    MemberDto get(String email);

  MemberDto getName(String username);
   MemberDto getNo(int no);

    MemberDto getUsername(String username);

    boolean existsByEmail(String email);

    int join(OAuthDto oauthDto);

}
