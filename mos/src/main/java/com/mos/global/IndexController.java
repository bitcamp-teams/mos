package com.mos.global;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

  @GetMapping("/")
  public String index(Model model) {

    model.addAttribute("", "");

    return "index";
  }


  @GetMapping("/admin")
  public String indexAdmin(Model model) {

    model.addAttribute("", "");

    return "admin/index-admin";
  }


}
