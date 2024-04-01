package com.mos.domain.code.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;


@Data
public class Code implements Serializable {
  List<CodeGroup> codeGroups;
  String code;
  String codeName;
  Date createDate;
  Date updateDate;


}
