package com.mos.domain.code.dto;

import com.mos.domain.code.entity.CodeGroup;
import lombok.Builder;

import java.sql.Date;

@Builder
public record CodeGroupDto(
    String codeGroup,
    String codeGroupName,
    String moduleCode,
    Date createDate,
    Date updateDate
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
