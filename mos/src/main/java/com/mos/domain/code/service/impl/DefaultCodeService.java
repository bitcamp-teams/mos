package com.mos.domain.code.service.impl;

import com.mos.domain.code.dto.Code;
import com.mos.domain.code.dto.CodeGroup;
import com.mos.domain.code.repository.CodeRepository;
import com.mos.domain.code.service.CodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultCodeService implements CodeService {

  private final CodeRepository codeRepository;

  @Override
  public void add(Code code) {
    codeRepository.add(code);
  }

  @Override
  public void addCodeGroup(CodeGroup codeGroup) {
    codeRepository.addCodeGroup(codeGroup);
  }

  @Override
  public List<CodeGroup> listGroup() {
    return codeRepository.findAllGroup();
  }

  @Override
  public List<Code> list() {
    return null;
  }

  @Override
  public Code get(int no) {
    return null;
  }

  @Override
  public int update(Code code) {
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
