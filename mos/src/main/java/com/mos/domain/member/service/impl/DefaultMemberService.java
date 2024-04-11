package com.mos.domain.member.service.impl;

import com.mos.domain.member.dto.MemberDto;
import com.mos.domain.member.dto.MemberStudyDto;
import com.mos.domain.member.dto.MemberJoinDto;
import com.mos.domain.member.repository.MemberRepository;
import com.mos.domain.member.service.MemberService;
import java.util.List;
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
  public MemberDto getName(String username) {
    return memberRepository.findByUsername(username);
  }
    @Override
    public MemberDto getNo(int no) {

        return memberRepository.findByNo(no);
    }

    @Override
    public MemberDto getUsername(String username) {
        return memberRepository.findByUsername(username);
    }

  @Override
  public List<MemberStudyDto> findMyStudies(int no) {
    MemberDto member = memberRepository.findByNo(no);
    if (member == null) {
      throw new IllegalArgumentException("해당 사용자를 찾을 수 없습니다: " + no);
    }

    List<MemberStudyDto> myStudy = memberRepository.findMyStudies(no);
    return myStudy;
  }
    @Override
    public int join(MemberJoinDto joinDto) {
    return memberRepository.add(joinDto);
  }

    @Override
    public boolean existsByEmail(String email) {
      return memberRepository.existsByEmail(email);
    }
}
