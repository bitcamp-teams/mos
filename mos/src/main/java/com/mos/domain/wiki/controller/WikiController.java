package com.mos.domain.wiki.controller;

import com.mos.domain.comment.dto.WikiCommentDto;
import com.mos.domain.comment.service.CommentService;
import com.mos.domain.study.service.impl.DefaultStudyService;
import com.mos.domain.wiki.dto.WikiDto;
import com.mos.domain.wiki.service.WikiService;
import java.util.List;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/wiki")
public class WikiController {

  private static final Log log = LogFactory.getLog(Thread.currentThread().getClass());
  private final WikiService wikiService;
    private final CommentService commentService;

    @GetMapping("form")
    public void form(@RequestParam int studyNo, Model model) throws Exception {

      model.addAttribute("studyNo", studyNo);
    }

    @PostMapping("add")
    public String add(@ModelAttribute WikiDto wikiDto,
                      @RequestParam("studyNo") int studyNo) {

      wikiService.add(wikiDto);

      return "redirect:/wiki/list?studyNo=" + studyNo;
    }

  @GetMapping("editWiki")
  public void edit(@RequestParam int wikiNo, Model model) throws Exception {
    WikiDto wikiDto = wikiService.getByWikiNo(wikiNo);
    if (wikiDto == null) {
      throw new Exception("해당 스터디 번호가 존재하지 않습니다.");
    }
    model.addAttribute("wiki", wikiDto);
  }

  @GetMapping("viewWiki")
  public void view(@RequestParam int wikiNo, Model model) throws Exception {
    WikiDto wikiDto = wikiService.getByWikiNo(wikiNo);
    if (wikiDto == null) {
      throw new Exception("해당 스터디 번호가 존재하지 않습니다.");
    }
//    log.debug(wikiDto.toString());
    model.addAttribute("wiki", wikiDto);

    List<WikiCommentDto> wikiCommentDtoList = commentService.getWikiComments(wikiNo);
    model.addAttribute("wikiComments", wikiCommentDtoList);
  }

  @PostMapping("updateWiki")
  public String update(@ModelAttribute WikiDto wikiDto, Model model) throws Exception {
    wikiService.updateWiki(wikiDto);
    model.addAttribute("wiki", wikiDto);
    return "redirect:/wiki/viewWiki?wikiNo="+wikiDto.getWikiNo();
  }


  @GetMapping("list")
  public void list(@RequestParam int studyNo, Model model) {
    model.addAttribute("studyNo", studyNo);
    model.addAttribute("wikis", wikiService.listByStudyNo(studyNo));
  }

  @GetMapping("delete")
  public String delete(HttpSession session, @RequestParam int wikiNo) throws Exception {
    //TODO 1. 작성자만 삭제 가능
    int studyNo = wikiService.getByWikiNo(wikiNo).getStudyNo();
    wikiService.deleteWiki(wikiNo);
    return "redirect:/wiki/list?studyNo="+studyNo;
  }


}
