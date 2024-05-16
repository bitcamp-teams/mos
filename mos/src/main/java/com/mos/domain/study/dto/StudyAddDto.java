package com.mos.domain.study.dto;

import java.sql.Date;
import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StudyAddDto {
  private int studyNo;
  @NotEmpty(message = "제목을 입력해주세요.")
  private String title;
  @NotEmpty(message = "글을 작성하세요.")
  private String introduction;
  private String method;
  private Date startDate;
  private Date endDate;
//  @NotNull(message = "태그는 하나 이상 선택해야합니다.")
  private List<TagDto> tagList;
  @Min(value = 1, message = "모집 인원 수는 1명 이상 필요합니다.")
  private int intake;
  private Date recruitmentDeadline;

}
