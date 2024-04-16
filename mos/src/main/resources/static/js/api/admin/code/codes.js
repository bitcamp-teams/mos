
export default {
    listCode: async param => {
        return await axios.post('/api/v1/admin/code/list', param);
    },
    listCodeGroup: async param => {
        return await axios.post('/api/v1/admin/code/list/group', param);
    },
    createCode: async param => {
        return await axios.post('/api/v1/admin/code/add', param, {
            headers: {
                'Content-Type': 'application/json; charset=utf-8'
            }
        });
    },
    updateCode: async param => {
        return await axios.put('/api/v1/admin/code/update', param);
    },
    deleteCode: async param => {
        return await axios.delete('/api/v1/admin/code/delete', param);
    }
}
