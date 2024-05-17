package com.mos.domain.wiki.controller;

import com.mos.domain.comment.dto.WikiCommentDto;
import com.mos.domain.comment.service.CommentService;
import com.mos.domain.member.dto.MemberDto;
import com.mos.domain.wiki.dto.WikiDto;
import com.mos.domain.wiki.service.WikiApiService;
import com.mos.domain.wiki.service.WikiService;
import com.mos.global.auth.LoginUser;
import java.util.List;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
  private final WikiApiService wikiApiService;
  private final CommentService commentService;

  @GetMapping("form")
  public void form(@RequestParam int studyNo, Model model) throws Exception {

    model.addAttribute("studyNo", studyNo);
  }

  @PostMapping("add")
  public String add(
      @ModelAttribute WikiDto wikiDto, @RequestParam("studyNo") int studyNo
  ) {

    wikiService.add(wikiDto);

    return "redirect:/wiki/list?studyNo=" + studyNo;
  }

  @GetMapping("editWiki")
  public void editWiki(@RequestParam int wikiNo, Model model) throws Exception {
    WikiDto wikiDto = wikiService.getByWikiNo(wikiNo);
    if (wikiDto == null) {
      throw new Exception("해당 스터디 번호가 존재하지 않습니다.");
    }
    model.addAttribute("wiki", wikiDto);
  }

  @GetMapping("edit")
  public void edit(
      @LoginUser MemberDto loginUser,
      @RequestParam(required = false, defaultValue = "0") int wikiNo,
      @RequestParam int studyNo,
      Model model
  ) throws Exception {
    try {
      model.addAttribute("loginUser", loginUser);
    } catch (Exception e) {
      //slightly quit
    }
    
  }


  //studyNo에 따라 리스트를 가져오고, 거기서 비동기로 wikiNo에 따라 본문을 보여준다.
  //만약 wikiNo가 있다면 그 위키를 보여준다. (없으면 그냥 리스트만 보여주자)
  @GetMapping("view")
  public void view(
      @LoginUser MemberDto loginUser,
      @RequestParam(required = false, defaultValue = "0") int wikiNo,
      @RequestParam int studyNo,
      Model model
  ) throws Exception {
    try {
      model.addAttribute("loginUser", loginUser);
    } catch (Exception e) {
      //slightly quit
    }
    // 뷰를 반환한다. async로 api 컨트롤러에 요청하여 동작하므로 service 객체를 사용하지 않음.
    try {
      wikiService.updateHitCount(wikiNo);
    } catch (Exception e) {
      // slightly quit
    }
  }

  @PostMapping("updateWiki")
  public String update(@ModelAttribute WikiDto wikiDto, Model model) throws Exception {
    wikiService.updateWiki(wikiDto);
    model.addAttribute("wiki", wikiDto);
    return "redirect:/wiki/view?studyNo=" + wikiDto.getStudyNo() + "&wikiNo=" + wikiDto.getWikiNo();
  }

  @GetMapping("list")
  public void list(@RequestParam int studyNo, Model model) {
    model.addAttribute("studyNo", studyNo);
    model.addAttribute("wikis", wikiService.listByStudyNo(studyNo));
  }

  @GetMapping("card")
  public void card(@RequestParam int studyNo, Model model) {
    model.addAttribute("studyNo", studyNo);
    model.addAttribute("wikis", wikiService.listByStudyNo(studyNo));
  }

  @GetMapping("modooWikiList")
  public void modooWikiList(Model model, int page) {
    Pageable pageable = PageRequest.of(page - 1, 20);
    System.out.println("wikiService.listByWikiNo(page) = " + wikiService.listByWikiNo(pageable));
    model.addAttribute("wiki", wikiService.listByWikiNo(pageable));
  }

  @GetMapping("delete")
  public String delete(HttpSession session, @RequestParam int wikiNo) throws Exception {
    // TODO: 권한 검증
    int studyNo = wikiService.getByWikiNo(wikiNo).getStudyNo();
    wikiService.deleteWiki(wikiNo);
    return "redirect:/wiki/list?studyNo=" + studyNo;
  }

}
