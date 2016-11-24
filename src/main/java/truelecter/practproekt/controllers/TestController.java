package truelecter.practproekt.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {
    @RequestMapping(path = "/admin")
    public void adminDummyMapping() {
    }
    @RequestMapping(path = "/user")
    public void userDummyMapping() {
    }
    @RequestMapping(path = "/method")
    public void mathodDummyMapping() {
    }
    
    @RequestMapping(path = "/info")
    public String homepage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        model.addAttribute("name", name);
        return "index";
    }
    
}
