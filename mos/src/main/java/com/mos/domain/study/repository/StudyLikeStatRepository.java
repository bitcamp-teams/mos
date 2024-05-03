package com.mos.domain.study.repository;

import com.mos.domain.study.dto.StudyLikeStatDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface StudyLikeStatRepository {

    int like(StudyLikeStatDto studyLikeStatDto);
    int unlike(StudyLikeStatDto studyLikeStatDto);
    int checked(StudyLikeStatDto studyLikeStatDto);
    int countLikesByStudyNo(int studyNo);

}
