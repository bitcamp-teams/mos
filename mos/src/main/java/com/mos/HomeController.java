package com.mos;

import com.mos.global.auth.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequiredArgsConstructor
@Controller
@RequestMapping
public class HomeController {


  private static final Log log = LogFactory.getLog(HomeController.class);
  private final KakaoService kakaoService;

  /*@RequestMapping(value="/", method= RequestMethod.GET)
  public String home() {

    //return "login";
  }*/

}
