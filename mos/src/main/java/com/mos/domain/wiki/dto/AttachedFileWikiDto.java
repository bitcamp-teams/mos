package com.mos.domain.wiki.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor // Mybatis 가 사용할 기본 생성자를 만들어야 한다.
@AllArgsConstructor
@Builder
@Data
public class AttachedFileWikiDto {

    private int no;
    private String filePath;
    private int wikiNo;


}
