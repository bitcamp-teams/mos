package com.mos.domain.code.repository;

import com.mos.domain.code.dto.Code;
import com.mos.domain.code.dto.CodeGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CodeRepository {
  void add(Code code);

  void addCodeGroup(CodeGroup codeGroup);

  int delete(int no);

  List<CodeGroup> findAllGroup();
  List<Code> findAllCode();

  Code findBy(int no);

  int update(Code code);

  int countAll();
}
