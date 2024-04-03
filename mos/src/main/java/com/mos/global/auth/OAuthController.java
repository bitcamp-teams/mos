package com.mos.global.auth;

import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@CrossOrigin("*")
@RequestMapping("/auth")
public class OAuthController {

  private static final Log log = LogFactory.getLog(OAuthController.class);

  @GetMapping("login")
  public void login() {

  }


}
