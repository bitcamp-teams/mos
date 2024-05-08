package com.mos.domain.wiki.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WikiLikeStatDto {


    private int wikiLikeStatNo;
    private Integer memberNo;
    private int wikiNo;
}
