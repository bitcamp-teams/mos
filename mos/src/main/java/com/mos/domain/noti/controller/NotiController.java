package com.mos.domain.noti.controller;

import com.mos.domain.member.dto.MemberDto;
import com.mos.domain.noti.dto.NotiAddDto;
import com.mos.domain.noti.dto.NotiDto;
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
  public ResponseEntity<NotiAddDto> save(@ModelAttribute NotiAddDto notiDto) {
    log.debug("notiDto = " + notiDto);
    notiService.add(notiDto);
    return ResponseEntity.ok(notiDto);
  }

  @GetMapping("/noti/list")
  public ResponseEntity<?> list(@RequestParam Map<String, Object> paramMap, HttpSession session, @PageableDefault Pageable page) {
    MemberDto loginUser = (MemberDto) session.getAttribute("loginUser");
    int no = loginUser.getMemberNo();

    log.debug("no = " + no);
    paramMap.put("recipientId", no);
    System.out.println("paramMap = " + paramMap);
    Page<Map<String, Object>> list = notiService.list(paramMap, page);
    log.debug("list = " + list);
    Map<String, Object> resultMap = new HashMap<>();
    resultMap.put("list", list);
    resultMap.put("size", page.getPageSize());
    return ResponseEntity.ok(resultMap);
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