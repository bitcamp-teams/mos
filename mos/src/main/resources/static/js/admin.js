
function codeList() {
    debugger
    $.ajax({
        type: 'get',
        url: "/api/v1/admin/code/list",
        data: {
            paging: {
                pageNo: null
            }
        },
        dataType: "json",
        async: false,
        success: function (data) {
            debugger
            console.log(data)
            codeList = data
        },
        error: function (request, status, error) {
        }
    });
}
