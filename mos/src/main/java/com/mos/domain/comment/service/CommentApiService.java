package com.mos.domain.comment.service;

import com.mos.domain.comment.dto.StudyCommentDto;
import com.mos.domain.comment.dto.WikiCommentDto;
import com.mos.domain.comment.repository.CommentApiRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public interface CommentApiService {

  List<WikiCommentDto> getCommentByWikiNo(int wikiNo);

  List<StudyCommentDto> getCommentByStudyNo(int studyNo);

  void createWikiComment(WikiCommentDto wikiCommentDto);

  void createStudyComment(StudyCommentDto studyCommentDto);

  void deleteWikiCommentByCommentNo(int commentNo);

  void deleteStudyCommentByCommentNo(int commentNo);
}