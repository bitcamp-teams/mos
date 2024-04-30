package com.mos.domain.study.service;

import com.mos.domain.member.dto.MemberStudyDto;
import java.util.List;
import com.mos.domain.study.dto.StudyDto;
import com.mos.domain.study.dto.TagDto;

public interface StudyService {

  List<StudyDto> list();

  void add(StudyDto studyDto);

  StudyDto getByStudyNo(int studyNo);

  void deleteStudy(int studyNo);

  int update(StudyDto studyDto);

  List<TagDto> getAllTags();

  boolean applyStudy(MemberStudyDto memberStudyDto);

  List<StudyDto> searchByTypeAndKeyword(String type, String keyword);
}