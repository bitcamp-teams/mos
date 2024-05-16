package com.mos.domain.wiki.service;

import com.mos.domain.wiki.dto.WikiDto;
import java.util.List;

import com.mos.domain.wiki.dto.JstreeWikiDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WikiApiService {

  // 전체 노드 조회
  List<JstreeWikiDto> getWikiTitleTree(int studyNo);

  // 노드 추가
  int addNode(JstreeWikiDto jstreeWikiDto);

  JstreeWikiDto getNodeByWikiNo(int wikiNo);

  void patchWikiByWikiNo(JstreeWikiDto jstreeWikiDto);

  void deleteWikiByWikiNo(int wikiNo);

  WikiDto getWikiContent(int wikiNo);

  Page<WikiDto> getList(Pageable pageable);
}
