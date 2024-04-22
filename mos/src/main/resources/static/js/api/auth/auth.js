
export default {
    modalHtml: async () => {
        return await axios.get('/auth/login',{
            responseType: 'document'
        })
    },
    signUp: async url => {
        return await axios.post('/member/add');
    }

}
