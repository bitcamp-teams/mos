package com.mos.domain.noti.controller;

import com.mos.domain.noti.dto.NotiAddDto;
import com.mos.domain.noti.service.NotiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class NotiController {

  private final NotiService notiService;
  private final NotiAddDto notiDto;

  @GetMapping("noti")
  public void noti(int senderId, int recipientId, String message, String link) {
    notiDto.setSenderId(senderId);
    notiDto.setRecipientId(recipientId);
    notiDto.setLink(link);
    notiDto.setMessage(message);
    notiService.send(notiDto);
  }
}
