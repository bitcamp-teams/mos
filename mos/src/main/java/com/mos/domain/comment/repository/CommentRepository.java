package com.mos.domain.comment.repository;

import com.mos.domain.comment.dto.StudyCommentDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface CommentRepository {

  List<StudyCommentDto> findAllStudyComments(int studyNo);
  void addStudyComment(StudyCommentDto studyCommentDto);

}
