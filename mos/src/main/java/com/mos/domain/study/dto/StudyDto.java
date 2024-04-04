package com.mos.domain.study.dto;

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
public class StudyDto {
  int studyNo;
  int memberNo;
  String title;
  String introduction;
  String location;
  Date startDate;
  Date endDate;
  String stat;
  int intake;
  String method;
  Date recruitmentDeadline;
  Date createdDate;
  Date updatedDate;
  int siggNo;
}
