package com.mos.domain.study.controller;

import com.mos.domain.comment.dto.StudyCommentDto;
import com.mos.domain.comment.service.CommentService;
import com.mos.domain.study.dto.StudyDto;
import com.mos.domain.study.dto.TagDto;
import com.mos.domain.study.service.StudyService;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/study")
public class StudyController {

  // 현재 스레드의 클래스를 파라미터로 받아서 로그 객체를 만든다.
  private static final Log log = LogFactory.getLog(Thread.currentThread().getClass());
  private final StudyService studyService;
  // 스터디에 파일저장 / 이미지 옵티마이징 따로 없으므로 변수 추가 없음
  private final CommentService commentService;

  @GetMapping("form")
  public String form(Model model) throws Exception {
    List<TagDto> tagList = studyService.getAllTags();
    model.addAttribute("tagList", tagList);
    return "study/form";
  }

  @PostMapping("add")
  public String add(@ModelAttribute StudyDto studyDto,
                    @RequestParam("tags") List<Integer> tagNums) {

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
  public void view(@RequestParam int studyNo, Model model) throws Exception {
    StudyDto studyDto = studyService.getByStudyNo(studyNo);
    if (studyDto == null) {
      throw new Exception("해당 스터디 번호가 존재하지 않습니다.");
    }
    model.addAttribute("study", studyDto);

    List<StudyCommentDto> studyCommentDtoList = commentService.getStudyComments(studyNo);
    model.addAttribute("studyComment", studyCommentDtoList);
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
    return "/study/view";
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

  @GetMapping("list")
  public void list(Model model) {
    model.addAttribute("studyList", studyService.list());
  }


  @GetMapping("test")
  @ResponseBody
  public String test() {
    return "This is a test";
  }

}
