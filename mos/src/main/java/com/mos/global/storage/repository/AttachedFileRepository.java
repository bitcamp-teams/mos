package com.mos.global.storage.repository;

import com.mos.domain.study.dto.AttachedFileStudyDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AttachedFileRepository {

    void add(AttachedFileStudyDto file);

    int addAll(List<AttachedFileStudyDto> files);

    int delete(int fileNo);

    int deleteAll(int studyNo);

    List<AttachedFileStudyDto> findAllByStudyNo(int studyNo);

    AttachedFileStudyDto findByFileNo(int fileNo);

}
