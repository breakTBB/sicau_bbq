function chk() {
    alert('审核成功，当前对所有人可见');
    $("#check").fadeOut();
}

function up() {
    var id = $('#tid').val();
    var formData = new FormData();
    formData.append("id_topic", id);

    $.ajax({
        "url": "http://localhost:8080/topic/upthumb",
        "type": "POST",
        "data": formData,
        contentType: false,
        processData: false,
        "success": function () {
            M.toast({html: '点赞成功'});
        },
        error: function () {
            M.toast({html: '操作失败'});
        }
    });
}