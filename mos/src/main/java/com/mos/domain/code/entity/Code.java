package com.mos.domain.code.entity;

import com.mos.domain.code.dto.CodeDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Code {
  String code;
  CodeGroup group;
  String codeName;
  Date createDate;
  Date updateDate;

  public CodeDto toDto() {
    return CodeDto.builder()
        .codeGroup(group)
        .code(code)
        .codeName(codeName)
        .createDate(createDate)
        .updateDate(updateDate)
        .build();
  }
}
