package com.mos.domain.code.dto;

import com.mos.domain.code.entity.CodeGroup;
import com.mos.global.common.paging.Paging;
import lombok.Builder;

import java.sql.Date;


@Builder
public record CodeResponseDto(
    CodeGroup codeGroup,
    String code,
    String codeName,
    Date createDate,
    Date updateDate,
    Paging paging
){
}
