package com.mos.domain.wiki.service;

import java.util.List;

import com.mos.domain.wiki.dto.JstreeWikiDto;

public interface WikiApiService {

  // 전체 노드 조회
  List<JstreeWikiDto> getWikiTitleTree(int studyNo);

  // 노드 추가
  int addNode(JstreeWikiDto jstreeWikiDto);

  JstreeWikiDto getNodeByWikiNo(int wikiNo);
}