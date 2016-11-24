function postChanges() {
    $.ajax({
        url: "/api/discipline/" + id,
        method: "POST",
        contentType: 'application/json',
        data: JSON.stringify({
            name: $("#name").val(),
            credits: $("#credits").val(),
            teacher_id: $("#teacher").val()
        })
    }).then(function (data) {
        if (data.success) {
            $.notify({
                message: 'Зміни збережено'
            }, {
                type: 'success'
            });
            console.log(data);
        } else {
            $.notify({
                message: 'Помилка збереження змін'
            }, {
                type: 'danger'
            });
        }
    });
}

$(document).ready(function () {
    $("#save").click(postChanges);
});