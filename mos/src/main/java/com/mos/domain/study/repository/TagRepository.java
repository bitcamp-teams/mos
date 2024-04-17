package com.mos.domain.study.repository;

import com.mos.domain.study.dto.TagDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TagRepository {

  void add(TagDto tagDto);

  //들어오는 파라미터가 다수이니 반드시 명시해야 합니다!!
  int addAll(@Param("tagList") List<TagDto> tagList);

  int delete(int tagNo);

  List<TagDto> findAll();

  TagDto getByTagNo(int tagNo);

  int update(TagDto tagDto);

  void addTags(@Param("tagList") List<TagDto> tagList);

}
