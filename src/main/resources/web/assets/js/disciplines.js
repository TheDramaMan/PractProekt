if (!newDiscipline){
    newDiscipline = function(discipline) {
        var item = $($("#template").html());

        var id = discipline.id;
        var course_name = discipline.name;
        var teacher_name = discipline.teacher.name;
        var credits = discipline.credits;
        var recommended = discipline.recommended;
        
        if (!teacher_name) {
            teacher_name = "Default";
        }

        var btn_additional_info = item.find(".additional_info");
        
        btn_additional_info.click(function(){
        	setAdditionalInfo(discipline);
        });
        
        item.find(".teacher_name").html(teacher_name);

        item.find(".credits").html(credits);

        item.find(".title").html(course_name);

        if (!recommended)
            item.find(".recommended").addClass("hidden");

        return item;
    }
}

function putNewItem(item) {
    if (item) {
        $("#discipline_container").append(item);
    }
    $("#spinner").hide();
}

function loadDisciplines(){
    $.ajax({
        url: "/api/discipline/"
    }).then(function(data) {
        if (data.success) {
            var disciplines = data.object;
            if (disciplines.length < 1){
            	$("#spinner").hide();
            	$("#empty_disc").show();
            	return;
            }
            for (var i = 0; i < disciplines.length; i++) {
                var discipline = disciplines[i];
                putNewItem(newDiscipline(discipline));
            }
        } else {
            //TODO add error handling        
        }
    });
}

function setAdditionalInfo(discipline){
	$("#teacher_modal").html(discipline.teacher.name);
	$("#credits_modal").html(discipline.credits);
	$("#annotation_modal").html(discipline.annotation);
	$("#discipline_modal").html(discipline.name);
	$("#student_list_modal").html("");
	
	var students = discipline.students;
	for (var i = 0; i < students.length; i++){
		var student = students[i];
		$("#student_list_modal").append($("<a href=\"/students/\"" + student.id + ">" + student.name + "</a>&nbsp;&nbsp;&nbsp;"));
	}	
	
	$("#student_count_modal").html("(" + students.length + ")");
}

$(document).ready(function() {
	if (!loadEnrolled){
		loadDisciplines();
	} else {
		loadEnrolled(loadDisciplines);
	}
});