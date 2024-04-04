
function codeList() {
    $.ajax({
        type: 'post',
        url: "/api/v1/admin/code/list",
        data: {
            identifyNumber: identifyNumber,
            identifyCode: identifyCode,
            studentNumber: studentNumber
        },
        dataType: "json",
        async: false,
        success: function (data) {
            // $("#contC ul.tabs").find(".tab-link").remove();
            // $("#contC ul.tabs").prepend(data.tabs);
        },
        error: function (request, status, error) {
        }
    });
}
