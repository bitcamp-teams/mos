package com.mos.domain.comment.dto;

import java.sql.Date;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WikiCommentDto {
  int commentNo;
  int parentCommentNo;
  int wikiNo;
  int memberNo;
  String username;
  String content;
  int layer;
  String stat;
  Timestamp createdDate;
  Timestamp updatedDate;
  String wikiTitle;
}