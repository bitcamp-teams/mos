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

  //스터디의 첫번째 위키 보기
  //작성된 위키 없는 경우 0을 반환
  int getFirstWikiNo(int studyNo);

//  List<WikiDto> listMyWikiNo();
}
