package com.mos.domain.noti.repository;

import com.mos.domain.noti.dto.NotiAddDto;
import com.mos.domain.noti.dto.NotiDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NotiRepository {

  void add(NotiAddDto notiDto);

  List<NotiDto> findById(int id);
}
