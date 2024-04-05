package com.mos.domain.study.service;

import com.mos.domain.study.dto.StudyDto;
import java.util.List;

public interface StudyService {

  List<StudyDto> list();
  void add(StudyDto studyDto);
  StudyDto getByStudyNo(int studyNo);

  void deleteStudy(int studyNo);
}
