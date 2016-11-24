var working = false;

function recommend(id, cb) {
    action(id, "recommend", cb);
}

function unrecommend(id, cb) {
    action(id, "unrecommend", cb);
}

function action(id, action, cb) {
    $.ajax({
        url: "/api/discipline/" + id + "/" + action,
        method: "post"
    }).then(function (data) {
        console.log(data);
        if (data.success) {
            cb(null, data.object);
        } else {
            cb(data.errorMessage, data.object);
        }
    });
}

function fit(btn){
    btn.fitText(1.3);
    btn.height($(".additional_info").height());
}

function getRecommendHandler(id, btn_recommend, cb) {
    return function () {
        if (!cb) {
            cb = function () {
            };
        }
        if (working) {
            return;
        }
        if (btn_recommend.hasClass("btn-danger")) {
            //unroll
            working = true;
            btn_recommend.addClass("disabled");
            unrecommend(id, function (err, student) {
                btn_recommend.removeClass("btn-danger");
                fit(btn_recommend);
                btn_recommend.addClass("btn-info");
                btn_recommend.removeClass("disabled");
                working = false;
                cb(false);
            });
        } else {
            //enroll
            working = true;
            btn_recommend.addClass("disabled");
            recommend(id, function (err, student) {
                if (err) {
                    $.notify({
                        title: '<strong>Помилка рекомендації дисципліни!</strong></br>',
                        message: ''
                    }, {
                        type: 'danger'
                    });
                    btn_recommend.removeClass("disabled");
                    working = false;
                    return;
                }
                btn_recommend.addClass("btn-danger");
                btn_recommend.html("Вилучити з рекомендованих");
                fit(btn_recommend);
                btn_recommend.removeClass("btn-info");
                btn_recommend.removeClass("disabled");
                working = false;
                cb(true);
            });
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

    var btn_recommend = item.find(".recommend");

    var handler = getRecommendHandler(id, btn_recommend);

    btn_recommend.click(handler);

    var btn_additional_info = item.find(".additional_info");

    btn_additional_info.click(function () {
        setAdditionalInfo(discipline);
    });


    if (recommended) {
        btn_recommend.addClass("btn-danger");
        btn_recommend.html("Вилучити з рекомендованих");
        fit(btn_recommend);
        btn_recommend.removeClass("btn-info");
    } else {
        btn_recommend.removeClass("btn-danger");
        btn_recommend.html("Рекмендувати");
        fit(btn_recommend);
        btn_recommend.addClass("btn-info");
    }

    disciplines.id = item;

    return item;
}