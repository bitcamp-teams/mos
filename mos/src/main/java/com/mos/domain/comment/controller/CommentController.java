package com.mos.domain.comment.controller;

import com.mos.domain.comment.dto.StudyCommentDto;
import com.mos.domain.comment.dto.WikiCommentDto;
import com.mos.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment") //fragment 로 삽입될 예정
public class CommentController {

  private static final Log log = LogFactory.getLog(Thread.currentThread().getClass());
  private final CommentService commentService;

  //조회를 위한 컨트롤러 없음. SSR 로 처리할 것이므로, Study/Wiki Controller 에서 CommentService 요청하도록 함.

  @PostMapping("study/add")
  public ResponseEntity<StudyCommentDto> addStudyComment(StudyCommentDto studyCommentDto) throws Exception {

    commentService.addStudyComment(studyCommentDto);

    return ResponseEntity.ok(studyCommentDto);
//    return "redirect:/study/view?studyNo=" + studyCommentDto.getStudyNo();
  }

  @GetMapping("study/delete")
  public String deleteStudyComment(@RequestParam int commentNo, @RequestParam int studyNo) throws Exception {
    commentService.deleteStudyComment(commentNo);
    return "redirect:/study/view?studyNo="+studyNo;
  }

  //TODO 댓글 변경기능 추가할 것 (ajax)
  @PostMapping("study/update")
  public String updateStudyComment(@ModelAttribute StudyCommentDto studyCommentDto) throws Exception {
    //  //  commentService.updateStudyComment(studyCommentDto);
    return "";
  }

  @PostMapping("wiki/add")
  public String addWikiComment(WikiCommentDto wikiCommentDto) throws Exception {

    commentService.addWikiComment(wikiCommentDto);

    return "redirect:/wiki/viewWiki?wikiNo=" + wikiCommentDto.getWikiNo();
  }

  @GetMapping("wiki/delete")
  public String deleteWikiComment(@RequestParam int commentNo, @RequestParam int wikiNo) throws Exception {
    commentService.deleteStudyComment(commentNo);
    return "redirect:/wiki/viewWiki?wikiNo="+ wikiNo;
  }

  //TODO 댓글 변경기능 추가할 것 (ajax)
  @PostMapping("wiki/update")
  public String updateWikiComment(@ModelAttribute StudyCommentDto studyCommentDto) throws Exception {
    //  commentService.updateWikiComment(wikiCommentDto);
    return "";
  }

}
