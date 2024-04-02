package com.mos.domain.code.dto;

import com.mos.domain.code.entity.Code;
import com.mos.domain.code.entity.CodeGroup;
import lombok.Builder;

import java.sql.Date;


@Builder
public record CodeDto(
    CodeGroup codeGroup,
    String code,
    String codeName,
    Date createDate,
    Date updateDate
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
