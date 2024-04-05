package com.mos.domain.code.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mos.domain.code.dto.CodeGroupRequestDto;
import com.mos.domain.code.dto.CodeGroupResponseDto;
import com.mos.domain.code.dto.CodeRequestDto;
import com.mos.domain.code.dto.CodeResponseDto;
import com.mos.domain.code.service.CodeService;
import com.mos.global.common.message.Result;
import com.mos.global.common.paging.Paging;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/code")
public class CodeController {
  private static final Log log = LogFactory.getLog(CodeController.class);

  private final CodeService codeService;


  @GetMapping("list")
  public String add() throws Exception {
    return "code/list";
  }


}
