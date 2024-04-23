package com.mos.domain.member.controller;

import com.mos.domain.member.dto.MemberDto;
import com.mos.domain.member.dto.MemberJoinDto;
import com.mos.domain.member.service.impl.DefaultMemberService;
import com.mos.global.common.ErrorCode;
import com.mos.global.common.message.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/member")
public class MemberApiController {

  private final DefaultMemberService memberService;

  @PostMapping("findByEmail")
  public Result<?> findByEmail(@RequestBody MemberDto memberDto) throws Exception {
    MemberDto member = memberService.get(memberDto.getEmail());
    if (member == null) {
      return new Result<>().setResultCode("fail").setErrorMessage(ErrorCode.USER_NOT_FOUND_DATA);
    }
    return new Result<>(member);
  }

  @PostMapping("findByUsername")
  public Result<?> findByUsername(@RequestBody @Valid MemberDto memberDto) throws Exception {
    MemberDto member = memberService.getName(memberDto.getUserName());
    if (member != null) {
      return new Result<>().setResultCode("fail").setErrorMessage(ErrorCode.EXIST_DATA);
    }
    return new Result<>(member);
  }

  @PostMapping("add")
  public Result<?> add(@RequestBody @Valid MemberJoinDto joinDto) {
    int result;
    try {
      result = memberService.join(joinDto);
    } catch (Exception e) {
      return new Result<>().setResultCode("fail").setErrorMessage(e.getMessage());
    }
    return new Result<>(result);
  }

  @GetMapping("view")
  public Result<?> view(int memberNo) throws Exception {
    MemberDto member = memberService.getNo(memberNo);
    if (member == null) {
      return new Result<>().setResultCode("fail").setErrorMessage(ErrorCode.USER_NOT_FOUND_DATA);
    }
    return new Result<>(member);
  }
}
