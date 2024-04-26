package com.mos.domain.wiki.controller;

import com.mos.domain.comment.dto.WikiCommentDto;
import com.mos.domain.comment.service.CommentService;
import com.mos.domain.member.dto.MemberDto;
import com.mos.domain.wiki.dto.JstreeWikiDto;
import com.mos.domain.wiki.dto.WikiDto;
import com.mos.domain.wiki.service.WikiService;
import com.mos.global.auth.LoginUser;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
@RequestMapping("/wiki")
public class WikiController {

  private static final Log log = LogFactory.getLog(Thread.currentThread().getClass());
  private final WikiService wikiService;
  private final CommentService commentService;
  private int wikiNo;
  private Model model;

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
  public void edit(@RequestParam int wikiNo, Model model) throws Exception {
    WikiDto wikiDto = wikiService.getByWikiNo(wikiNo);
    if (wikiDto == null) {
      throw new Exception("해당 스터디 번호가 존재하지 않습니다.");
    }
    model.addAttribute("wiki", wikiDto);
  }

  @GetMapping("viewWiki")
  public void viewWiki(@RequestParam int wikiNo, Model model) throws Exception {
    WikiDto wikiDto = wikiService.getByWikiNo(wikiNo);
    if (wikiDto == null) {
      throw new Exception("해당 스터디 번호가 존재하지 않습니다.");
    }
    //    log.debug(wikiDto.toString());
    model.addAttribute("wiki", wikiDto);

    List<WikiCommentDto> wikiCommentDtoList = commentService.getWikiComments(wikiNo);
    model.addAttribute("wikiComments", wikiCommentDtoList);
  }

  @GetMapping("view")
  public void view(@RequestParam int wikiNo, Model model) throws Exception {
    //해당 위키 컨텐츠를 먼저 가져온다.
    WikiDto wikiDto = wikiService.getByWikiNo(wikiNo);
    //소속된 스터디 번호도 wikiDto 안에 있다.
    int studyNo = wikiDto.getStudyNo();

    //스터디의 전체 위키 리스트를 만들기 위해, 소속 스터디 번호로 리스트를 만든다.
    model.addAttribute("wikiList", wikiService.listByStudyNo(studyNo));

    //위키 본문을 만들기 위한 데이터를 서비스를 통해 가져와서 모델에 넣는다.
    model.addAttribute("wikiDto", wikiDto);
    log.debug(wikiDto);

    //뷰를 반환한다.
  }

  @PostMapping("updateWiki")
  public String update(@ModelAttribute WikiDto wikiDto, Model model) throws Exception {
    wikiService.updateWiki(wikiDto);
    model.addAttribute("wiki", wikiDto);
    return "redirect:/wiki/view?wikiNo=" + wikiDto.getWikiNo();
  }


  @GetMapping("list")
  public void list(@RequestParam int studyNo, Model model) {
    model.addAttribute("studyNo", studyNo);
    model.addAttribute("wikis", wikiService.listByStudyNo(studyNo));
  }

  @GetMapping("modoowikilist")
  public void modoowikilist(Model model) {
    model.addAttribute("wiki", wikiService.listByWikiNo());
  }

  @GetMapping("delete")
  public String delete(HttpSession session, @RequestParam int wikiNo) throws Exception {
    //TODO 1. 작성자만 삭제 가능
    int studyNo = wikiService.getByWikiNo(wikiNo).getStudyNo();
    wikiService.deleteWiki(wikiNo);
    return "redirect:/wiki/list?studyNo=" + studyNo;
  }

  @GetMapping("jstreeTest")
  public void jstreeTest(@LoginUser MemberDto loginUser, Model model, @RequestParam int studyNo) throws Exception {
    model.addAttribute("wikis", wikiService.listByStudyNo(studyNo));
  }

  @GetMapping("listTitle")
  @ResponseBody
  public List<JstreeWikiDto> listTitle(@RequestParam int studyNo) {
    return wikiService.getWikiTitleTree(studyNo);
  }

  //AJAX 요청을 받을 컨트롤러
  @PostMapping("updateListOrder")
    public Boolean updateListOrder (@RequestBody List<JstreeWikiDto> wikiDtoList) {

    //TODO: 권한 검사 추가 필요
    //POST 로 받은 RequestBody로 service객체에 parent_wiki_no 컬럼의 업데이트를 요청한다.

    //업데이트한 결과를 JSON으로 리턴하지 않아도 된다. 이미 화면은 변경되어 있기 때문이다.
    //우선 성공, 실패 여부만 반환해보도록 하자.
    return true;
  }


}
