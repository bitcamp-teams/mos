import wiki from "../api/wiki/wiki.js";
import {excludeImg, formatDate} from "../util/util.js";

let base = 167;
let circle = 10;
let count = 0;

const index = {
  currentPage: 0,
  totalPages: 0,
  searchText: '',
  init() {
    const _this = this;
    document.addEventListener('DOMContentLoaded', function () {
      _this.initLoad();
      $('.search_searchInput').on('keypress', function (e) {
        $('.search_searchInitialize').show();
        if ($(this).val() === '') {
          $('.search_searchInitialize').hide();
        }
        // 엔터 치면 검색
        if (e.keyCode === 13) {
          _this.searchText = $(this).val();
          _this.loadWiki(0, false);
        }
      });
      $('.search_searchInitialize').on('click', function () {
        $('.search_searchInput').val('');
        $('.search_searchInitialize').hide();
        _this.searchText = '';
      });
    });

    $(window).scroll(function () {
      //전체 문서의 높이
      const documentHeight = document.body.offsetHeight;
      //(현재 화면상단 + 현재 화면 높이)
      const nowHeight = window.scrollY + window.innerHeight;
      if (nowHeight >= documentHeight) {
        console.log(_this.currentPage, _this.totalPages);
        if (_this.currentPage < _this.totalPages) {
          _this.loadWiki(_this.currentPage, true);
        }
      }
    });

  },
  initLoad() {
    const _this = this;
    // 초기 페이지 번호 설정
    _this.currentPage = 0;
    _this.totalPages = 0;
    $(document).scrollTop(0);

    _this.loadWiki(_this.currentPage);
  },
  loadWiki(page, append = false) {
    const _this = this;
    const cardsContainer = $('.PostCardGrid_block');

    $('.loader').show();
    if (!append) {
      cardsContainer.html('');
    }

    const params = {
      page: page,
      searchText: _this.searchText
    };

    wiki.findAll(params).then(res => {
      console.log(res.data.totalElements);
      if (res.data == null) {
        throw new Error('위키 정보를 불러올 수 없음');
      }
      $('#totalWikiCount').text(res.data.totalElements);
      _this.totalPages = res.data.totalPages;
      _this.currentPage++;
      return res.data.content;
    }).then(wikiList => {
      if (wikiList.length === 0) {
        cardsContainer.html('데이터가 없습니다.');
      } else {
        cardsContainer.append(_this.createCards(wikiList));
      }
      $('.loader').delay('500').fadeOut();
    });
  },
  createCards(wikiList) {
    const cardsArr = [];
    wikiList.forEach(function (wiki) {
      //썸네일 교체. 우선순위: UUID/1번이미지/랜덤이미지

      let thumbnailUrl = 'https://picsum.photos/320/' + (base + count++
          % circle);
      if (wiki.thumbnail != null) {
        thumbnailUrl = wiki.thumbnail;
      } else if (extractImageUrl(wiki.content) != null) {
        thumbnailUrl = extractImageUrl(wiki.content);
      }
      const li = $('<li class="PostCard_block"></li>');
      // 썸네일 영역
      const thumbnail = $(
          `<a href="/wiki/view?studyNo=${wiki.studyNo}&wikiNo=${wiki.wikiNo}" class="VLink_block PostCard_styleLink">
                                            <div class="RatioImage_block" style="padding-top: 52.1921%;">
                                                <img alt="thumbnail"
                                                     fetchpriority="high"
                                                     decoding="async"
                                                     data-nimg="fill"
                                                     src="${thumbnailUrl}"
                                                     style="position: absolute; height: 100%; width: 100%; inset: 0px; color: transparent;">
                                            </div>
                                        </a>`);

      // 본문
      const date = formatDate(wiki.createdDate);
      const parsedData = excludeImg(wiki.content);
      const content = $(`<div class="PostCard_content">
                                            <a href="/wiki/view?studyNo=${wiki.studyNo}&wikiNo=${wiki.wikiNo}"
                                               class="VLink_block PostCard_styleLink">
                                                <h4 class="PostCard_h4 utils_ellipsis">${wiki.title}</h4>
                                                <div class="PostCard_descriptionWrapper">
                                                    <p class="PostCard_clamp">${parsedData}</p>
                                                </div>
                                            </a>
                                            <div class="PostCard_subInfo"><span>${date}</span>
                                                <span class="PostCard_separator">·</span><span>${wiki.commentCount}개의 댓글</span>
                                            </div>
                                        </div>`);
      // 유저 정보
      const photo = (wiki.photo != null)
          ? `https://4l8fsxs62676.edge.naverncp.com/iBroLT7rzG/member/${wiki.photo}?type=f&w=32&h=32&ttype=jpg`
          : '/img/icon2.png';
      const userInfo = $(
          `<div class="PostCard_footer"><a class="PostCard_userInfo" href="/member/dashboard?no=${wiki.memberNo}">
                                            <img
                                                    alt="user thumbnail of heyday.xz" loading="lazy" width="24" height="24"
                                                    decoding="async"
                                                    data-nimg="1"
                                                    src="${photo}"
                                                    style="color: transparent;"><span>by <b>${wiki.username}</b></span></a>
                                            <div class="PostCard_likes">
                                                <svg viewBox="0 0 24 24">
                                                    <path fill="currentColor" d="m18 1-6 4-6-4-6 5v7l12 10 12-10V6z"></path>
                                                </svg>
                                                ${wiki.likes}
                                            </div>
                                        </div>`)
      li.append(thumbnail);
      li.append(content);
      li.append(userInfo);

      cardsArr.push(li)
    });
    return cardsArr;
  }
};

function extractImageUrl(text) {
  // text가 null이나 undefined인 경우 null 반환
  if (!text) {
    return null;
  }

  const regex = /!\[(.*?)]\((.*?)\)/;
  const match = text.match(regex);

  if (match && match.length > 2 && isValidImageUrl(match[2])) {
    return match[2];
  }

  return null;
}

function isValidImageUrl(url) {
  return new Promise((resolve) => {
    // URL이 비어있거나 null인 경우 false 반환
    if (!url) {
      resolve(false);
      return;
    }

    try {
      // URL을 생성하고, 프로토콜이 http나 https인지 확인
      const parsedUrl = new URL(url);
      if (!['http:', 'https:'].includes(parsedUrl.protocol)) {
        resolve(false);
        return;
      }

      // 이미지 확장자 검사
      const validExtensions = ['jpg', 'jpeg', 'png', 'gif', 'bmp', 'webp'];
      const extension = parsedUrl.pathname.split('.').pop().toLowerCase();
      if (!validExtensions.includes(extension)) {
        resolve(false);
        return;
      }

      // Image 객체를 사용하여 이미지 로드 및 렌더링 가능 여부 확인
      const img = new Image();
      img.onload = () => resolve(true);
      img.onerror = () => resolve(false);
      img.src = url;
    } catch (error) {
      // URL 형식이 잘못된 경우 false 반환
      resolve(false);
    }
  });
}

index.init();
