package com.mos.domain.code.controller;

import com.mos.domain.code.dto.CodeDto;
import com.mos.domain.code.dto.CodeGroupDto;
import com.mos.domain.code.entity.Code;
import com.mos.domain.code.entity.CodeGroup;
import com.mos.domain.code.service.CodeService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/code")
public class CodeController {
  private static final Log log = LogFactory.getLog(CodeController.class);

  private final CodeService codeService;


  @PostMapping("add")
  public void add(CodeDto codeDto) throws Exception {
    codeService.add(codeDto);

  }

  @PostMapping("add/group")
  public void addCodeGroup(CodeGroupDto codeGroupDto) throws Exception {
    codeService.addCodeGroup(codeGroupDto);
  }

  @GetMapping("list/group")
  public ResponseEntity<List<CodeGroupDto>> listGroup() {
    List<CodeGroupDto> codeGroups = codeService.listGroup();
    HttpHeaders headers= new HttpHeaders();
    headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
    return new ResponseEntity<>(codeGroups, headers, HttpStatus.OK);
  }

  @GetMapping("list")
  public ResponseEntity<List<CodeDto>> list() {
    HttpHeaders headers= new HttpHeaders();
    headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
    List<CodeDto> list = codeService.list();
    return new ResponseEntity<>(list, headers, HttpStatus.OK);
  }
}
