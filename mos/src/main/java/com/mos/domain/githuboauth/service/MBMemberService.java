//package com.mos.domain.githuboauth.service;
//
//import com.mos.dao.MemberDao;
//import com.mos.vo.Member;
//import com.mos.vo.MemberJoinDto;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class MBMemberService implements MemberService{
//    private final MemberDao memberDao;
//    private GithubOAuthService githubService;
//
//    @Autowired
//    public MBMemberService(MemberDao memberDao) {
//        this.memberDao = memberDao;
//    }
//
//    @Override
//    public int add(MemberJoinDto joinDto) {
//        return memberDao.add(joinDto);
//    }
//}
