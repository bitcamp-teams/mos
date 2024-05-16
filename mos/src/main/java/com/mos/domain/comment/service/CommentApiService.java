package com.mos.domain.comment.service;

import com.mos.domain.comment.dto.StudyCommentDto;
import com.mos.domain.comment.dto.WikiCommentDto;
import java.util.List;

public interface CommentApiService {

  List<WikiCommentDto> getCommentByWikiNo(int wikiNo);

  List<StudyCommentDto> getCommentByStudyNo(int studyNo);

  void createWikiComment(WikiCommentDto wikiCommentDto);

  void createStudyComment(StudyCommentDto studyCommentDto);

  void deleteWikiCommentByCommentNo(int commentNo);

  void deleteStudyCommentByCommentNo(int commentNo);

  void patchWikiComment(WikiCommentDto wikiCommentDto);
}