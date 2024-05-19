package com.mos.domain.study.service.impl;

import com.mos.domain.member.dto.MemberStudyDto;

import com.mos.domain.study.dto.AttachedFileStudyDto;
import com.mos.global.storage.repository.AttachedFileRepository;
import java.util.List;

import com.mos.domain.study.dto.TagDto;
import com.mos.domain.study.repository.TagRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.mos.domain.study.dto.StudyDto;
import com.mos.domain.study.repository.StudyRepository;
import com.mos.domain.study.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class DefaultStudyService implements StudyService {

  private final StudyRepository studyRepository;
  private final TagRepository tagRepository;
  private final AttachedFileRepository attachedFileRepository;


  @Override
  public void add(StudyDto studyDto) {
    studyRepository.add(studyDto);
    for (TagDto tagDto : studyDto.getTagList()) {
      tagDto.setStudyNo(studyDto.getStudyNo());
    }
    tagRepository.addAll(studyDto.getTagList());
  }

  @Transactional(readOnly = true)
  @Override
  public StudyDto getByStudyNo(int studyNo) {
    return studyRepository.getByStudyNo(studyNo);
  }

  @Transactional
  @Override
  public void deleteStudy(int studyNo) {
    studyRepository.delete(studyNo);
  }

  @Override
  public int update(StudyDto studyDto) {
    return studyRepository.update(studyDto);
  }

  // 조회수 카운트
  @Override
  public StudyDto updateHitCount(int studyNo) {
    studyRepository.updateHitCount(studyNo);
    return studyRepository.getByStudyNo(studyNo);
  }

  @Transactional(readOnly = true)
  @Override
  public Page<StudyDto> list(Pageable pageable) {
    List<StudyDto> studyList = studyRepository.findAll(pageable);
    long totalCount = studyRepository.countAll(); // 전체 데이터 개수 구하기
    return new PageImpl<>(studyList, pageable, totalCount);
  }

  @Transactional(readOnly = true)
  @Override
  public Page<StudyDto> list(Pageable pageable, String flag, String searchText) {
    List<StudyDto> studyList = studyRepository.findAll(pageable, flag, searchText);
    long totalCount = studyRepository.countAll(); // 전체 데이터 개수 구하기
    return new PageImpl<>(studyList, pageable, totalCount);
  }

  @Transactional(readOnly = true)
  @Override
  public List<TagDto> getAllTags() {
    return tagRepository.findAll();
  }

  @Override
  public boolean applyStudy(MemberStudyDto memberStudyDto) {
    studyRepository.applyStudy(memberStudyDto);
    return true;
  }

  @Transactional(readOnly = true)
  @Override
  public Page<StudyDto> listAll(Pageable pageable) {
    return studyRepository.listAll(pageable);
  }

  @Transactional(readOnly = true)
  @Override
  public Page<StudyDto> searchByTypeAndKeyword(String type, String keyword, Pageable pageable) {

    List<StudyDto> searchResult;
    try {
      int count = studyRepository.searchCount(type, keyword);
      List<StudyDto> list = studyRepository.searchByStudy(type, keyword, pageable);
      return new PageImpl<>(list, pageable, count);
    } catch (Exception e) {
      throw new IllegalArgumentException("유효하지 않은 검색 유형입니다.");
    }
  }

  @Override
  public void updateLikeCount(int studyNo, int likeCount) {
    studyRepository.updateLikeCount(studyNo, likeCount);
  }

  @Override
  public List<AttachedFileStudyDto> getAttachedFiles(int studyNo) {
    return attachedFileRepository.findAllByStudyNo(studyNo);
  }

  @Override
  public AttachedFileStudyDto getAttachedFile(int fileNo) {
    return attachedFileRepository.findByFileNo(fileNo);
  }

  @Override
  public int deleteAttachedFile(int fileNo) {
    return attachedFileRepository.delete(fileNo);
  }
}
