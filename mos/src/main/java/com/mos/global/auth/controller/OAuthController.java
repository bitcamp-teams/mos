package com.mos.global.auth.controller;

import com.mos.domain.member.dto.MemberDto;
import com.mos.domain.member.dto.MemberJoinDto;
import com.mos.domain.member.service.MemberService;
import com.mos.global.auth.handler.LoginApiManager;
import com.mos.global.auth.handler.OAuthRequestParam;
import com.mos.global.auth.handler.response.LoginResponseHandler;
import com.mos.global.auth.service.KakaoService;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
  {
    restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
  }


  @GetMapping("/auth/login")
  public String login(Model model) {

    model.addAttribute("kakaoUrl", kakaoService.getKakaoLogin());
    model.addAttribute("clientId", OAuthRequestParam.GITHUB_CLIENT_ID.getParam());
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

  @GetMapping("logout")
  public String logout(HttpSession session) throws Exception {
    session.invalidate();
    return "redirect:/";
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

  @PostMapping("signup")
  public void signup(@RequestParam String username) {
    // 중복확인 완료한 후 가입을 진행하던 중
    // 그 시점에 같은 닉네임으로 가입을 완료한 사람이 있다면??

    /*MemberDto member = memberService.getName(username);

    if (memberService.getName(username) != null) {
      System.out.println("중복된 닉네임입니다.");
      return "auth/signup";
    }

    System.out.println("사용가능한 닉네임입니다!!");
    return "auth/signup";*/
  }

  // 닉네임 중복확인
  @GetMapping("checkUsername")
  public ResponseEntity<String> checkUsername(@RequestParam String username) {
    MemberDto member = memberService.getName(username);

    if (member != null) {
      // 이미 존재하는 닉네임인 경우
      return ResponseEntity.ok("duplicate");
    } else {
      // 존재하지 않는 닉네임인 경우
      return ResponseEntity.ok("available");
    }
  }

  @PostMapping("signup2")
  public void signup2() {

  }
}
