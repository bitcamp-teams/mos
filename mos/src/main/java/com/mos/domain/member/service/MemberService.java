package com.mos.domain.member.service;

import com.mos.domain.member.dto.MemberDto;
import com.mos.domain.member.dto.MemberStudyDto;
import com.mos.domain.study.dto.StudyDto;
import java.util.List;

public interface MemberService {

  MemberDto get(String email);

   MemberDto getNo(int no);

   MemberDto getUsername(String username);

   List<MemberStudyDto> findMyStudies(int no);

}
