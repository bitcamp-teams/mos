package com.mos.global.notification.repository;

import com.mos.domain.notify.dto.NotifyAddDto;
import com.mos.domain.notify.dto.NotifyDto;
import com.mos.domain.notify.dto.NotifyListDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NotificationRepository {

  void save(NotifyDto notiDto);

  NotifyDto findById(int id);

  void updateReadById(int id);

  List<NotifyDto> findAllByMemberIdAndUnread(int memberNo);

  void updateNotificationRead(int memberNo);

}
