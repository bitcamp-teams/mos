package com.mos.global;

import com.mos.domain.code.controller.CodeApiController;
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


  @GetMapping("/admin")
  public String indexAdmin(Model model) {

    model.addAttribute("", "");

    return "admin/index-admin";
  }

  @GetMapping("/")
  public String index(Model model) {
    System.out.println();
    return "index.html";
  }


}
