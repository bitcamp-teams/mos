package com.mos.domain.noti.controller;

import com.mos.domain.noti.dto.NotiAddDto;
import com.mos.domain.noti.service.NotiService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class NotiController {

  private final NotiService notiService;
//  private final NotiAddDto notiDto;

  @GetMapping("/noti/add")
  public String noti(
      NotiAddDto notiDto,
      @RequestParam int recipientId,
      @RequestParam String message,
      @RequestParam String url
  ) {
    notiDto.setLink(url);
    System.out.println("url = " + url);
    notiDto.setRecipientId(recipientId);
    System.out.println("recipientId = " + recipientId);
    notiDto.setMessage(message);
    System.out.println("message = " + message);
    notiService.sendNoti(notiDto);
    return "redirect:" + url;
  }
}
