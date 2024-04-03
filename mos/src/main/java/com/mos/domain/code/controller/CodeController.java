package com.mos.domain.code.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mos.domain.code.dto.CodeRequestDto;
import com.mos.domain.code.dto.CodeGroupRequestDto;
import com.mos.domain.code.dto.CodeGroupResponseDto;
import com.mos.domain.code.dto.CodeResponseDto;
import com.mos.domain.code.service.CodeService;
import com.mos.global.common.message.Result;
import com.mos.global.common.paging.Paging;
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
  public void add(CodeRequestDto codeRequestDto) throws Exception {
    codeService.add(codeRequestDto);

  }

  @PostMapping("add/group")
  public void addCodeGroup(CodeGroupRequestDto codeGroupRequestDto) throws Exception {
    codeService.addCodeGroup(codeGroupRequestDto);
  }

  @GetMapping("list/group")
  public Result<List<CodeGroupResponseDto>> listGroup(@RequestBody CodeGroupRequestDto codeGroupRequestDto) {
    int totalCnt = codeService.countAllGroup();

    Paging paging = Paging.builder()
        .pageNo(codeGroupRequestDto.paging().getPageNo())
        .pageSize(codeGroupRequestDto.paging().getPageSize())
        .numOfRecord(totalCnt)
        .build();

    List<CodeGroupResponseDto> list = codeService.listGroup(paging);
    List<CodeGroupResponseDto> json =
        new Gson().fromJson(new Gson().toJson(list), new TypeToken<List<CodeGroupResponseDto>>() {}.getType());
    return new Result<>(json, paging);
  }

  @GetMapping("list")
  public Result<List<CodeResponseDto>> list(@RequestBody CodeRequestDto codeRequestDto) {
    int totalCnt = codeService.countAll();

    Paging paging = Paging.builder()
        .pageNo(codeRequestDto.paging().getPageNo())
        .pageSize(codeRequestDto.paging().getPageSize())
        .numOfRecord(totalCnt)
        .build();

    List<CodeResponseDto> list = codeService.list(paging);

    List<CodeResponseDto> json = new Gson().fromJson(new Gson().toJson(list), new TypeToken<List<CodeResponseDto>>() {}.getType());
    return new Result<>(json, paging);
  }
}
