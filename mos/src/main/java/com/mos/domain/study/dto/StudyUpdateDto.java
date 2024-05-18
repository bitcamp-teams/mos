package com.mos.domain.study.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.sql.Date;

@Data
public class StudyUpdateDto {
    private int studyNo;
    @NotEmpty(message = "제목을 입력해주세요.")
    private String title;
    @NotEmpty(message = "진행 방식을 선택해주세요.")
    private String method;
    private Date recruitmentDeadline;
    @Min(value = 1, message = "인원수는 한 명 이상이어야 합니다.")
    private int intake;
    @NotEmpty(message = "내용을 입력해주세요.")
    private String introduction;
}
