package com.mos.global.notification.event;

import com.mos.domain.comment.dto.StudyCommentDto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public class CommentAndAuthorIdEvent {

  private final StudyCommentDto studyCommentDto;


}
