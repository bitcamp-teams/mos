package com.mos.domain.comment.dto;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyCommentDto {
  int commentNo;
  int parentCommentNo;
  int studyNo;
  int memberNo;
  int notiReceiver;
  String title;
  String username;
  String content;
  int layer;
  String stat;
  Timestamp createdDate;
  Timestamp updatedDate;
  String studyTitle;
}
