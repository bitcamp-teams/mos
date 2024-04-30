package com.mos.global.auth.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MosSessionRepository {

  int countAll();
}
