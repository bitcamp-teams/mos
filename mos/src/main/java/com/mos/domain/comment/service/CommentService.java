package com.mos.domain.comment.service;

import com.mos.domain.comment.dto.StudyCommentDto;
import java.util.List;
import org.springframework.stereotype.Service;

public interface CommentService {
  List<StudyCommentDto> getStudyComments(int studyNo);

  void addStudyComment(StudyCommentDto studyCommentDto);
}
