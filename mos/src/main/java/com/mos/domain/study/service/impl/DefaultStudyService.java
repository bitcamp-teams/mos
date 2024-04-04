package com.mos.domain.study.service.impl;

import com.mos.domain.study.dto.StudyDto;
import com.mos.domain.study.repository.StudyRepository;
import com.mos.domain.study.service.StudyService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultStudyService implements StudyService {
  private final StudyRepository studyRepository;

  @Override
  public void add(StudyDto studyDto) {
    studyRepository.add(studyDto);
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
  public List<StudyDto> list() {
    return studyRepository.findAll();
  }
}
