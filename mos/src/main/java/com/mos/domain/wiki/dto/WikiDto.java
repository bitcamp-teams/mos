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

  private int wikiNo;
  private Integer parent_wiki_no;
  private int studyNo;
  private String title;
  private String content;
  private int ordr;
  private int layer;
  private int layerOrder;
  private int likes; //반정규화
  private Date createdDate;
  private Date updatedDate;
  private Date contentCreatedDate;
  private Date contentUpdatedDate;
  private String stat;
  private int memberNo;
  private String username;
  private int hitCount;
}
