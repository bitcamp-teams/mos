
export default {
    modalHtml: async () => {
        return await axios.get('/auth/login',{
            responseType: 'document'
        })
    }

}
