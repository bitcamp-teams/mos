package com.mos.domain.member.repository;


import com.mos.domain.comment.dto.StudyCommentDto;
import com.mos.domain.comment.dto.WikiCommentDto;
import com.mos.domain.member.dto.MemberDto;
import com.mos.domain.member.dto.MemberJoinDto;
import com.mos.domain.member.dto.MemberStudyDto;
import com.mos.domain.member.dto.MyStudiesDto;
import com.mos.domain.member.dto.MyStudiesUpdateDto;
import com.mos.domain.member.dto.UpdateFavoritesDto;
import java.util.List;

import com.mos.domain.wiki.dto.WikiDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

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
  List<MemberStudyDto> findMyStudies(int no, Pageable page);

  int mystudiesCount(int memberNo);

  // 스터디 즐겨찾기 추가
  void addFavorites(UpdateFavoritesDto memberStudyDto);

  // 회원이 참여한 스터디 상세보기
  List<MemberStudyDto> viewMyStudies(int no);

  int update(MemberDto member);

  // 회원 탈퇴 기능 구현.
  int withdraw(int no);

  // 회원이 쓴 위키글
  List<WikiDto> findMyWiki(@Param("memberNo") int memberNo, @Param("pageable") Pageable pageable);

  int myWikiCount(int memberNo);

  // 회원이 쓴 스터디 댓글
  List<StudyCommentDto> findMyStudyComment(@Param("memberNo") int memberNo, @Param("pageable") Pageable pageable);

  int myStudyCommentCount(int memberNo);

  // 회원이 쓴 위키 댓글
  List<WikiCommentDto> findMyWikiComment(@Param("memberNo") int memberNo, @Param("pageable") Pageable pageable);

  int myWikiCommentCount(int memberNo);

  List<MyStudiesDto> findListByStudyNo(int studyNo, int memberNo, long offset, int pageSize);

  int acceptCount(int studyNo, int memberNo);

  void updateStatus(MyStudiesUpdateDto updateDto);

  List<MemberDto> findAuthorizedMembersByStudyNo(int studyNo);
}
