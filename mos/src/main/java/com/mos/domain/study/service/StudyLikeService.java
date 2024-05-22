package com.mos.domain.study.service;

import com.mos.domain.study.dto.StudyLikeStatDto;

public interface StudyLikeService {

    void addLike(StudyLikeStatDto studyLikeStatDto);
    void deleteLike(StudyLikeStatDto studyLikeStatDto);
    int checked(StudyLikeStatDto studyLikeStatDto);
    int countLikesByStudyNo(int studyNo);
}

