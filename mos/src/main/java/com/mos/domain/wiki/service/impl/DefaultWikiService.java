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
  public void add(WikiDto studyDto) {
    wikiRepository.add(studyDto);
  }

  @Override
  public WikiDto getByStudyNo(int studyNo) {
    return wikiRepository.getByStudyNo(studyNo);
  }
  @Override
  public List<WikiDto> list() {
    return wikiRepository.findAll();
  }
}
