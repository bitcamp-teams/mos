package com.mos.domain.member.repository;

import com.mos.domain.member.dto.MemberDto;
import com.mos.domain.member.dto.MemberJoinDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberRepository {

  int add(MemberJoinDto joinDto);

  MemberDto findByEmail(String email);
}
