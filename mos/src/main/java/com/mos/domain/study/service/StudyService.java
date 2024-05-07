package com.mos.domain.study.service;

import com.mos.domain.member.dto.MemberStudyDto;
import java.util.List;
import com.mos.domain.study.dto.StudyDto;
import com.mos.domain.study.dto.TagDto;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudyService {

  Page<StudyDto> list(Pageable pageable);

  void add(StudyDto studyDto);

  StudyDto getByStudyNo(int studyNo);

  void deleteStudy(int studyNo);

  int update(StudyDto studyDto);

  StudyDto updateHitCount(int studyNo);

  List<TagDto> getAllTags();

  boolean applyStudy(MemberStudyDto memberStudyDto);

  Page<StudyDto> listAll(Pageable pageable);

  Page<StudyDto> searchByTypeAndKeyword(String type, String keyword, Pageable pageable);
}