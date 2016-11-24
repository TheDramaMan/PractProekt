package truelecter.practproekt.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import truelecter.practproekt.State;
import truelecter.practproekt.entity.Discipline;
import truelecter.practproekt.entity.dao.DisciplineDao;
import truelecter.practproekt.entity.dao.TeacherDao;

@Controller
public class DisciplineViewController {

    @Autowired
    TeacherDao tDao;

    @Autowired
    DisciplineDao dDao;

    @RequestMapping(path = "/disciplines/edit/{id}")
    public String createDisciplinePage(@PathVariable int id,  Model model){
        Discipline d = dDao.findOne(id);
        if (d == null){
            return "404";
        }
        model.addAttribute("discipline", d);
        model.addAttribute("teachers", tDao.findAll());
        return "disciplineCreate";
    }

    @RequestMapping(path = "/disciplines")
    public String disciplinesMainPage(Model model) {
        State.getCurrentState().apply(model);
        return "disciplines";
    }
    
    @RequestMapping("/")
    public String indexRedirect(){
        return "redirect:/disciplines";
    }
}
