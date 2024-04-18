package com.mos.domain.wiki.repository;

import com.mos.domain.wiki.dto.WikiDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WikiRepository {

  List<WikiDto> listByStudyNo(int studyNo);

  List<WikiDto> listByWikiNo();

  WikiDto getByWikiNo(int wikiNo);

  int updateWiki(WikiDto wikiDto);

  void deleteWiki(int wikiNo);

  void add(WikiDto wikiDto);
}
