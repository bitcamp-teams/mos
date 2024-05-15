package com.mos.domain.study.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor // Mybatis 가 사용할 기본 생성자를 만들어야 한다.
@AllArgsConstructor
@Builder
@Data
public class AttachedFileDto {

    private int no;
    private String filePath;
    private int studyNo;


}
