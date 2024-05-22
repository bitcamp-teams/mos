document.addEventListener('DOMContentLoaded', function () {
        // 페이지가 로드될 때 이전에 저장된 검색 값이 있는지 확인
        const searchParams = new URLSearchParams(window.location.search);
        const type = searchParams.get('type');
        const keyword = searchParams.get('keyword');

        // 검색 타입과 키워드를 폼 요소에 설정
        const typeSelect = document.getElementById('type');
        const keywordInput = document.getElementById('keyword');

        if (type) {
            // 검색 타입이 저장된 경우 선택된 값으로 설정
            typeSelect.value = type;
        }

        if (keyword) {
            // 검색 키워드가 저장된 경우 입력 필드에 설정
            keywordInput.value = keyword;
        }
    });

    function clearSearch() {
        // 검색 버튼을 클릭할 때 호출되는 함수
        // 필요한 경우 추가적인 작업을 수행할 수 있음
        // 여기서는 아무 작업도 하지 않음
    }

    if (this.placeholder && typeof this.placeholder.id !== 'undefined') {
        this.$element.val(this.placeholder.id);
    } else {
        console.error('this.placeholder가 존재하지 않거나 id 속성이 정의되지 않았습니다.');
    }