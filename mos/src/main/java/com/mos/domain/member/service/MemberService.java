package com.mos.domain.member.service;

import com.mos.domain.member.dto.MemberDto;
import com.mos.domain.member.dto.MemberStudyDto;
import com.mos.domain.study.dto.StudyDto;
import java.util.List;
import com.mos.domain.member.dto.MemberJoinDto;

public interface MemberService {

    MemberDto get(String email);

  MemberDto getName(String username);
   MemberDto getNo(int no);

   MemberDto getUsername(String username);

   List<MemberStudyDto> findMyStudies(int no);

    boolean existsByEmail(String email);

    int join(MemberJoinDto joinDto);

    List<MemberStudyDto> viewMyStudies(int no);
}
