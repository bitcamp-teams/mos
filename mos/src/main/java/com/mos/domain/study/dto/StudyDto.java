package com.mos.domain.study.dto;

import java.sql.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
//iBatis 는 생성자가 반드시 필요하다.
@NoArgsConstructor
@AllArgsConstructor
public class StudyDto {

  private int studyNo;
  private int memberNo;
  private String leaderName;
  private String title;
  private String introduction;
  private String location;
  private Date startDate;
  private Date endDate;
  private String stat;
  private int intake;
  private String method;
  private Date recruitmentDeadline;
  private Date createdDate;
  private Date updatedDate;
  private int siggNo;
  private String tags;
  private List<TagDto> tagList;
  private int hitCount;
  private int likeCount;
  private String username;
  private int commentCount;
  private List<AttachedFileStudyDto> fileList;
  private String photo;

}
