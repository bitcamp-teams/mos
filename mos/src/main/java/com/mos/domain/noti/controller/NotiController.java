package com.mos.domain.noti.controller;

import com.mos.domain.member.dto.MemberDto;
import com.mos.domain.noti.dto.NotiAddDto;
import com.mos.domain.noti.dto.NotiDto;
import com.mos.domain.noti.dto.NotiUpdateDto;
import com.mos.domain.noti.service.NotiService;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class NotiController {

  private final NotiService notiService;

  @PostMapping("/noti/save")
  public ResponseEntity<NotiAddDto> save(@ModelAttribute NotiAddDto notiDto) {
    System.out.println("url = " + notiDto.getLink());
    System.out.println("recipientId = " + notiDto.getRecipientId());
    System.out.println("message = " + notiDto.getMessage());
    notiService.add(notiDto);
    return ResponseEntity.ok(notiDto);
  }

  @GetMapping("/noti/list")
  public ResponseEntity<String> list(HttpSession session) {
    MemberDto loginUser = (MemberDto) session.getAttribute("loginUser");
    int no = loginUser.getMemberNo();
    System.out.println("no = " + no);
    String notiListJson = notiService.list(no);
    System.out.println("notiList = " + notiListJson);
    return ResponseEntity.ok(notiListJson);
  }

  @PostMapping("/noti/update")
  public ResponseEntity<?> read(@RequestBody NotiUpdateDto updateDto) {
    int id = updateDto.getId();
    if (!notiService.existsById(id)) {
      return ResponseEntity.badRequest().build();
    }
    notiService.updateRead(id);
    return ResponseEntity.ok().build();
  }
}