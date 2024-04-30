package com.mos.domain.notify.service;

import com.mos.domain.notify.dto.NotifyAddDto;
import com.mos.domain.notify.dto.NotifyListDto;
import com.mos.domain.notify.repository.NotifyRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service
@RequiredArgsConstructor
public class NotifyServiceImpl implements NotifyService {

  private static final Log log = LogFactory.getLog(NotifyServiceImpl.class);
  private final NotifyRepository notifyRepository;


  @Override
  public void save(NotifyAddDto notifyAddDto) {
    try {
      log.debug("notifyAddDto = " + notifyAddDto);
      notifyRepository.add(notifyAddDto);
    } catch (Exception e) {
      TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
      log.debug("NotifyService.add() 실패 = " + e);
    }
  }

  @Override
  @Transactional(readOnly = true)
  public Page<NotifyListDto> getNotifyList(int recipientId, Pageable page) {
    List<NotifyListDto> list = notifyRepository.getNotifyList(recipientId, page.getOffset(), page.getPageSize());
    System.out.println("list = " + list);
    int count = notifyRepository.notifyCount(recipientId);
    return new PageImpl<>(list, page, count);
  }

  @Override
  @Transactional(readOnly = true)
  public boolean existsById(int id) {
    return notifyRepository.existsById(id);
  }

  @Override
  public void updateRead(int id) {
    try {
      notifyRepository.updateReadById(id);
    } catch (Exception e) {
      TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
      log.debug("NotifyService.updateRead() 실패 = " + e);
    }
  }
}
