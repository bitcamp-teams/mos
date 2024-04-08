package com.mos.domain.wiki.dto;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
//iBatis 는 생성자가 반드시 필요하다.
@NoArgsConstructor
@AllArgsConstructor
public class WikiDto {

  int wikiNo;
  int studyNo;
  String title;
  String content;
  int order;
  int layer;
  int layerOrder;
  int likes; //반정규화
  Date createdDate;
  Date updatedDate;
  Date contentCreatedDate;
  Date contentUpdatedDate;
  String stat;
  int memberNo;
}
