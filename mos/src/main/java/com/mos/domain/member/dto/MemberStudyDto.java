package com.mos.domain.member.dto;

import com.mos.domain.study.dto.StudyDto;
import com.mos.domain.member.dto.MemberDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MemberStudyDto {

    private int no;

    private MemberDto memberDto;

    private StudyDto studyDto;

    private String status;

    private String favorites;

    private String applyMsg;

}
