package com.mos;

import com.mos.domain.code.controller.CodeApiController;
import com.mos.domain.member.dto.MemberDto;
import com.mos.global.auth.LoginUser;
import com.mos.global.auth.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class IndexController {
  private static final Log log = LogFactory.getLog(CodeApiController.class);
  private final SessionService sessionService;

  @GetMapping("/admin")
  public String indexAdmin(Model model) {

    model.addAttribute("", "");

    return "admin/index-admin";
  }

  @GetMapping("/")
  public String index(Model model, @LoginUser MemberDto user) {
    model.addAttribute("loginUser", user);
    int activeSession = sessionService.findAllSession();
    model.addAttribute("activeSessions", activeSession);
    return "index";
  }

}
