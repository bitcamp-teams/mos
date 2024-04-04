package com.mos.domain.member.repository;

import com.mos.domain.member.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberRepository {

  MemberDto findByEmail(String email);
  MemberDto findByUsername(String username);
}
