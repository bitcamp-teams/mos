export default {
    findAll: async params => {
        return await axios.get('/api/wiki/list', {
            params: {
                page: params.page,
                searchText: params.searchText
            }
        })
    },

}
