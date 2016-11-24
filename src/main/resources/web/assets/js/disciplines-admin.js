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

    var btn_edit = item.find(".edit");
    btn_edit.attr("href","/disciplines/edit/" + discipline.id);

    var btn_additional_info = item.find(".additional_info");

    btn_additional_info.click(function () {
        setAdditionalInfo(discipline);
    });
    
    disciplines.id = item;

    return item;
}