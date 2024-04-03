package com.mos.domain.githuboauth.controller;

import com.mos.dao.MemberDao;
import com.mos.service.GithubOAuthService;
import com.mos.service.MBMemberService;
import com.mos.service.MemberService;
import com.mos.vo.MemberJoinDto;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GithubOAuthController {

  @Autowired
  private GithubOAuthService loginService;

  @GetMapping("callback")
  public String githubLogin(@RequestParam String code, Model model) throws IOException{
    String email = loginService.getAccessToken(code);
    model.addAttribute("email", email);
    return "member/form";
  }
}
