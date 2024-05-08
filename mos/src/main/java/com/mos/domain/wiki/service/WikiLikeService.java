package com.mos.domain.wiki.service;

import com.mos.domain.wiki.dto.WikiLikeStatDto;

public interface WikiLikeService {

    void addLike(WikiLikeStatDto wikiLikeStatDto);
    void deleteLike(WikiLikeStatDto wikiLikeStatDto);
    int checked(WikiLikeStatDto wikiLikeStatDto);
    int countLikesByWikiNo(int wikiNo);
}


