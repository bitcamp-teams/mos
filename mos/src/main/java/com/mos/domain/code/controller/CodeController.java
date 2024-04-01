package com.mos.domain.code.controller;

import com.mos.domain.code.dto.Code;
import com.mos.domain.code.dto.CodeGroup;
import com.mos.domain.code.service.CodeService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/code")
public class CodeController {
  private static final Log log = LogFactory.getLog(CodeController.class);

  private final CodeService codeService;


  @PostMapping("add")
  public void add(Code code) throws Exception {
    codeService.add(code);

  }

  @PostMapping("add/group")
  public void addCodeGroup(CodeGroup codeGroup) throws Exception {
    codeService.addCodeGroup(codeGroup);
  }

  @GetMapping("list/group")
  public ResponseEntity<List<CodeGroup>> listGroup() {
    List<CodeGroup> codeGroups = codeService.listGroup();
    HttpHeaders headers= new HttpHeaders();
    headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

    return new ResponseEntity<>(codeGroups, headers, HttpStatus.OK);
  }

  @GetMapping("list")
  public ResponseEntity<List<Code>> list() {
    HttpHeaders headers= new HttpHeaders();
    headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
    return new ResponseEntity<>(codeService.list(), headers, HttpStatus.OK);
  }
}
