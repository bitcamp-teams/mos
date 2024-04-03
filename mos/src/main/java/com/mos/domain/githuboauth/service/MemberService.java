package com.mos.domain.githuboauth.service;

import com.mos.vo.Member;
import com.mos.vo.MemberJoinDto;

public interface MemberService {
    int add(MemberJoinDto joinDto);

}
