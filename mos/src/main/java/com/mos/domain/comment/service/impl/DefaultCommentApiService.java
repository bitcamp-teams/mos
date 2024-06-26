package com.mos.domain.comment.service.impl;

import com.mos.domain.comment.dto.StudyCommentDto;
import com.mos.domain.comment.dto.WikiCommentDto;
import com.mos.domain.comment.repository.CommentApiRepository;
import com.mos.domain.comment.service.CommentApiService;
import com.mos.global.notification.event.CommentAndAuthorIdEvent;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DefaultCommentApiService implements CommentApiService {

  private final CommentApiRepository commentApiRepository;
  private final ApplicationEventPublisher applicationEventPublisher;

  @Transactional(readOnly = true)
  @Override
  public List<WikiCommentDto> getCommentByWikiNo(int wikiNo) {
    return commentApiRepository.findCommentByWikiNo(wikiNo);
  }

  @Transactional(readOnly = true)
  @Override
  public List<StudyCommentDto> getCommentByStudyNo(int studyNo) {
    return commentApiRepository.findCommentByStudyNo(studyNo);
  }

  @Override
  public void createWikiComment(WikiCommentDto wikiCommentDto) {
    commentApiRepository.saveWikiCommentByWikiNo(wikiCommentDto);
  }

  @Override
  public void createStudyComment(StudyCommentDto studyCommentDto) {
    publishEvent(studyCommentDto);
    commentApiRepository.saveStudyCommentByWikiNo(studyCommentDto);
  }

  @Override
  public void deleteWikiCommentByCommentNo(int commentNo) {
    commentApiRepository.deleteWikiCommentByCommentNo(commentNo);
  }

  @Override
  public void deleteStudyCommentByCommentNo(int commentNo) {
    commentApiRepository.deleteStudyCommentByCommentNo(commentNo);
  }

  @Override
  public void patchWikiComment(WikiCommentDto wikiCommentDto) {
    commentApiRepository.patchWikiComment(wikiCommentDto);
  }

  @Override
  public void patchStudyComment(StudyCommentDto studyCommentDto) {
    commentApiRepository.patchStudyComment(studyCommentDto);
  }

  private void publishEvent(StudyCommentDto studyCommentDto) {
    CommentAndAuthorIdEvent commentAndAuthorIdEvent = new CommentAndAuthorIdEvent(studyCommentDto);
    applicationEventPublisher.publishEvent(commentAndAuthorIdEvent);
  }
}
