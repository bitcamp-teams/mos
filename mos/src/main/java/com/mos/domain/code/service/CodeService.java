package com.mos.domain.code.service;

import com.mos.domain.code.dto.Code;
import com.mos.domain.code.dto.CodeGroup;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CodeService {
  void add(Code code);

  void addCodeGroup(CodeGroup codeGroup);

  List<CodeGroup> listGroup();
  List<Code> list();

  Code get(int no);

  int update(Code code);

  int delete(int no);

  int countAll();

}
