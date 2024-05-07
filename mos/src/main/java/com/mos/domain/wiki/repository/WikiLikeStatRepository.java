package com.mos.domain.wiki.repository;

import com.mos.domain.wiki.dto.WikiLikeStatDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WikiLikeStatRepository {

    int like(WikiLikeStatDto wikiLikeStatDto);
    int unlike(WikiLikeStatDto wikiLikeStatDto);
    int checked(WikiLikeStatDto wikiLikeStatDto);
    int countLikesByWikiNo(int wikiNo);

}
