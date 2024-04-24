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


