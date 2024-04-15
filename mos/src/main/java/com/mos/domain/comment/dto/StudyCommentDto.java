package com.mos.domain.comment.dto;

import java.sql.Date;
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
  String username;
  String content;
  int layer;
  int stat;
  Date createdDate;
  Date updatedDate;
}
