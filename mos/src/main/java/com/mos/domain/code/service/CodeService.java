package com.mos.domain.code.service;

import com.mos.domain.code.dto.CodeDto;
import com.mos.domain.code.dto.CodeGroupDto;
import com.mos.domain.code.entity.Code;
import com.mos.domain.code.entity.CodeGroup;

import java.util.List;

public interface CodeService {
  void add(CodeDto codeDto);

  void addCodeGroup(CodeGroupDto codeGroupDto);

  List<CodeGroupDto> listGroup();
  List<CodeDto> list();

  CodeDto get(int no);

  int update(CodeDto code);

  int delete(int no);

  int countAll();

}
