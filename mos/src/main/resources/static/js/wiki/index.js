import wiki from "../api/wiki/wiki.js";
import {excludeImg, formatDate} from "../util/util.js";

const index = {
    currentPage: 0,
    totalPages: 0,
    init() {
        const _this = this;
        document.addEventListener('DOMContentLoaded', function () {
            _this.initLoad();
        });

        $(window).scroll(function () {
            //전체 문서의 높이
            const documentHeight = document.body.offsetHeight;
            //(현재 화면상단 + 현재 화면 높이)
            const nowHeight = window.scrollY + window.innerHeight;
            if (nowHeight >= documentHeight) {
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
        wiki.findAll(page).then(res => {
            if (res.data == null) {
                throw new Error('위키 정보를 불러올 수 없음');
            }
            _this.totalPages = res.data.totalPages;
            _this.currentPage++;
            return res.data.content;
        }).then(wikiList => {
            if (wikiList.length === 0) {
                cardsContainer.html('데이터가 없습니다.');
            } else {
                cardsContainer.append(_this.createCards(wikiList));
            }
        });
    },
    createCards(wikiList) {
        const cardsArr = [];
        console.log(wikiList)
        wikiList.forEach(function (wiki) {
            const li = $('<li class="PostCard_block"></li>');
            // 썸네일 영역
            const thumbnail = $(`<a href="/wiki/view?studyNo=${wiki.studyNo}&wikiNo=${wiki.wikiNo}" class="VLink_block PostCard_styleLink">
                                            <div class="RatioImage_block" style="padding-top: 52.1921%;">
                                                <img alt="thumbnail"
                                                     fetchpriority="high"
                                                     decoding="async"
                                                     data-nimg="fill"
                                                     src="https://picsum.photos/320/167"
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
            const userInfo = $(`<div class="PostCard_footer"><a class="PostCard_userInfo" href="/member/dashboard?no=${wiki.memberNo}">
                                            <img
                                                    alt="user thumbnail of heyday.xz" loading="lazy" width="24" height="24"
                                                    decoding="async"
                                                    data-nimg="1"
                                                    src="https://picsum.photos/24/24"
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

index.init();
