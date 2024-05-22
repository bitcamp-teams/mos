export default {
    findAll: async params => {
        return await axios.get(`/api/v1/study/list/${params.flag}`, {
            params: {
                page: params.page,
                searchText: params.searchText
            }
        })
    },


}
