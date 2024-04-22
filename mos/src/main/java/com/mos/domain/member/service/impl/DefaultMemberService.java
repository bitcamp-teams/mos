package com.mos.domain.member.service.impl;

import com.mos.domain.member.dto.MemberDto;
import com.mos.domain.member.dto.MemberStudyDto;
import com.mos.domain.member.dto.MemberJoinDto;
import com.mos.domain.member.repository.MemberRepository;
import com.mos.domain.member.service.MemberService;
import com.mos.domain.study.dto.StudyDto;
import com.mos.domain.study.repository.StudyRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class DefaultMemberService implements MemberService {


  private final MemberRepository memberRepository;
  private final StudyRepository studyRepository;

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
    System.out.println("joinDto = " + joinDto);
    return memberRepository.add(joinDto);
  }

    @Override
    public boolean existsByEmail(String email) {
      return memberRepository.existsByEmail(email);
    }

  @Override
  public List<MemberStudyDto> viewMyStudies(int no) {
    StudyDto studyDto = studyRepository.getByStudyNo(no);

    List<MemberStudyDto> myStudy = memberRepository.viewMyStudies(no);
    return myStudy;
  }

  @Transactional
  @Override
  public int update(MemberDto member) {
    return memberRepository.update(member);
  }

  @Transactional
  @Override
  public int withdraw(MemberDto member) {
    return memberRepository.withdraw(member);
  }

  @Override
    public boolean existsByUserName(String username) {
      return memberRepository.existsByUserName(username);
    }
}
