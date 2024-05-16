package com.mos.domain.wiki.service.impl;

import com.mos.domain.wiki.dto.WikiDto;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mos.domain.wiki.dto.JstreeWikiDto;
import com.mos.domain.wiki.repository.WikiApiRepository;
import com.mos.domain.wiki.service.WikiApiService;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DefaultWikiApiService implements WikiApiService {

  private final WikiApiRepository wikiApiRepository;

  @Override
  public List<JstreeWikiDto> getWikiTitleTree(int studyNo) {
    return wikiApiRepository.findByStudyNo(studyNo);
  }

  @Transactional
  @Override
  public int addNode(JstreeWikiDto jstreeWikiDto) {
    // addNode
    return wikiApiRepository.saveNode(jstreeWikiDto);
  }

  @Override
  public JstreeWikiDto getNodeByWikiNo(int wikiNo) {
    return wikiApiRepository.findByWikiNo(wikiNo);
  }

  @Override
  public void patchWikiByWikiNo(JstreeWikiDto jstreeWikiDto) {
    wikiApiRepository.updateByWikiNo(jstreeWikiDto);
  }

  @Override
  public void deleteWikiByWikiNo(int wikiNo) {
    wikiApiRepository.deleteWikiByWikiNo(wikiNo);
  }

  @Override
  public WikiDto getWikiContent(int wikiNo) {
    return wikiApiRepository.findWikiByWikiNo(wikiNo);
  }

  @Override
  public Page<WikiDto> getList(Pageable page) {
    List<WikiDto> all = wikiApiRepository.findAll(page);
    return new PageImpl<>(all, page, all.size());
  }
}
