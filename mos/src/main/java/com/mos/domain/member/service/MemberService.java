package com.mos.domain.member.service;

import com.mos.domain.comment.dto.StudyCommentDto;
import com.mos.domain.comment.dto.WikiCommentDto;
import com.mos.domain.member.dto.MemberDto;
import com.mos.domain.member.dto.MemberStudyDto;

import com.mos.domain.member.dto.MyStudiesDto;
import com.mos.domain.member.dto.UpdateFavoritesDto;
import com.mos.domain.study.dto.StudyDto;
import java.util.List;

import com.mos.domain.member.dto.MemberJoinDto;
import com.mos.domain.wiki.dto.WikiDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberService {

  MemberDto get(String email);

  MemberDto getName(String username);

  MemberDto getNo(int no);

  MemberDto getUsername(String username);

  boolean existsByEmail(String email);

  boolean existsByUserName(String nickname);

  int join(MemberJoinDto joinDto);

  Page<MemberStudyDto> findMyStudies(int no, Pageable page);

  void addFavorites(UpdateFavoritesDto updateFavoritesDto);

  List<MemberStudyDto> viewMyStudies(int no);

  int update(MemberDto member);

  int withdraw(int no);

  Page<WikiDto> findMyWiki(int memberNo, Pageable pageable);

  Page<StudyCommentDto> findMyStudyComment(int memberNo, Pageable pageable);

  Page<WikiCommentDto> findMyWikiComment(int memberNo, Pageable pageable);

  Page<MyStudiesDto> findListByStudyNo(int studyNo, int memberNo, Pageable page);

  Page<StudyDto> findLikedStudiseByNo(int memberNo, Pageable page);

  List<MemberDto> getAuthorizedMembers(int studyNo);
}

