package com.mos.domain.comment.service.impl;

import com.mos.domain.comment.dto.StudyCommentDto;
import com.mos.domain.comment.dto.WikiCommentDto;
import com.mos.domain.comment.repository.CommentRepository;
import com.mos.domain.comment.service.CommentService;
import com.mos.domain.study.repository.StudyRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultCommentService implements CommentService {

  private final CommentRepository commentRepository;

  @Override
  public List<StudyCommentDto> getStudyComments(int studyNo) {
    return commentRepository.findAllStudyComments(studyNo);
  }

  @Override
  public void addStudyComment(StudyCommentDto studyCommentDto) {
    commentRepository.addStudyComment(studyCommentDto);
  }

  @Override
  public void deleteStudyComment(int commentNo) {
    commentRepository.deleteStudyComment(commentNo);
  }

  @Override
  public List<WikiCommentDto> getWikiComments(int wikiNo) {
    return commentRepository.findAllWikiComments(wikiNo);
  }

  @Override
  public void addWikiComment(WikiCommentDto wikiCommentDto) {
    commentRepository.addWikiComment(wikiCommentDto);
  }

  @Override
  public void deleteWikiComment(int commentNo) {
    commentRepository.deleteWikiComment(commentNo);
  }
}
