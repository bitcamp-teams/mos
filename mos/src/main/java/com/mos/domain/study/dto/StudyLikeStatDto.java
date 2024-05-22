package com.mos.domain.study.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyLikeStatDto {

    private int studyLikeStatNo;
    private Integer memberNo;
    private int studyNo;

}
