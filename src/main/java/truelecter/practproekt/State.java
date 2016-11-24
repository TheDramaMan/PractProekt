package truelecter.practproekt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.ui.Model;

public enum State {
    PRE, ONGOING, POST;

    @Getter
    @Setter
    private static State currentState = ONGOING;

    public void apply(Model mdl) {
//        if (this == PRE){
//            mdl.addAttribute("canEnroll", false);
//            mdl.addAttribute("canEdit", true);
//        }
//        if (this == ONGOING){
//            mdl.addAttribute("canEnroll", true);
//            mdl.addAttribute("canEdit", false);
//        }
//        if (this == POST){
//            mdl.addAttribute("canEnroll", false);
//            mdl.addAttribute("canEdit", false);
//        }
        mdl.addAttribute("canEnroll", true);
        mdl.addAttribute("canEdit", true);
    }
}
