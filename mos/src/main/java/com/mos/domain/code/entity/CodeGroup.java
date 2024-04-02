package com.mos.domain.code.entity;

import com.mos.domain.code.dto.CodeGroupDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CodeGroup {
  String codeGroup;
  String codeGroupName;
  String moduleCode;
  Date createDate;
  Date updateDate;

  public CodeGroupDto toDto() {
    return CodeGroupDto.builder()
        .codeGroup(codeGroup)
        .codeGroupName(codeGroupName)
        .moduleCode(moduleCode)
        .createDate(createDate)
        .updateDate(updateDate)
        .build();
  }
}
