package truelecter.practproekt.controllers.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseContainer {
    Object object;
    boolean success;
    String errorMessage;
}
