package com.mos.domain.study.repository;

import com.mos.domain.study.dto.AttachedFileDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AttachedFileRepository {

    void add(AttachedFileDto file);

    int addAll(List<AttachedFileDto> files);

    int delete(int studyNo);

    int deleteAll(int studyNo);

    List<AttachedFileDto> findAllByStudyNo(int studyNo);

    AttachedFileDto findByStudyNo(int studyNo);

}
