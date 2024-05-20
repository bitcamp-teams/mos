package com.mos.domain.study.repository;

import com.mos.domain.study.dto.TagDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Mapper
public interface TagRepository {

  void add(TagDto tagDto);

  int addAll(@Param("tagList") List<TagDto> tagList);

  int delete(int tagNo);

  boolean deleteByStudyNo(int studyNo);

  List<TagDto> findAll();

  TagDto getByTagNo(int tagNo);

  int update(TagDto tagDto);

  void addTags(@Param("tagList") List<TagDto> tagList);

}
