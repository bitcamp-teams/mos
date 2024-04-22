import codes from "./api/admin/code/codes.js";
import {convertToObject} from "./util/util.js";

const index = {
    init() {
        const _this = this;

        _this.list();


        $(document).ready(function () {
            $(document).on("click", "#modalOpenBtn", function () {
                _this.modalOpen();
            })
        })

        $('#addFrm').on("submit", function (e) {
            e.preventDefault();
            _this.save(this);
        })
    },
    list() {
        const param = {
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
            $('#example1_filter').append('<button id="modalOpenBtn" class="btn btn-outline-info btn-sm" data-toggle="modal" data-target="#modal-lg">추가</button>')
        })
    },
    save(data) {
        const form = new FormData(data)
        let tmp = [];
        for (const data of form.entries()) {
            tmp.push(data)
        }

        const result = JSON.stringify(convertToObject(tmp));
        const Toast = Swal.mixin({
            toast: true,
            position: 'top-end',
            showConfirmButton: false,
            timer: 3000
        });

        codes.createCode(result).then(res => {
            console.log(res.data)
            let errorMessage = res.data.errorMessage;
            if (res.data.errorCode === '-32') {
                Toast.fire({
                    icon: 'error',
                    title: '코드 저장 실패!',
                    text: `${errorMessage}`
                })
            } else if (res.data.errorCode === '-99') {
                Toast.fire({
                    icon: 'error',
                    title: '코드 저장 실패!',
                    text: `${errorMessage}`
                })
            } else {
                Toast.fire({
                    icon: 'success',
                    title: '코드 저장 성공!',
                    text: ``
                })
            }
            $('.close').click();
            $('.modal-backdrop').remove();
        }).catch(res => {
            Toast.fire({
                icon: 'error',
                title: '코드 저장 실패!',
                text: res.data.resultData
            })
        }).finally(() => {
            $('#addFrm')[0].reset()
        })
    },
    modalOpen() {
        const param = {
            paging: {
                pageNo: 1
            }
        }
        codes.listCodeGroup(param).then(res => {
            const arr = res.data.resultData;
            const data = $.map(arr, function (obj) {
                obj.id = obj.codeGroup;
                obj.text = obj.codeGroupName;
                return obj;
            });
            $('.js-example-data-array').select2({
                theme: 'bootstrap4',
                dropdownParent: $("#modal-lg"),
                data: data
            })
        })
    },
    update() {
        const param = {};

        codes.updateCode(param).then(res => {

        })

    },
    delete() {
        const param = {};

        codes.deleteCode(param).then(res => {

        })
    }
};

index.init();
