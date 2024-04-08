package com.mos.domain.wiki.service;

import com.mos.domain.wiki.dto.WikiDto;
import java.util.List;

public interface WikiService {

  List<WikiDto> listByStudyNo(int studyNo);

  WikiDto getByWikiNo(int wikiNo);
}
