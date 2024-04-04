package com.mos.domain.member.service;

import com.mos.domain.member.dto.MemberDto;

public interface MemberService {

  MemberDto get(String email);

   MemberDto getNo(int no);

    MemberDto getUsername(String username);

}
