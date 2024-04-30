package com.mos.domain.study.repository;

import com.mos.domain.member.dto.MemberStudyDto;
import com.mos.domain.study.dto.StudyDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudyRepository {

  void add(StudyDto studyDto);

  List<StudyDto> findAll();

  StudyDto getByStudyNo(int studyNo);

  void delete(int studyNo);

  int update(StudyDto studyDto);

  boolean applyStudy(MemberStudyDto memberStudyDto);

  List<StudyDto> searchByTitle(String keyword);

  List<StudyDto> searchByIntroduction(String keyword);

  List<StudyDto> searchByTag(String keyword);
}
