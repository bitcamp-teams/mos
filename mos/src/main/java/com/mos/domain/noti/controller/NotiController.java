package com.mos.domain.noti.controller;

import com.mos.domain.member.dto.MemberDto;
import com.mos.domain.noti.dto.NotiAddDto;
import com.mos.domain.noti.dto.NotiDto;
import com.mos.domain.noti.dto.NotiListDto;
import com.mos.domain.noti.dto.NotiUpdateDto;
import com.mos.domain.noti.service.NotiService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NotiController {

  private static final Log log = LogFactory.getLog(NotiController.class);
  private final NotiService notiService;

  @PostMapping("/noti/save")
  public ResponseEntity<NotiAddDto> save(NotiAddDto notiDto) {
    log.debug("notiDto = " + notiDto);
    notiService.add(notiDto);
    return ResponseEntity.ok(notiDto);
  }

  @GetMapping("/noti/list")
  public ResponseEntity<?> list(NotiListDto notiListDto, HttpSession session, @PageableDefault Pageable page) {
    MemberDto loginUser = (MemberDto) session.getAttribute("loginUser");
    notiListDto.setRecipientId(loginUser.getMemberNo());
    Page<NotiListDto> list = notiService.list(notiListDto.getRecipientId(), page);
    log.debug("list = " + list);
    return ResponseEntity.ok().body(list);
  }

  @PostMapping("/noti/update")
  public ResponseEntity<?> updateRead(@RequestBody NotiUpdateDto updateDto) {
    int id = updateDto.getId();
    if (!notiService.existsById(id)) {
      return ResponseEntity.badRequest().build();
    }
    notiService.updateRead(id);
    return ResponseEntity.ok().build();
  }
}