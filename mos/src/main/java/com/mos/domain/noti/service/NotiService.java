package com.mos.domain.noti.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mos.domain.member.repository.MemberRepository;
import com.mos.domain.noti.dto.NotiAddDto;
import com.mos.domain.noti.dto.NotiDto;
import com.mos.domain.noti.repository.NotiRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service
@RequiredArgsConstructor
public class NotiService {

  private static final Log log = LogFactory.getLog(NotiService.class);
  private final NotiRepository notiRepository;


  public void add(NotiAddDto notiDto) {
    try {
      log.debug("notiDto = " + notiDto);
      notiRepository.add(notiDto);
    } catch (Exception e) {
      TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
      log.debug("NotiService.add() 실패 = " + e);
    }
  }


  @Transactional(readOnly = true)
  public String list(int id) {
    List<NotiDto> list = notiRepository.findByRecipientId(id);

    Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .disableHtmlEscaping()
        .create();
    log.debug("list = " + gson);
    return gson.toJson(list);
  }

  @Transactional(readOnly = true)
  public boolean existsById(int id) {
    return notiRepository.existsById(id);
  }

  public void updateRead(int id) {
    try {
      notiRepository.updateReadById(id);
    } catch (Exception e) {
      TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
      log.debug("NotiService.updateRead() 실패 = " + e);
    }
  }
}
