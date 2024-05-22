package com.mos.domain.study.dto;

import java.util.List;
import javax.swing.text.html.HTML.Tag;
import javax.validation.constraints.NotNull;
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
    private Date startDate;
    private Date endDate;
    private List<TagDto> tagList;
    @NotNull(message = "모집 인원 수를 선택해주세요.")
    @Min(value = 1, message = "인원수는 한 명 이상이어야 합니다.")
    private Integer intake;
    @NotEmpty(message = "내용을 입력해주세요.")
    private String introduction;
}
