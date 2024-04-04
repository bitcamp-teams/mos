package com.mos.domain.code.dto;

import com.mos.domain.code.entity.CodeGroup;
import com.mos.global.common.paging.Paging;
import lombok.Builder;

import java.sql.Date;

@Builder
public record CodeGroupRequestDto(
    String codeGroup,
    String codeGroupName,
    String moduleCode,
    Date createDate,
    Date updateDate,
    Paging paging
){
  public CodeGroup toEntity() {
    return CodeGroup.builder()
        .codeGroup(codeGroup)
        .codeGroupName(codeGroupName)
        .moduleCode(moduleCode)
        .createDate(createDate)
        .updateDate(updateDate)
        .build();
  }
}
