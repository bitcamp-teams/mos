package com.mos.domain.noti.repository;

import com.mos.domain.noti.dto.NotiAddDto;
import com.mos.domain.noti.dto.NotiDto;
import com.mos.domain.noti.dto.NotiListDto;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NotiRepository {

  void add(NotiAddDto notiDto);

//  List<Map<String, Object>> getNotiList(Map<String, Object> paramMap);

  boolean existsById(int id);

  NotiDto findById(int id);

  void updateReadById(int id);

  int findIdByEmail(String email);

  int notiCount(int no);

  List<NotiListDto> getNotiList(int recipientId, long offset, int pageSize);
}
