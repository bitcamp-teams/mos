export const convertToObject = (arr) => {
    return arr.reduce((acc, [key, value]) => {
        let keys = key.split(".");
        let temp = acc;

        for (let i = 0; i < keys.length - 1; i++) {
            if (!temp[keys[i]]) {
                temp[keys[i]] = {};
            }
            temp = temp[keys[i]]
        }
        temp[keys[keys.length - 1]] = value;
        return acc;
    }, {});
}

export const windowOpenHandler = (
    name = '_blank',
    url,
    options = '',
    $height = 500,
    $width = 600,
) => {
    const windowWidth = $width;
    const windowHeight = $height;
    const left = Math.round(window.screen.width / 2 - windowWidth / 2);
    const top = Math.round(window.screen.height / 2 - windowHeight / 2);

    const target = `${name}`;
    const defaultOption = `width=${windowWidth},height=${windowHeight},top=${top},left=${left},toolbar=no,titlebar=no,scrollbars=no,status=no,location=no,menubar=no,frame=no`;
    return window.open(url, target, options ? options : `${defaultOption}`);
};

export const formatDate = (dateString) => {
    const date = new Date(dateString);
    const now = new Date();
    const timeDiff = now.getTime() - date.getTime();

    if (timeDiff < 60000) { // 1분 미만
        return '방금 전';
    } else if (timeDiff < 3600000) { // 1시간 미만
        const minutes = Math.floor(timeDiff / 60000);
        return `${minutes}분 전`;
    } else if (timeDiff < 86400000) { // 24시간 미만
        const hours = Math.floor(timeDiff / 3600000);
        return `${hours}시간 전`;
    } else { // 24시간 이상
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');
        return `${year}-${month}-${day}`;
    }
}

