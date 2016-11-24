package truelecter.practproekt.controllers.rest;

import lombok.Data;
import lombok.ToString;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import truelecter.practproekt.entity.Discipline;
import truelecter.practproekt.entity.dao.DisciplineDao;
import truelecter.practproekt.entity.dao.TeacherDao;

@RestController
@RequestMapping(path = "/api/discipline")
public class DisciplineRestController {

    @Autowired
    private DisciplineDao dDao;

    @Autowired
    private TeacherDao tDao;

    private Logger logger = Logger.getLogger(getClass());

    @RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseContainer listDisciplines() {
        try {
            return new ResponseContainer(dDao.findAll(), true, null);
        } catch (Exception e) {
            return new ResponseContainer(null, false, "db_error:find_disciplines");
        }
    }

    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseContainer newDiscipline(@RequestParam String name, @RequestParam int credits, @RequestParam int teacher_id) {
        try {
            Discipline d = new Discipline(name, credits);
            d.setTeacher(tDao.findOne(teacher_id));
            dDao.save(d);
            return new ResponseContainer(d, true, null);
        } catch (Exception e) {
            return new ResponseContainer(null, false, "db_error:save_discipline");
        }
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseContainer getDiscipline(@PathVariable Integer id) {
        try {
            Discipline d = dDao.findOne(id);
            if (d == null) {
                throw new Exception("No such id present");
            }
            return new ResponseContainer(d, true, null);
        } catch (Exception e) {
            return new ResponseContainer(null, false, "wrong_id");
        }
    }

    @RequestMapping(path = "/{id}/recommend", method = RequestMethod.POST)
    public ResponseContainer recommend(@PathVariable Integer id) {
        try {
            Discipline d = dDao.findOne(id);
            if (d == null) {
                throw new Exception("No such id present");
            }
            d.setRecommended(true);
            dDao.save(d);
            return new ResponseContainer(d, true, null);
        } catch (Exception e) {
            return new ResponseContainer(null, false, "wrong_id");
        }
    }

    @RequestMapping(path = "/{id}/unrecommend", method = RequestMethod.POST)
    public ResponseContainer unrecommend(@PathVariable Integer id) {
        try {
            Discipline d = dDao.findOne(id);
            if (d == null) {
                throw new Exception("No such id present");
            }
            d.setRecommended(false);
            dDao.save(d);
            return new ResponseContainer(d, true, null);
        } catch (Exception e) {
            return new ResponseContainer(null, false, "wrong_id");
        }
    }

    @Data
    private static class DisciplineRepresentation {
        public String name;
        public int credits;
        public int teacher_id;
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseContainer changeDiscipline(@PathVariable Integer id, @RequestBody DisciplineRepresentation discipline) {
        try {
            Discipline d = dDao.findOne(id);
            if (d == null) {
                throw new Exception("No such id present");
            }
            d.setName(discipline.name);
            d.setTeacher(tDao.findOne(discipline.teacher_id));
            d.setCredits(discipline.credits);
            dDao.save(d);
            return new ResponseContainer(d, true, null);
        } catch (Exception e) {
            logger.warn("Exception in editing discipline", e);
            logger.warn("RequestBody: \n"+discipline.toString());
            return new ResponseContainer(null, false, "wrong_id");
        }
    }
}
