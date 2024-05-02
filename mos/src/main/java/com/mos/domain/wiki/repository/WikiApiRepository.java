package com.mos.domain.wiki.repository;

import com.mos.domain.wiki.dto.WikiDto;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mos.domain.wiki.dto.JstreeWikiDto;

@Mapper
public interface WikiApiRepository {

  // studyNo에 따라 DB에 있는 데이터를 JSON 타입대로 가져옴
  List<JstreeWikiDto> findByStudyNo(int studyNo);

  // Jstree에서 json 형태의 문자열을 RequestBody에 넣고 POST요청으로 보낸 것을 받아서 여기서 처리함.
  int saveNode(JstreeWikiDto jstreeWikiDto);

  JstreeWikiDto findByWikiNo(int wikiNo);

  void updateByWikiNo(JstreeWikiDto jstreeWikiDto);

  void deleteWikiByWikiNo(int wikiNo);

  WikiDto findWikiByWikiNo(int wikiNo);
}
