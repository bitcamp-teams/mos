package com.mos.domain.code.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;

@Data
@NoArgsConstructor
public class CodeGroup implements Serializable {
  String codeGroup;
  String codeGroupName;
  String moduleCode;
  Date createDate;
  Date updateDate;
}
