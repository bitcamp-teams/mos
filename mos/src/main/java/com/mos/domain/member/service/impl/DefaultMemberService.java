package com.mos.domain.member.service.impl;

import com.mos.domain.comment.dto.StudyCommentDto;
import com.mos.domain.comment.dto.WikiCommentDto;
import com.mos.domain.member.dto.MemberDto;
import com.mos.domain.member.dto.MemberStudyDto;
import com.mos.domain.member.dto.MemberJoinDto;
import com.mos.domain.member.dto.MyStudiesDto;
import com.mos.domain.member.dto.MyStudiesUpdateDto;
import com.mos.domain.member.dto.UpdateFavoritesDto;
import com.mos.domain.member.repository.MemberRepository;
import com.mos.domain.member.service.MemberService;
import com.mos.domain.study.dto.StudyDto;
import com.mos.domain.study.repository.StudyRepository;

import java.util.List;

import com.mos.domain.wiki.dto.WikiDto;
import com.mos.domain.wiki.repository.WikiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class DefaultMemberService implements MemberService {


  private final MemberRepository memberRepository;
  private final StudyRepository studyRepository;
  private final WikiRepository wikiRepository;

  @Override
  public MemberDto get(String email) {
    return memberRepository.findByEmail(email);
  }

  @Override
  public MemberDto getName(String username) {
    return memberRepository.findByUsername(username);
  }

  @Override
  public MemberDto getNo(int no) {
    return memberRepository.findByNo(no);
  }

  @Override
  public MemberDto getUsername(String username) {
    return memberRepository.findByUsername(username);
  }

  @Override
  public Page<MemberStudyDto> findMyStudies(int no, Pageable page) {
    MemberDto member = memberRepository.findByNo(no);
    if (member == null) {
      throw new IllegalArgumentException("해당 사용자를 찾을 수 없습니다: " + no);
    }

    List<MemberStudyDto> myStudy = memberRepository.findMyStudies(no, page);
    int count = memberRepository.mystudiesCount(no);
    if (myStudy == null) {
      throw new IllegalStateException("회원의 스터디 목록을 가져오는 데 실패했습니다.");
    }
    return new PageImpl<>(myStudy, page, count);
  }

  @Transactional(readOnly = true)
  @Override
  public Page<MyStudiesDto> findListByStudyNo(int studyNo, int memberNo, Pageable page) {
    List<MyStudiesDto> list = memberRepository.findListByStudyNo(studyNo, memberNo, page.getOffset(), page.getPageSize());
    int count = memberRepository.acceptCount(studyNo, memberNo);
    return new PageImpl<>(list, page, count);
  }

  @Override
  public void addFavorites(UpdateFavoritesDto updateFavoritesDto) {
    memberRepository.addFavorites(updateFavoritesDto);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public int join(MemberJoinDto joinDto) {
    System.out.println("joinDto = " + joinDto);
    return memberRepository.add(joinDto);
  }

  @Override
  public boolean existsByEmail(String email) {
    return memberRepository.existsByEmail(email);
  }

  @Override
  public List<MemberStudyDto> viewMyStudies(int no) {
    StudyDto studyDto = studyRepository.getByStudyNo(no);

    List<MemberStudyDto> myStudy = memberRepository.viewMyStudies(no);
    return myStudy;
  }

  @Transactional
  @Override
  public int update(MemberDto member) {
    return memberRepository.update(member);
  }

  @Transactional
  @Override
  public int withdraw(int no) {
    return memberRepository.withdraw(no);
  }

  @Override
  public boolean existsByUserName(String username) {
    return memberRepository.existsByUserName(username);
  }

  @Override
  public Page<WikiDto> findMyWiki(int memberNo, Pageable pageable) {
    List<WikiDto> myWiki = memberRepository.findMyWiki(memberNo, pageable);
    int count = memberRepository.myWikiCount(memberNo);
    return new PageImpl<>(myWiki, pageable, count);
  }

  @Override
  public Page<StudyCommentDto> findMyStudyComment(int memberNo, Pageable pageable) {
    List<StudyCommentDto> myStudyComment = memberRepository.findMyStudyComment(memberNo, pageable);
    int count = memberRepository.myStudyCommentCount(memberNo);
    return new PageImpl<>(myStudyComment, pageable, count);
  }

  @Override
  public Page<WikiCommentDto> findMyWikiComment(int memberNo, Pageable pageable) {
    List<WikiCommentDto> myWikiComment = memberRepository.findMyWikiComment(memberNo, pageable);
    int count = memberRepository.myWikiCommentCount(memberNo);
    return new PageImpl<>(myWikiComment, pageable, count);
  }

  public void updateStatus(MyStudiesUpdateDto updateDto) {
    memberRepository.updateStatus(updateDto);
  }

  @Override
  public Page<StudyDto> findLikedStudiseByNo(int memberNo, Pageable pageable) {

    return null;
  }
}
