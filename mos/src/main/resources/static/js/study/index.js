import study from "../api/study/study.js";
import {formatDate} from "../util/util.js";

let base = 167;
let circle = 10;
let count = 0;

const index = {
    currentPage: 0,
    totalPages: 0,
    flag: 'hit_count',
    totalCount: 0,
    searchText: '',
    init() {
        const _this = this;
        $('.HomeTab_tab > a').on('click', function (e) {
            e.preventDefault();
            $('.loader').show();
            $('.HomeTab_tab > a').removeClass('HomeTab_active');
            $(this).addClass('HomeTab_active');
            const flag = $(this).attr('data-flag');
            if (flag === 'trending') {
                _this.flag = 'hit_count';
                $('.HomeTab_indicator').css('left', '2%');
                $('#primary-stroke').css('stroke', 'rgb(0, 0, 0)');
                $('#secondary-stroke').css('stroke', 'rgb(44, 169, 188)');
                $('#tertiary-fill').css('fill', '#b7b7b7');
            } else if (flag === 'recent') {
                _this.flag = 'created_date';
                $('.HomeTab_indicator').css('left', '60.33%');
                $('#primary-stroke').css('stroke', '');
                $('#secondary-stroke').css('stroke', '');
            }
            _this.initLoad(_this.flag);
        })

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
                    _this.loadStudy(0, false, _this.flag);
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
                console.log(_this.currentPage, _this.totalPages)
                if (_this.currentPage < _this.totalPages) {
                    _this.loadStudy(_this.currentPage, true);
                }
            }
        });

    },
    initLoad(flag = 'hit_count') {
        const _this = this;
        // 초기 페이지 번호 설정
        _this.currentPage = 0;
        _this.totalPages = 0;
        $(document).scrollTop(0);

        _this.loadStudy(_this.currentPage, false, flag);
    },
    loadStudy(page, append = false, flag = 'hit_count') {
        const _this = this;
        const cardsContainer = $('.PostCardGrid_block');

        const params = {
            page: page,
            flag: flag,
            searchText: _this.searchText
        };

        $('.loader').show();
        if (!append) {
            cardsContainer.html('');
        }

        study.findAll(params).then(res => {
            if (res.data == null) {
                throw new Error('스터디 정보를 불러올 수 없음');
            }
            $('#totalCount').text(res.data.totalElements);
            _this.totalPages = res.data.totalPages;
            _this.currentPage++;
            return res.data.content;
        }).then(studyList => {
            if (!studyList || studyList.length === 0) {
                cardsContainer.append('<div>데이터가 없습니다.</div>');
            } else {
                cardsContainer.append(_this.createCards(studyList));
            }
            $('.loader').delay('500').fadeOut();
        });
    },
    createCards(studyList) {
        const cardsArr = [];
        studyList.forEach(function (study) {

      //썸네일 교체. 우선순위: UUID/1번이미지/랜덤이미지
      let thumbnailUrl = 'https://picsum.photos/320/' + (base + count++
          % circle);
      if (study.thumbnail != null) {
        thumbnailUrl = study.thumbnail;
      } else if (extractImageUrl(study.introduction) != null) {
        thumbnailUrl = extractImageUrl(study.introduction);
      }

            // 썸네일 영역
            const li = $('<li class="PostCard_block"></li>');
            const thumbnail = $(`<a href="/study/view?studyNo=${study.studyNo}" class="VLink_block PostCard_styleLink">
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
            const date = formatDate(study.createdDate);
            // photo 가 없으면 ''
            const photo = (study.photo != null)
                ? `https://4l8fsxs62676.edge.naverncp.com/iBroLT7rzG/member/${study.photo}?type=f&w=32&h=32&ttype=jpg`
                : '/img/icon2.png';
            const content = $(`<div class="PostCard_content">
                                            <a href="/study/view?studyNo=${study.studyNo}"
                                               class="VLink_block PostCard_styleLink">
                                                <h4 class="PostCard_h4 utils_ellipsis">${study.title}</h4>
                                                <div class="PostCard_descriptionWrapper">
                                                    <p class="PostCard_clamp">${study.introduction}</p>
                                                </div>
                                            </a>
                                            <div class="PostCard_subInfo"><span>${date}</span>
                                                <span class="PostCard_separator">·</span><span>${study.commentCount}개의 댓글</span>
                                            </div>
                                        </div>`);
            // 유저 정보
            const userInfo = $(`<div class="PostCard_footer"><a class="PostCard_userInfo" href="/member/dashboard?no=${study.memberNo}">
                                            <img
                                                    alt="user thumbnail of heyday.xz" loading="lazy" width="24" height="24"
                                                    decoding="async"
                                                    data-nimg="1"
                                                    src="${photo}"
                                                    style="color: transparent;"><span>by <b>${study.leaderName}</b></span></a>
                                            <div class="PostCard_likes">
                                                <svg viewBox="0 0 24 24">
                                                    <path fill="currentColor" d="m18 1-6 4-6-4-6 5v7l12 10 12-10V6z"></path>
                                                </svg>
                                                ${study.likeCount}
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
