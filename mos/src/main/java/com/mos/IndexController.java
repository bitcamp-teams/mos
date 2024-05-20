package com.mos;

import com.mos.domain.code.controller.CodeApiController;
import com.mos.domain.member.dto.MemberDto;
import com.mos.global.auth.LoginUser;
import com.mos.global.auth.service.SessionService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.SessionAttribute;

@RequiredArgsConstructor
@Controller
public class IndexController {
  private final SessionService sessionService;

  @GetMapping("/admin")
  public String indexAdmin(Model model) {

    model.addAttribute("", "");

    return "admin/index-admin";
  }

  @GetMapping("/")
  public String index(HttpSession session) {
    int activeSession = sessionService.findAllSession();
    session.setAttribute("activeSessions", activeSession);
    return "index";
  }

  @GetMapping("/about")
  public String showAboutPage() {
    return "about";
  }

}
