package com.mos.domain.code.entity;

import com.mos.domain.code.dto.CodeGroupResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.sql.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CodeGroup {
  @NotBlank
  String codeGroup;
  @NotBlank
  String codeGroupName;
  @NotBlank
  String moduleCode;
  Date createDate;
  Date updateDate;

  public CodeGroupResponseDto toDto() {
    return CodeGroupResponseDto.builder()
        .codeGroup(codeGroup)
        .codeGroupName(codeGroupName)
        .moduleCode(moduleCode)
        .createDate(createDate)
        .updateDate(updateDate)
        .build();
  }
}
