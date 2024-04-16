import codes from "./api/admin/code/codes.js";

const index = {
    init() {
        const _this = this;

        _this.list();

    },
    list() {
        const param = {  // 페이지 샘플
            paging: {
                pageNo: 1
            }
        }
        codes.listCode(param).then(res => {
            const data = res.data.resultData;
            $("#example1").DataTable({
                responsive: true,
                lengthChange: false,
                autoWidth: false,
                buttons: ["copy", "csv", "excel", "pdf", "print", "colvis"],
                data: data,
                columns: [
                    {data: 'codeGroup.codeGroup'},
                    {data: 'codeGroup.codeGroupName'},
                    {data: 'codeGroup.moduleCode'},
                    {data: 'code'},
                    {data: 'codeName'},
                    {data: 'createDate'},
                    {data: 'updateDate'},
                ]
            }).buttons().container().appendTo('#example1_wrapper .col-md-6:eq(0)');
        })
    },
    save: function () {
        const param = {

        };

        codes.createCode(param).then(resizeBy => {

        })

    },
    update: function () {
        const param = {

        };

        codes.updateCode(param).then(res => {

        })

    },
    delete: function () {
        const param = {

        };

        codes.deleteCode(param).then(res => {

        })
    }
};
index.init();
