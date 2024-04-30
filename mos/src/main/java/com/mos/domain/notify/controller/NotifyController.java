package com.mos.domain.notify.controller;

import com.mos.domain.member.dto.MemberDto;
import com.mos.domain.notify.dto.NotifyListDto;
import com.mos.domain.notify.dto.NotifyUpdateDto;
import com.mos.domain.notify.service.NotifyService;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notify")
public class NotifyController {

  private static final Log log = LogFactory.getLog(NotifyController.class);
  private final NotifyService notifyService;


  @GetMapping("list")
  public ResponseEntity<?> list(NotifyListDto notifyListDto, HttpSession session, @PageableDefault Pageable page) {
    MemberDto loginUser = (MemberDto) session.getAttribute("loginUser");
    notifyListDto.setRecipientId(loginUser.getMemberNo());
    Page<NotifyListDto> list = notifyService.getNotifyList(notifyListDto.getRecipientId(), page);
    log.debug("list = " + list);
    return ResponseEntity.ok().body(list);
  }

  @PostMapping("update")
  public ResponseEntity<?> updateRead(@RequestBody NotifyUpdateDto updateDto) {
    int id = updateDto.getId();
    if (!notifyService.existsById(id)) {
      return ResponseEntity.badRequest().build();
    }
    notifyService.updateRead(id);
    return ResponseEntity.ok().build();
  }
}
