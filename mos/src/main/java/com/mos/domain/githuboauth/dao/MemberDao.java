package com.mos.domain.githuboauth.dao;

import com.mos.vo.Member;
import com.mos.vo.MemberJoinDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Mapper
@Component
public interface MemberDao {
    public int add(MemberJoinDto joinDto);

}
