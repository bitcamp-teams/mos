export default {
    findAll: async params => {
        return await axios.get('/api/wiki/list', {params})
    },



}
