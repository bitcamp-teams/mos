package com.mos.domain.wiki.service;

import com.mos.domain.wiki.dto.WikiDto;
import java.util.List;

public interface WikiService {

  List<WikiDto> listByStudyNo(int studyNo);

  List<WikiDto> listByWikiNo();

  WikiDto getByWikiNo(int wikiNo);

  int updateWiki(WikiDto wikiDto);

  void deleteWiki(int wikiNo);

  void add(WikiDto wikiDto);
}
