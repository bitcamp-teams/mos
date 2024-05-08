package com.mos.domain.comment.repository;

import com.mos.domain.comment.dto.StudyCommentDto;
import com.mos.domain.comment.dto.WikiCommentDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentApiRepository {

  List<WikiCommentDto> findCommentByWikiNo(int wikiNo);

  List<StudyCommentDto> findCommentByStudyNo(int studyNo);

  void saveWikiCommentByWikiNo(WikiCommentDto wikiCommentDto);

  void saveStudyCommentByWikiNo(StudyCommentDto studyCommentDto);

  void deleteWikiCommentByCommentNo(int commentNo);

  void deleteStudyCommentByCommentNo(int commentNo);
}
