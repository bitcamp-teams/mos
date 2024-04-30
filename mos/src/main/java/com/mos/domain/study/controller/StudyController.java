package com.mos.domain.study.controller;

import com.mos.domain.comment.dto.StudyCommentDto;
import com.mos.domain.comment.service.CommentService;
import com.mos.domain.member.dto.MemberDto;
import com.mos.domain.member.dto.MemberStudyDto;
import com.mos.domain.study.dto.StudyDto;
import com.mos.domain.study.dto.TagDto;
import com.mos.domain.study.service.StudyService;
import com.mos.domain.wiki.service.WikiService;
import com.mos.global.auth.LoginUser;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/study")
public class StudyController {

  // 현재 스레드의 클래스를 파라미터로 받아서 로그 객체를 만든다.
  private static final Logger log = LoggerFactory.getLogger(Thread.currentThread().getClass());
  private final StudyService studyService;
  // 스터디에 파일저장 / 이미지 옵티마이징 따로 없으므로 변수 추가 없음
  private final CommentService commentService;
  private final WikiService wikiService;

  @GetMapping("form")
  public String form(Model model) throws Exception {

    List<TagDto> tagList = studyService.getAllTags();
    model.addAttribute("tagList", tagList);
    return "study/form";
  }

  @PostMapping("add")
  public String add(
      @LoginUser MemberDto user, @ModelAttribute StudyDto studyDto, @RequestParam("tags") List<Integer> tagNums
  ) {
    try {
      int memberNo = user.getMemberNo();
      studyDto.setMemberNo(user.getMemberNo());
    } catch (Exception e) {
      log.debug("login is required");
      e.printStackTrace();
    }

    List<TagDto> tagList = new ArrayList<>();
    for (int no : tagNums) {
      TagDto tag = TagDto.builder().tagNo(no).build();
      tagList.add(tag);
    }
    studyDto.setTagList(tagList);
    studyService.add(studyDto);

    return "redirect:list";
  }

  @GetMapping("view")
  public void view(@LoginUser MemberDto user, @RequestParam int studyNo, Model model) throws Exception {

    if (user != null) {
      model.addAttribute("memberNo", user.getMemberNo());
    }

    StudyDto studyDto = studyService.getByStudyNo(studyNo);
    if (studyDto == null) {
      throw new Exception("해당 스터디 번호가 존재하지 않습니다.");
    }

    model.addAttribute("study", studyDto);

    List<StudyCommentDto> studyCommentDtoList = commentService.getStudyComments(studyNo);

    model.addAttribute("studyComments", studyCommentDtoList);

    //첫번째 wikiNo도 모델에 담아준다.
    try {
      model.addAttribute("firstWikiNo", wikiService.getFirstWikiNo(studyNo));
    } catch (Exception e) {
      //아직 위키가 없는 상태임
    }


  }

  @GetMapping("edit")
  public void edit(int studyNo, Model model) throws Exception {
    StudyDto studyDto = studyService.getByStudyNo(studyNo);
    if (studyDto == null) {
      throw new Exception("해당 스터디 번호가 존재하지 않습니다.");
    }
    model.addAttribute("study", studyDto);
  }

  @PostMapping("update")
  // 히든필드로 POST에 studyNo를 받는다!
  public String update(@ModelAttribute StudyDto studyDto, Model model) throws Exception {
    studyService.update(studyDto);
    StudyDto result = studyService.getByStudyNo(studyDto.getStudyNo());
    model.addAttribute("study", result);
    // return "view?studyNo=" + studyDto.getStudyNo();
    return "redirect:view?studyNo=" + studyDto.getStudyNo();
  }

  @GetMapping("delete")
  public String delete(HttpSession session, int studyNo) throws Exception {
    // TODO 스터디장만 삭제 권한 있고,
    //  연결된 다른 참여회원이 존재할 경우 삭제 불가하며
    //  타인이 작성된 위키가 있는 경우는 삭제 불가
    studyService.deleteStudy(studyNo);

    // TODO 연결된 위키도 전부 삭제함
    // wikiService.deleteAllByStudyNo(studyNo);

    return "redirect:list";
  }

  @PostMapping("/applyStudy")
  @ResponseBody
  public Object applyStudy(
      @LoginUser MemberDto loginUser,
      @RequestParam int studyNo,
      @RequestParam String applyMsg,
      RedirectAttributes redirectAttributes) {

    if (loginUser == null) {
      return "auth/login";
    }

    // 스터디 정보 가져오기
    StudyDto studyDto = studyService.getByStudyNo(studyNo);

    // 스터디가 존재하지 않는 경우 처리
    if (studyDto == null) {
      redirectAttributes.addFlashAttribute("message", "해당 스터디를 찾을 수 없습니다.");
      return "study/list";
    }

    // 스터디 상태 확인
    String stat = studyDto.getStat();
    if (stat == null || stat.equals("모집완료") || stat.equals("종료")) {
      redirectAttributes.addFlashAttribute("message", "이미 모집이 완료되었거나 종료된 스터디입니다.");
      return "study/list";
    }

    // MemberStudyDto 객체 생성 및 값 설정
    MemberStudyDto memberStudyDto = new MemberStudyDto();
    memberStudyDto.setMemberDto(loginUser);
    memberStudyDto.setStudyDto(studyDto);
    memberStudyDto.setApplyMsg(applyMsg); // applyMsg 설정

    try {
      studyService.applyStudy(memberStudyDto);
      redirectAttributes.addFlashAttribute("message", "스터디 신청이 완료되었습니다.");
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("message", "스터디 신청에 실패했습니다.");
      // 에러 로깅 또는 처리
    }

    return "success";
  }


  @GetMapping("list")
  public void list(Model model) {
    model.addAttribute("studyList", studyService.list());
  }

  @GetMapping("main")
  public void main(Model model) {
    model.addAttribute("studyList", studyService.list());
  }

  @GetMapping("test")
  @ResponseBody
  public String test() {
    return "This is a test";
  }

//  @GetMapping("search")
//  public String search(Model model,
//                       @RequestParam(value="title") String title,
//                       @RequestParam(value="introduction") String introduction,
//                       @RequestParam(required = false, defaultValue = "1")int num)throws Exception {
//
//    if (title != null  && introduction !=null) {
//      search(model,title,introduction,num);
//      log.debug("studyService = {}", studyService.toString());
//    } else {
//      model.addAttribute("studyList", studyService.list());
//      log.debug("studyService = {}", studyService.toString());
//    }
//    return "study/list";
//  }

  @GetMapping("/search")
  public String search(Model model,
                       @RequestParam(value="type") String type,
                       @RequestParam(value="keyword") String keyword) {
    try {
      List<StudyDto> studyList = studyService.searchByTypeAndKeyword(type, keyword);
      model.addAttribute("studyList", studyList);
      return "study/list";
    } catch (Exception e) {
      log.error("Error occurred during study search", e);
      model.addAttribute("errorMessage", "검색 중 오류가 발생했습니다.");
      return "error-page";
    }
  }

}
