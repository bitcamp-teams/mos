package com.mos.domain.noti.repository;

import com.mos.domain.noti.dto.NotiAddDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NotiRepository {

  void add(NotiAddDto notiDto, int recipientId);
}
