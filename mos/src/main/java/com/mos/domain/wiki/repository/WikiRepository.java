package com.mos.domain.wiki.repository;

import com.mos.domain.wiki.dto.WikiDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WikiRepository {

  void add(WikiDto studyDto);

  List<WikiDto> findAll();

  WikiDto getByStudyNo(int studyNo);

}
