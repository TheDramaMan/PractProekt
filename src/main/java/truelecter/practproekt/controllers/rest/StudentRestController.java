package truelecter.practproekt.controllers.rest;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import truelecter.practproekt.entity.Discipline;
import truelecter.practproekt.entity.Student;
import truelecter.practproekt.entity.dao.DisciplineDao;
import truelecter.practproekt.entity.dao.StudentDao;

@RestController
@RequestMapping(path = "/api/student")
public class StudentRestController {
    @Autowired
    private DisciplineDao dDao;

    @Autowired
    private StudentDao sDao;

    @RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseContainer listStudents() {
        try {
            return new ResponseContainer(dDao.findAll(), true, null);
        } catch (Exception e) {
            return new ResponseContainer(null, false, "db_error:find_students");
        }
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseContainer getStudent(@PathVariable String id) {
        try {
            Student s = sDao.findOne(id);
            if (s == null) {
                throw new Exception("No such id present");
            }
            return new ResponseContainer(s, true, null);
        } catch (Exception e) {
            return new ResponseContainer(null, false, "wrong_id");
        }
    }

    @RequestMapping(path = "/{id}/enrolledId", method = RequestMethod.GET)
    public ResponseContainer getStudentDisciplinesId(@PathVariable String id) {
        try {
            Student s = sDao.findOne(id);
            if (s == null) {
                return new ResponseContainer(null, false, "wrong_id");
            }

            Set<Discipline> disciplines = s.getDisciplines();
            int[] re = new int[disciplines.size()];
            int i = 0;
            for (Discipline d : disciplines) {
                re[i++] = d.getId();
            }

            return new ResponseContainer(re, true, null);
        } catch (Exception e) {
            return new ResponseContainer(null, false, "db_error:find_student");
        }
    }

    // TODO make this post method (or put?)
    @RequestMapping(path = "/{id}/enroll", method = RequestMethod.GET)
    public ResponseContainer enrollStudent(@PathVariable String id, @RequestParam Integer disciplineId) {
        Student s;
        try {
            s = sDao.findOne(id);
            if (s == null) {
                throw new Exception("No such id present");
            }
        } catch (Exception e) {
            return new ResponseContainer(null, false, "wrong_id");
        }
        Discipline d;
        try {
            d = dDao.findOne(disciplineId);
            if (d == null) {
                throw new Exception("No such id present");
            }
        } catch (Exception e) {
            return new ResponseContainer(null, false, "wrong_id");
        }
        if (s.getTotalCredits() + d.getCredits() > 30){
            return new ResponseContainer(s, false, "not_enough_credits");
        }
        Set<Discipline> di = s.getDisciplines();
        di.add(d);
        s.setDisciplines(di);
        try {
            dDao.save(d);
            return new ResponseContainer(s, true, null);
        } catch (Exception e) {
            return new ResponseContainer(null, false, "db_error:save");
        }
    }

    // TODO make this delete method
    @RequestMapping(path = "/{id}/unroll", method = RequestMethod.GET)
    public ResponseContainer unrollStudent(@PathVariable String id, @RequestParam Integer disciplineId) {
        Student s;
        try {
            s = sDao.findOne(id);
            if (s == null) {
                throw new Exception("No such id present");
            }
        } catch (Exception e) {
            return new ResponseContainer(null, false, "wrong_id");
        }
        Discipline d;
        try {
            d = dDao.findOne(disciplineId);
            if (d == null) {
                throw new Exception("No such id present");
            }
        } catch (Exception e) {
            return new ResponseContainer(null, false, "wrong_id");
        }
        Set<Discipline> di = s.getDisciplines();
        di.remove(d);
        s.setDisciplines(di);
        try {
            dDao.save(d);
            return new ResponseContainer(s, true, null);
        } catch (Exception e) {
            return new ResponseContainer(null, false, "db_error:save");
        }
    }
}
