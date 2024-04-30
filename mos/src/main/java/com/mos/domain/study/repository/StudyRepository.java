package com.mos.domain.study.repository;

import com.mos.domain.member.dto.MemberStudyDto;
import com.mos.domain.study.dto.StudyDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Mapper
public interface StudyRepository {

  void add(StudyDto studyDto);

  List<StudyDto> findAll(long offset, int pageSize);

  StudyDto getByStudyNo(int studyNo);

  void delete(int studyNo);

  int update(StudyDto studyDto);
  int studyCount();

  boolean applyStudy(MemberStudyDto memberStudyDto);

}
