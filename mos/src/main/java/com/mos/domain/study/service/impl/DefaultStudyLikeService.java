package com.mos.domain.study.service.impl;

import com.mos.domain.study.dto.StudyLikeStatDto;
import com.mos.domain.study.repository.StudyLikeStatRepository;
import com.mos.domain.study.repository.StudyRepository;
import com.mos.domain.study.service.StudyLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class DefaultStudyLikeService implements StudyLikeService {

    private final StudyLikeStatRepository studyLikeStatRepository;
    private final StudyRepository studyRepository;


    @Override
    public void addLike(StudyLikeStatDto studyLikeStatDto) {
       studyLikeStatRepository.like(studyLikeStatDto);
       int likeCount = studyLikeStatRepository.countLikesByStudyNo(studyLikeStatDto.getStudyNo());
       studyRepository.updateLikeCount(studyLikeStatDto.getStudyNo(), likeCount);
    }


    @Override
    public void deleteLike(StudyLikeStatDto studyLikeStatDto) {
        studyLikeStatRepository.unlike(studyLikeStatDto);
        int likeCount = studyLikeStatRepository.countLikesByStudyNo(studyLikeStatDto.getStudyNo());
        studyRepository.updateLikeCount(studyLikeStatDto.getStudyNo(), likeCount);
    }

    @Transactional(readOnly = true)
    @Override
    public int checked(StudyLikeStatDto studyLikeStatDto) {
        return studyLikeStatRepository.checked(studyLikeStatDto);
    }

    @Transactional(readOnly = true)
    @Override
    public int countLikesByStudyNo(int studyNo) {
        return studyLikeStatRepository.countLikesByStudyNo(studyNo);
    }
}
