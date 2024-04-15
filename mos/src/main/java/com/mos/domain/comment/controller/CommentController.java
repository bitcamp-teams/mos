package com.mos.domain.comment.controller;

import com.mos.domain.comment.dto.StudyCommentDto;
import com.mos.domain.comment.service.CommentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment") //fragment 로 삽입될 예정
public class CommentController {

  private static final Log log = LogFactory.getLog(Thread.currentThread().getClass());
  private final CommentService commentService;

  @PostMapping("study/add")
  public String view(StudyCommentDto studyCommentDto) throws Exception {

    commentService.addStudyComment(studyCommentDto);

    return "redirect:/study/view?studyNo=" + studyCommentDto.getStudyNo();
  }

}
