package com.mos.domain.wiki.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mos.domain.wiki.dto.WikiDto;

@Mapper
public interface WikiRepository {

  // 모두의 위키 mooduwiki에서 사용함
  List<WikiDto> listByWikiNo();

  List<WikiDto> listByStudyNo(int studyNo);

  WikiDto getByWikiNo(int wikiNo);

  int updateWiki(WikiDto wikiDto);

  void deleteWiki(int wikiNo);

  void add(WikiDto wikiDto);

  int getFirstWiki(int studyNo);

}
