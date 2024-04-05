package com.mos.domain.wiki.service;

import com.mos.domain.wiki.dto.WikiDto;
import java.util.List;

public interface WikiService {

  List<WikiDto> list();
  void add(WikiDto studyDto);
  WikiDto getByStudyNo(int studyNo);
}
