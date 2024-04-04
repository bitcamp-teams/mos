package com.mos.domain.member.service.impl;

import com.mos.domain.member.dto.MemberDto;
import com.mos.domain.member.repository.MemberRepository;
import com.mos.domain.member.service.MemberService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DefaultMemberService implements MemberService {

  private final MemberRepository memberRepository;

  @Override
  public MemberDto get(String email) {
    return memberRepository.findByEmail(email);
  }

    @Override
    public MemberDto getNo(int no) {
        return memberRepository.findByNo(no);
    }

    @Override
    public MemberDto getUsername(String username) {
        return memberRepository.findByUsername(username);
    }

}
