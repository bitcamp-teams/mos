package com.mos.domain.comment.service;

import com.mos.domain.comment.dto.StudyCommentDto;
import com.mos.domain.comment.dto.WikiCommentDto;
import java.beans.Transient;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public interface CommentService {
  List<StudyCommentDto> getStudyComments(int studyNo);
  List<WikiCommentDto> getWikiComments(int wikiNo);

  @Transactional
  void addStudyComment(StudyCommentDto studyCommentDto);
  void addWikiComment(WikiCommentDto wikiCommentDto);

  void deleteStudyComment(int commentNo);
  void deleteWikiComment(int commentNo);
}
