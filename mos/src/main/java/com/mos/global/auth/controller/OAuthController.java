package com.mos.global.auth.controller;

import com.mos.domain.member.dto.MemberJoinDto;
import com.mos.domain.member.service.MemberService;
import com.mos.global.auth.handler.LoginApiManager;
import com.mos.global.auth.handler.OAuthRequestParam;
import com.mos.global.auth.handler.response.LoginResponseHandler;
import com.mos.global.auth.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Controller
@CrossOrigin("*")
@RequestMapping()
public class OAuthController {

  private static final Log log = LogFactory.getLog(OAuthController.class);

  private final KakaoService kakaoService;
  private final MemberService memberService;
  private final LoginApiManager loginApiManager;
  private final RestTemplate restTemplate = new RestTemplate();


  @GetMapping("/auth/login")
  public String login(Model model) {

    // 카카오 인증 URL 저장
    model.addAttribute("kakaoUrl", kakaoService.getKakaoLogin());
    model.addAttribute("clientId", OAuthRequestParam.GITHUB_CLIENT_ID);
    //log.debug(String.format("requestUrl : %s", model));

    return "auth/login";
  }

  // 카카오
  @GetMapping("/auth/kakao/callback")
  public String callback(@RequestParam String code) throws Exception {
    LoginResponseHandler kakaoInfo =
        loginApiManager.getProvider("KAKAO").getUserInfo(restTemplate, code);

    if (memberService.get(kakaoInfo.getEmail()) != null) {
      System.out.println("로그인 성공!!!!!!!!");
      return "/index";
    }

    System.out.println("회원 정보가 없음!!!!!!!!");
    return "auth/signup";
  }

  // 깃헙
  @GetMapping("callback")
  public String githubLogin(@RequestParam String code, MemberJoinDto joinDto, Model model) {
    LoginResponseHandler githubInfo =
        loginApiManager.getProvider("GITHUB").getUserInfo(restTemplate, code);

    if (!githubInfo.getEmail().isEmpty()) {
      joinDto.setEmail(githubInfo.getEmail());
      if (memberService.existsByEmail(joinDto.getEmail())) {
        return "redirect:/";
      }
      model.addAttribute("joinDto", joinDto);
    } else {
      model.addAttribute("error", "github 로그인 실패");
    }
    return "auth/form";
  }

  @GetMapping("signup")
  public void signup() {
  }

}
