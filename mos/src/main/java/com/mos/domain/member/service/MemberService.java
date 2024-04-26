package com.mos.domain.member.service;

import com.mos.domain.comment.dto.StudyCommentDto;
import com.mos.domain.comment.dto.WikiCommentDto;
import com.mos.domain.member.dto.MemberDto;
import com.mos.domain.member.dto.MemberStudyDto;

import com.mos.domain.member.dto.MyStudiesDto;
import java.util.List;

import com.mos.domain.member.dto.MemberJoinDto;
import com.mos.domain.wiki.dto.WikiDto;

public interface MemberService {

  MemberDto get(String email);

  MemberDto getName(String username);

  MemberDto getNo(int no);

  MemberDto getUsername(String username);

  boolean existsByEmail(String email);

  boolean existsByUserName(String nickname);

  int join(MemberJoinDto joinDto);

  List<MemberStudyDto> findMyStudies(int no);


  List<MemberStudyDto> viewMyStudies(int no);

  int update(MemberDto member);

  int withdraw(int no);

  List<WikiDto> findMyWiki(int no);

  List<StudyCommentDto> findMyStudyComment(int no);

  List<WikiCommentDto> findMyWikiComment(int no);

  List<MyStudiesDto> findListByStudyNo(int studyNo, int memberNo);
}
