package com.mos.domain.code.service.impl;

import com.mos.domain.code.dto.CodeGroupResponseDto;
import com.mos.domain.code.dto.CodeRequestDto;
import com.mos.domain.code.dto.CodeGroupRequestDto;
import com.mos.domain.code.dto.CodeResponseDto;
import com.mos.domain.code.entity.Code;
import com.mos.domain.code.entity.CodeGroup;
import com.mos.domain.code.repository.CodeRepository;
import com.mos.domain.code.service.CodeService;
import com.mos.global.common.paging.Paging;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefaultCodeService implements CodeService {

  private final CodeRepository codeRepository;

  @Override
  public void add(CodeRequestDto codeRequestDto) {
    codeRepository.add(codeRequestDto.toEntity());
  }

  @Override
  public void addCodeGroup(CodeGroupRequestDto codeGroupRequestDto) {
    codeRepository.addCodeGroup(codeGroupRequestDto.toEntity());
  }

  @Override
  public List<CodeGroupResponseDto> listGroup(Paging paging) {
    return codeRepository.findAllGroup(paging).stream().map(CodeGroup::toDto).collect(Collectors.toList());
  }

  @Override
  public List<CodeResponseDto> list(Paging paging) {
    return codeRepository.findAllCode(paging).stream().map(Code::toDto).collect(Collectors.toList());
  }

  @Override
  public CodeResponseDto get(int no) {
    return null;
  }

  @Override
  public int update(CodeRequestDto codeRequestDto) {
    return 0;
  }

  @Override
  public int delete(int no) {
    return 0;
  }

  @Override
  public int countAll() {
    return codeRepository.countAll();
  }

  @Override
  public int countAllGroup() {
    return codeRepository.countAllGroup();
  }
}
