package com.mos.domain.notify.service;

import com.mos.domain.notify.dto.NotifyAddDto;
import com.mos.domain.notify.dto.NotifyListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotifyService {
  void save(NotifyAddDto notifyAddDto);

  Page<NotifyListDto> getNotifyList(int recipientId, Pageable page);

  boolean existsById(int id);

  void updateRead(int id);
}
