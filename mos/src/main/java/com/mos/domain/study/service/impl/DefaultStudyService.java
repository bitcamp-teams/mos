package com.mos.domain.study.service.impl;

import com.mos.domain.member.dto.MemberStudyDto;
import java.util.List;
import com.mos.domain.study.dto.TagDto;
import com.mos.domain.study.repository.TagRepository;
import java.util.stream.Collectors;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.mos.domain.study.dto.StudyDto;
import com.mos.domain.study.repository.StudyRepository;
import com.mos.domain.study.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DefaultStudyService implements StudyService {

  private final StudyRepository studyRepository;
  private final TagRepository tagRepository;

  @Transactional
  @Override
  public void add(StudyDto studyDto) {
    studyRepository.add(studyDto);
    for (TagDto tagDto : studyDto.getTagList()) {
      tagDto.setStudyNo(studyDto.getStudyNo());
    }
    tagRepository.addAll(studyDto.getTagList());
  }

  @Override
  public StudyDto getByStudyNo(int studyNo) {
    return studyRepository.getByStudyNo(studyNo);
  }

  @Override
  public void deleteStudy(int studyNo) {
    studyRepository.delete(studyNo);
  }

  @Override
  public int update(StudyDto studyDto) {
    return studyRepository.update(studyDto);
  }

  // 조회수 카운트
  @Transactional
  @Override
  public StudyDto updateHitCount(int studyNo) {
    studyRepository.updateHitCount(studyNo);
    return studyRepository.getByStudyNo(studyNo);
  }

  @Override
  public Page<StudyDto> list(Pageable pageable) {
    List<StudyDto> studyList = studyRepository.findAll(pageable);
    long totalCount = studyRepository.countAll(); // 전체 데이터 개수 구하기
    return new PageImpl<>(studyList, pageable, totalCount);
  }



  @Override
  public List<TagDto> getAllTags() {
    return tagRepository.findAll();
  }

  @Transactional
  @Override
  public boolean applyStudy(MemberStudyDto memberStudyDto) {
      studyRepository.applyStudy(memberStudyDto);
      return true;
  }

  @Override
  public Page<StudyDto> listAll(Pageable pageable) {
    return studyRepository.listAll(pageable);
  }

  @Override
  public Page<StudyDto> searchByTypeAndKeyword(String type, String keyword, Pageable pageable) {

    List<StudyDto> searchResult;
    try {
    int count = studyRepository.searchCount(type, keyword);
    List<StudyDto> list = studyRepository.searchByStudy(type, keyword, pageable);
    return new PageImpl<>(list, pageable, count);
    } catch(Exception e) {
      throw new IllegalArgumentException("유효하지 않은 검색 유형입니다.");
    }
  }

  //  @Override
//  public Page<StudyDto> searchByTypeAndKeyword(String type, String keyword, Pageable pageable) {
//    try {
//      int count;
//      List<StudyDto> list;
//
//      if ("title".equals(type)) {
//        count = studyRepository.searchCountByTitle(keyword);
//        list = studyRepository.searchByTitle(keyword, pageable);
//      } else if ("introduction".equals(type)) {
//        count = studyRepository.searchCountByIntroduction(keyword);
//        list = studyRepository.searchByIntroduction(keyword, pageable);
//      } else if ("tag".equals(type)) {
//        count = studyRepository.searchCountByTag(keyword);
//        list = studyRepository.searchByTag(keyword, pageable);
//      } else {
//        throw new IllegalArgumentException("유효하지 않은 검색 유형입니다.");
//      }
//
//      return new PageImpl<>(list, pageable, count);
//    } catch (Exception e) {
//      throw new IllegalArgumentException("검색 중 오류가 발생했습니다.");
//    }
//  }

}
