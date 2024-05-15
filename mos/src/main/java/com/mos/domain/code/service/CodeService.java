package com.mos.domain.code.service;

import com.mos.domain.code.dto.CodeGroupResponseDto;
import com.mos.domain.code.dto.CodeRequestDto;
import com.mos.domain.code.dto.CodeGroupRequestDto;
import com.mos.domain.code.dto.CodeResponseDto;
import com.mos.global.common.paging.Paging;

import java.util.List;

public interface CodeService {
  void add(CodeRequestDto codeRequestDto);

  void addCodeGroup(CodeGroupRequestDto codeGroupRequestDto);

  List<CodeGroupResponseDto> listGroup(Paging paging);
  List<CodeResponseDto> list(Paging paging);

  CodeResponseDto get(int no);

  int update(CodeRequestDto code);

  int delete(CodeRequestDto code);

  int countAll();

  int countAllGroup();
}
