package com.mos.domain.member.repository;


import com.mos.domain.member.dto.MemberDto;
import com.mos.domain.member.dto.MemberJoinDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberRepository {

  MemberDto findByEmail(String email);
  int add(MemberJoinDto joinDto);
  MemberDto save(MemberDto member);
  MemberDto findByNo(int no);
  MemberDto findByUsername(String username);
  boolean existsByEmail(String email);
  boolean existsByUserName(String username);
}
