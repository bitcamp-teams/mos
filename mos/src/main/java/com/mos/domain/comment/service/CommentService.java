package com.mos.domain.comment.service;

import com.mos.domain.comment.dto.StudyCommentDto;
import java.beans.Transient;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public interface CommentService {
  List<StudyCommentDto> getStudyComments(int studyNo);

  @Transactional
  void addStudyComment(StudyCommentDto studyCommentDto);

  void deleteStudyComment(int commentNo);
}
