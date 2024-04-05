package com.mos.domain.wiki.controller;

import com.mos.domain.wiki.dto.WikiDto;
import com.mos.domain.wiki.service.WikiService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/wiki")
public class WikiController {

  // 현재 스레드의 클래스를 파라미터로 받아서 로그 객체를 만든다.
  private static final Log log = LogFactory.getLog(Thread.currentThread().getClass());
  private final WikiService wikiService;
  //스터디에 파일저장 / 이미지 옵티마이징 따로 없으므로 변수 추가 없음

  @GetMapping("form")
  public void form() throws Exception {
  }

  @PostMapping("add")
  public String add(WikiDto studyDto) {
    wikiService.add(studyDto);
    return "redirect:list";
  }

  @GetMapping("view")
  public void view(int studyNo, Model model) throws Exception {
    WikiDto studyDto = wikiService.getByStudyNo(studyNo);
    if(studyDto==null) {
      throw new Exception("해당 스터디 번호가 존재하지 않습니다.");
    }
    model.addAttribute("study", studyDto);
  }


  @GetMapping("list")
  public void list(Model model) {
    model.addAttribute(
        "studyList",
        wikiService.list()
    );
  }


}
