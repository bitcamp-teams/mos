package com.mos.domain.notify.repository;

import com.mos.domain.notify.dto.NotifyAddDto;
import com.mos.domain.notify.dto.NotifyDto;
import com.mos.domain.notify.dto.NotifyListDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NotifyRepository {

  void add(NotifyAddDto notiDto);

//  List<Map<String, Object>> getNotiList(Map<String, Object> paramMap);

  boolean existsById(int id);

  NotifyDto findById(int id);

  void updateReadById(int id);

  int findIdByEmail(String email);

  int notifyCount(int no);

  List<NotifyListDto> getNotifyList(int recipientId, long offset, int pageSize);
}
