package com.mos.global.auth.controller;

import com.mos.domain.member.service.MemberService;
import com.mos.global.auth.dto.OAuthDto;
import com.mos.global.auth.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
@CrossOrigin("*")
@RequestMapping("/auth")
public class OAuthController {

  private static final Log log = LogFactory.getLog(OAuthController.class);

  private final KakaoService kakaoService;
  private final MemberService memberService;

  @Value("${github.clientId}")
  private String clientId;

  @GetMapping("login")
  public String login(Model model) {

    // 카카오 인증 URL 저장
    model.addAttribute("kakaoUrl", kakaoService.getKakaoLogin());
    model.addAttribute("clientId", clientId);
    //log.debug(String.format("requestUrl : %s", model));

    return "auth/login";
  }


  @GetMapping("/kakao/callback")
  public String callback(@RequestParam String code) throws Exception {
    OAuthDto kakaoInfo = kakaoService.getAccessToken(code);
    System.out.println("이메일: " + kakaoInfo.getEmail());
    System.out.println("닉네임: " + kakaoInfo.getNickname());

    if (memberService.get(kakaoInfo.getEmail()) != null) {
      System.out.println("로그인 성공!!!!!!!!");
      return "/index";
    }

    System.out.println("회원 정보가 없음!!!!!!!!");
    return "auth/signup";
  }

  @GetMapping("signup")
  public void signup() {
  }

}
