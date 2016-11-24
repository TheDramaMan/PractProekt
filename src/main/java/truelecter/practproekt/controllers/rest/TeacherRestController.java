package truelecter.practproekt.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import truelecter.practproekt.entity.Teacher;
import truelecter.practproekt.entity.dao.TeacherDao;

@RestController
@RequestMapping(path = "/api/teacher")
public class TeacherRestController {
    @Autowired
    private TeacherDao tDao;

    //Only for test
    @RequestMapping(path = "/create")
    public ResponseContainer createTeacher(@RequestParam String name) {
        try {
            Teacher t = new Teacher(name);
            tDao.save(t);
            return new ResponseContainer(t, true, null);
        } catch (Exception e) {
            return new ResponseContainer(null, false, "db_error:save_teacher");
        }
    }

    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseContainer createTeacherPost(@RequestParam String name) {
        try {
            Teacher t = new Teacher(name);
            tDao.save(t);
            return new ResponseContainer(t, true, null);
        } catch (Exception e) {
            return new ResponseContainer(null, false, "db_error:save_teacher");
        }
    }

    
    @RequestMapping(path = "")
    public ResponseContainer listTeachers() {
        try {
            return new ResponseContainer(tDao.findAll(), true, null);
        } catch (Exception e) {
            return new ResponseContainer(null, false, "db_error:get_teachers");
        }
    }

}
