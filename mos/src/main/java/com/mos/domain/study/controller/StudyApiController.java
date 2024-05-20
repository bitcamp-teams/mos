package com.mos.domain.study.controller;

import com.mos.domain.comment.service.CommentService;
import com.mos.domain.study.dto.StudyDto;
import com.mos.domain.study.service.StudyLikeService;
import com.mos.domain.study.service.StudyService;
import com.mos.domain.wiki.service.WikiService;
import com.mos.global.storage.service.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/study")
@Slf4j
public class StudyApiController {

  private final StudyService studyService;
  private final CommentService commentService;
  private final WikiService wikiService;
  private final StudyLikeService studyLikeService;


  @GetMapping("/list/{flag}")
  public ResponseEntity<?> list(@PathVariable String flag, int page, String searchText) {
    PageRequest pageRequest = PageRequest.of(page, 20);
    Page<StudyDto> studyList = studyService.list(pageRequest, flag, searchText);
    return ResponseEntity.ok().body(studyList);
  }



}
