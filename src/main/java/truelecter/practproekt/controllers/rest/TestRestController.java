package truelecter.practproekt.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import truelecter.practproekt.entity.Student;
import truelecter.practproekt.entity.dao.StudentDao;

@RestController
@RequestMapping(path = "/api")
public class TestRestController {

    @Autowired
    StudentDao sDao;

    @RequestMapping(path = "/student/create", method = RequestMethod.GET)
    public ResponseContainer createStudentDbEntry(@RequestParam String name) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.isAuthenticated()) {
            return new ResponseContainer(null, false, "no_auth");
        }
        Student s;
        try {
            s = sDao.findOne(auth.getName());
        } catch (Exception e) {
            return new ResponseContainer(null, false, "db_error:find_student");
        }
        if (s == null) {
            s = new Student(auth.getName(), name);
        } else {
            return new ResponseContainer(s, true, null);
        }
        try {
            sDao.save(s);
            return new ResponseContainer(s, true, null);
        } catch (Exception e) {
            return new ResponseContainer(null, false, "db_error:save_student");
        }
    }
}
