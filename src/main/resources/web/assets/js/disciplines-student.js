var enrolledIds = [];
var student = false;
var username = $("#username").html();

loadEnrolled = function (cb) {
    $.ajax({
        url: "/api/student/" + username,
        method: "GET"
    }).then(function (data) {
        if (data.success) {
            var studentI = data.object;
            student = studentI;
            var disciplines = studentI.disciplines;
            for (var i = 0; i < disciplines.length; i++) {
                enrolledIds.push(disciplines[i].id);
            }
            $("#total_credits").html(student.totalCredits);
            if (cb)
                cb(null, student, enrolledIds);
        } else {
            //TODO add error handling        
        }
    });
}

function enroll(id, cb) {
    enroll_unroll(id, "enroll", cb);
}

function unroll(id, cb) {
    enroll_unroll(id, "unroll", cb);
}

function enroll_unroll(id, action, cb) {
    $.ajax({
        url: "/api/student/" + username + "/" + action,
        data: {
            disciplineId: id
        }
    }).then(function (data) {
        console.log(data);
        if (data.success) {
            cb(null, data.object);
        } else {
            cb(data.errorMessage, data.object);
        }
    });
}

function enrolled(id) {
    return enrolledIds.indexOf(id) != -1;
}

var working = false;

function getEnrollUnrollHandler(id, btn_enroll_unroll, cb) {
    return function () {
        if (!cb) {
            cb = function () {
            };
        }
        if (working) {
            return;
        }
        if (btn_enroll_unroll.hasClass("btn-danger")) {
            //unroll
            working = true;
            btn_enroll_unroll.addClass("disabled");
            unroll(id, function (err, student) {

                btn_enroll_unroll.removeClass("btn-danger");
                btn_enroll_unroll.html("Записатись");
                btn_enroll_unroll.addClass("btn-info");
                btn_enroll_unroll.removeClass("disabled");
                working = false;
                loadEnrolled();
                cb(false);
            });
        } else {
            //enroll
            working = true;
            btn_enroll_unroll.addClass("disabled");
            enroll(id, function (err, student) {
                if (err) {
                    $.notify({
                        title: '<strong>Помилка запису на дисципліну!</strong></br>',
                        message: 'У Вас недостатньо кредитів.'
                    }, {
                        type: 'danger'
                    });
                    btn_enroll_unroll.removeClass("disabled");
                    working = false;
                    return;
                }
                btn_enroll_unroll.addClass("btn-danger");
                btn_enroll_unroll.html("Виписатись");
                btn_enroll_unroll.removeClass("btn-info");
                btn_enroll_unroll.removeClass("disabled");
                working = false;
                loadEnrolled();
                cb(true);
            });
        }
    };
}

function getModalAdditionalCallback(btn_enroll_unroll) {
    return function (enrolled) {
        if (enrolled) {
            btn_enroll_unroll.addClass("btn-danger");
            btn_enroll_unroll.html("Виписатись");
            btn_enroll_unroll.removeClass("btn-info");
        } else {
            btn_enroll_unroll.removeClass("btn-danger");
            btn_enroll_unroll.html("Записатись");
            btn_enroll_unroll.addClass("btn-info");
        }
    };
}

newDiscipline = function (discipline) {
    var item = $($("#template").html());

    var id = discipline.id;
    var course_name = discipline.name;
    var teacher_name = discipline.teacher.name;
    var credits = discipline.credits;
    var recommended = discipline.recommended;


    if (!teacher_name) {
        teacher_name = "Немає";
    }

    item.find(".teacher_name").html(teacher_name);

    item.find(".credits").html(credits);

    item.find(".title").html(course_name);

    if (!recommended)
        item.find(".recommended").addClass("hidden");

    var btn_enroll_unroll = item.find(".enroll_unroll");

    var enroll_unroll_handler = getEnrollUnrollHandler(id, btn_enroll_unroll);

    btn_enroll_unroll.click(enroll_unroll_handler);

    var btn_additional_info = item.find(".additional_info");

    btn_additional_info.click(function () {
        setAdditionalInfo(discipline);
        $("#enroll_modal").unbind("click");
        $("#enroll_modal").click(getEnrollUnrollHandler(id, $("#enroll_modal"), getModalAdditionalCallback(btn_enroll_unroll)));
        if (enrolled(id)) {
            $("#enroll_modal").addClass("btn-danger");
            $("#enroll_modal").html("Виписатись");
            $("#enroll_modal").removeClass("btn-info");
        } else {
            $("#enroll_modal").removeClass("btn-danger");
            $("#enroll_modal").html("Записатись");
            $("#enroll_modal").addClass("btn-info");
        }
    });


    if (enrolled(id)) {
        btn_enroll_unroll.addClass("btn-danger");
        btn_enroll_unroll.html("Виписатись");
        btn_enroll_unroll.removeClass("btn-info");
    } else {
        btn_enroll_unroll.removeClass("btn-danger");
        btn_enroll_unroll.html("Записатись");
        btn_enroll_unroll.addClass("btn-info");
    }

    disciplines.id = item;

    return item;
}