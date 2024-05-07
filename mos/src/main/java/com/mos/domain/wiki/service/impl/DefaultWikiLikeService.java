package com.mos.domain.wiki.service.impl;

import com.mos.domain.wiki.dto.WikiLikeStatDto;
import com.mos.domain.wiki.repository.WikiLikeStatRepository;
import com.mos.domain.wiki.repository.WikiRepository;
import com.mos.domain.wiki.service.WikiLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultWikiLikeService  implements WikiLikeService {

    private final WikiLikeStatRepository wikiLikeStatRepository;
    private final WikiRepository wikiRepository;

    @Override
    public void addLike(WikiLikeStatDto wikiLikeStatDto) {
        wikiLikeStatRepository.like(wikiLikeStatDto);
        int likes = wikiLikeStatRepository.countLikesByWikiNo(wikiLikeStatDto.getWikiNo());
        wikiRepository.updateLikeCount(wikiLikeStatDto.getWikiNo(), likes);
    }

    @Override
    public void deleteLike(WikiLikeStatDto wikiLikeStatDto) {
        wikiLikeStatRepository.unlike(wikiLikeStatDto);
        int likes = wikiLikeStatRepository.countLikesByWikiNo(wikiLikeStatDto.getWikiNo());
        wikiRepository.updateLikeCount(wikiLikeStatDto.getWikiNo(), likes);
    }

    @Override
    public int checked(WikiLikeStatDto wikiLikeStatDto) {
        return wikiLikeStatRepository.checked(wikiLikeStatDto);
    }

    @Override
    public int countLikesByWikiNo(int wikiNo) {
        return wikiLikeStatRepository.countLikesByWikiNo(wikiNo);
    }
}
