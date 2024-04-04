package com.mos.domain.code.dto;

import com.mos.domain.code.entity.Code;
import com.mos.domain.code.entity.CodeGroup;
import com.mos.global.common.paging.Paging;
import lombok.Builder;
import lombok.Setter;

import java.sql.Date;


@Builder
public record CodeRequestDto(
    CodeGroup codeGroup,
    String code,
    String codeName,
    Date createDate,
    Date updateDate,
    Paging paging
){
  public Code toEntity() {
    return Code.builder()
        .group(codeGroup)
        .code(code)
        .codeName(codeName)
        .createDate(createDate)
        .updateDate(updateDate)
        .build();
  }
}
