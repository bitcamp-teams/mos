package com.mos.domain.code.entity;

import com.mos.domain.code.dto.CodeResponseDto;
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

  public CodeResponseDto toDto() {
    return CodeResponseDto.builder()
        .codeGroup(group)
        .code(code)
        .codeName(codeName)
        .createDate(createDate)
        .updateDate(updateDate)
        .build();
  }
}
