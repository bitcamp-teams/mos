package com.mos.domain.code.service.impl;

import com.mos.domain.code.dto.CodeDto;
import com.mos.domain.code.dto.CodeGroupDto;
import com.mos.domain.code.entity.Code;
import com.mos.domain.code.entity.CodeGroup;
import com.mos.domain.code.repository.CodeRepository;
import com.mos.domain.code.service.CodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefaultCodeService implements CodeService {

  private final CodeRepository codeRepository;

  @Override
  public void add(CodeDto codeDto) {
    codeRepository.add(codeDto.toEntity());
  }

  @Override
  public void addCodeGroup(CodeGroupDto codeGroupDto) {
    codeRepository.addCodeGroup(codeGroupDto.toEntity());
  }

  @Override
  public List<CodeGroupDto> listGroup() {
    return codeRepository.findAllGroup().stream().map(CodeGroup::toDto).collect(Collectors.toList());
  }

  @Override
  public List<CodeDto> list() {
    List<Code> allCode = codeRepository.findAllCode();
    List<CodeDto> collect =
        allCode.stream().map(Code::toDto).collect(Collectors.toList());
    return collect;
  }

  @Override
  public CodeDto get(int no) {
    return null;
  }

  @Override
  public int update(CodeDto codeDto) {
    return 0;
  }

  @Override
  public int delete(int no) {
    return 0;
  }

  @Override
  public int countAll() {
    return 0;
  }
}
