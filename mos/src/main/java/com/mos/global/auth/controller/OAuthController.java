package com.mos.global.auth.controller;

import com.mos.global.auth.dto.KakaoDTO;
import com.mos.global.auth.service.KakaoService;
import com.mos.global.common.MsgEntity;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequiredArgsConstructor
@Controller
@CrossOrigin("*")
@RequestMapping("/auth")
public class OAuthController {

  private static final Log log = LogFactory.getLog(OAuthController.class);

  private final KakaoService kakaoService;

  @GetMapping("login")
  public String login(Model model) {

    // 카카오 인증 URL 저장
    model.addAttribute("kakaoUrl", kakaoService.getKakaoLogin());

    //log.debug(String.format("requestUrl : %s", model));

    return "auth/login";
  }

  /* 카카오 REST API 사용자 정보 가져오기 테스트
  @GetMapping("/kakao/callback")
  public ResponseEntity<MsgEntity> callback(HttpServletRequest request) throws Exception {
     KakaoDTO kakaoInfo = kakaoService.getKakaoInfo(request.getParameter("code"));
     System.out.println("이메일: " + kakaoInfo.getEmail());
     System.out.println("닉네임: " + kakaoInfo.getNickname());

    return ResponseEntity.ok()
        .body(new MsgEntity("Success", kakaoInfo));
  }
  */

  @GetMapping("/kakao/callback")
  public String callback(HttpServletRequest request) throws Exception {
    KakaoDTO kakaoInfo = kakaoService.getKakaoInfo(request.getParameter("code"));
    System.out.println("이메일: " + kakaoInfo.getEmail());
    System.out.println("닉네임: " + kakaoInfo.getNickname());

    return "/index";
  }


}
