package com.mos.domain.comment.repository;

import com.mos.domain.comment.dto.StudyCommentDto;
import com.mos.domain.comment.dto.WikiCommentDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface CommentRepository {

  List<StudyCommentDto> findAllStudyComments(int studyNo);
  void addStudyComment(StudyCommentDto studyCommentDto);
  void deleteStudyComment(int commentNo);

  List<WikiCommentDto> findAllWikiComments(int wikiNo);
  void addWikiComment(WikiCommentDto wikiCommentDto);
  void deleteWikiComment(int commentNo);

}
