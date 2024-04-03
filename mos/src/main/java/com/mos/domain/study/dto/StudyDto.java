package com.mos.domain.study.dto;

import java.sql.Date;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudyDto {
  int studyNo;
  int memberNo;
  String title;
  int intake;
  String method;
  Date recruitmentDeadline;

}
