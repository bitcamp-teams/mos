package com.mos.domain.member.repository;


import com.mos.domain.member.dto.MemberDto;
import com.mos.domain.member.dto.MemberJoinDto;
import com.mos.domain.member.dto.MemberStudyDto;
import java.util.List;
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

    // 회원이 참여한 스터디 목록 조회
    List<MemberStudyDto> findMyStudies(int no);

    // 회원이 참여한 스터디 상세보기
  List<MemberStudyDto> viewMyStudies(int no);

  int update(MemberDto member);

}
