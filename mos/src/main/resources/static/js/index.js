import wiki from "./api/wiki/wiki.js";

const index = {
    page: 0,
    isLastPage: false,
    loading: false,
    debounceTimer: null,
    init() {
        const _this = this;
        $(document).scroll(function () {
            clearTimeout(_this.debounceTimer);
            _this.debounceTimer = setTimeout(() => _this.checkScrollPosition(), 400);
        });

        _this.loadWiki();
    },
    loadWiki() {
        const _this = this;
        if (!_this.loading && !_this.isLastPage) {
            _this.loading = true;
            const params = _this.page;
            wiki.findAll(params).then(res => {
                if (res.data.last) {
                    _this.isLastPage = true;
                }
                const wikiList = res.data.content;
                const cardsContainer = $('.PostCardGrid_block');
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
                    const content = $(`<div class="PostCard_content">
                                            <a href="/wiki/view?studyNo=${wiki.studyNo}&wikiNo=${wiki.wikiNo}"
                                               class="VLink_block PostCard_styleLink">
                                                <h4 class="PostCard_h4 utils_ellipsis">${wiki.title}</h4>
                                                <div class="PostCard_descriptionWrapper">
                                                    <p class="PostCard_clamp">${wiki.content}</p>
                                                </div>
                                            </a>
                                            <div class="PostCard_subInfo"><span>2일전</span>
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
                    cardsContainer.append(li);
                });

                _this.page++;

                if (res.data.last) {
                    _this.isLastPage = true;
                    $(document).off('scroll');
                }
            }).finally(() => {
                _this.loading = false;
                _this.checkScrollPosition();
            });
        }
    },
    checkScrollPosition() {
        const _this = this;
        const scrollBottom = $(document).scrollTop() + $(window).height();
        if (scrollBottom >= $(document).height() * 0.9) {
            _this.loadWiki();
        }
    }

};

index.init();
