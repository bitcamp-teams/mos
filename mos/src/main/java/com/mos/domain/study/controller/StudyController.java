package com.mos.domain.study.controller;

import com.mos.domain.study.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/study")
public class StudyController {

  // 현재 스레드의 클래스를 파라미터로 받아서 로그 객체를 만든다.
  private static final Log log = LogFactory.getLog(Thread.currentThread().getClass());
  private final StudyService studyService;
  //스터디에 파일저장 / 이미지 옵티마이징 따로 없으므로 변수 추가 없음


  @GetMapping("list")
  public void list(Model model) {
    model.addAttribute(
        "studyList",
        studyService.list()
    );

  }
}
