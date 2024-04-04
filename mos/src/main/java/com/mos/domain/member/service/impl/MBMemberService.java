package com.mos.domain.member.service.impl;

import com.mos.domain.member.dto.MemberDto;
import com.mos.domain.member.dto.MemberJoinDto;
import com.mos.domain.member.repository.MemberRepository;
import com.mos.domain.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MBMemberService  {
    private final MemberRepository memberDao;
    private GithubOAuthService githubService;

    @Autowired
    public MBMemberService(MemberRepository memberDao) {
        this.memberDao = memberDao;
    }

    public int join(MemberJoinDto joinDto) {
        return memberDao.add(joinDto);
    }

    public boolean validateDuplicateUserEmail(String email) {
        return email.equals(memberDao.validateEmail(email));
    }
}
