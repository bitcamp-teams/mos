
export default {
    modalHtml: async () => {
        return await axios.get('/auth/login',{
            responseType: 'document'
        })
    },
    signUp: async params => {
        return await axios.post('/api/v1/member/add', params);
    },
    findByEmail: async params => {
        return await axios.post('/api/v1/member/findByUsername', params);
    }


}
