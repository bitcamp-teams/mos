package com.mos.domain.wiki.service.impl;

import com.mos.domain.wiki.dto.WikiDto;
import com.mos.domain.wiki.repository.WikiRepository;
import com.mos.domain.wiki.service.WikiService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultWikiService implements WikiService {

  private final WikiRepository wikiRepository;

  @Override
  public List<WikiDto> listByStudyNo(int studyNo) {
    return wikiRepository.listByStudyNo(studyNo);
  }

  @Override
  public WikiDto getByWikiNo(int wikiNo) {
    return wikiRepository.getByWikiNo(wikiNo);
  }
}
