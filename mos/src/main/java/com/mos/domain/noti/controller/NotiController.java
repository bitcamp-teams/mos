package com.mos.domain.noti.controller;

import com.mos.domain.member.dto.MemberDto;
import com.mos.domain.noti.dto.NotiAddDto;
import com.mos.domain.noti.dto.NotiDto;
import com.mos.domain.noti.service.NotiService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class NotiController {

  private final NotiService notiService;

  @PostMapping("/noti/save")
  public String save(@ModelAttribute NotiAddDto notiDto) {
    System.out.println("url = " + notiDto.getLink());
    System.out.println("recipientId = " + notiDto.getRecipientId());
    System.out.println("message = " + notiDto.getMessage());
    notiService.add(notiDto);
    return "redirect:" + notiDto.getLink();
  }

  @GetMapping("/noti/list")
  public String list(@RequestParam int no, Model model) {
    String notiListJson = notiService.list(no);
    model.addAttribute("notiListJson", notiListJson);
    System.out.println("notiList = " + notiListJson);
    return "noti/list";
  }
}
