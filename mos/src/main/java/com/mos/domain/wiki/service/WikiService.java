package com.mos.domain.wiki.service;

import java.util.List;

import com.mos.domain.wiki.dto.WikiDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WikiService {

  List<WikiDto> listByStudyNo(int studyNo);

  Page<WikiDto> listByWikiNo(Pageable page);

  WikiDto getByWikiNo(int wikiNo);

  int updateWiki(WikiDto wikiDto);

  void deleteWiki(int wikiNo);

  void add(WikiDto wikiDto);

  // 스터디의 첫번째 위키 보기
  // 작성된 위키 없는 경우 0을 반환
  int getFirstWikiNo(int studyNo);

  // 조회수 증가
  WikiDto updateHitCount(int wikiNo);

  Integer findWikiNoByStudyNo(int studyNo);

  void updateLikeCount(int wikiNo, int likes);
}
