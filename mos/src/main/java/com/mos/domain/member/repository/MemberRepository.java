package com.mos.domain.member.repository;


import com.mos.domain.member.dto.MemberDto;
import com.mos.domain.member.dto.MemberJoinDto;
import com.mos.global.auth.dto.OAuthDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberRepository {

    MemberDto findByEmail(String email);
    int add(OAuthDto oauthDto);
    MemberDto save(MemberDto member);
    MemberDto findByNo(int no);
    MemberDto findByUsername(String username);
    boolean existsByEmail(String email);
}
