
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
