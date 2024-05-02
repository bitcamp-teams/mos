package com.mos.global.notification.repository;

import com.mos.domain.notify.dto.NotificationDto;
import com.mos.domain.notify.dto.NotifyAddDto;
import com.mos.domain.notify.dto.NotifyDto;
import com.mos.domain.notify.dto.NotifyListDto;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NotificationRepository {

  void save(NotifyDto notiDto);

  NotificationDto findById(int id);

  void updateReadById(int id);

  List<NotificationDto> findAllByMemberIdAndUnread(int memberNo);

  void updateNotificationRead(int memberNo);

}
