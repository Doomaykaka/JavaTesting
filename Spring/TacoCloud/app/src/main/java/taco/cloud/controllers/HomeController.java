package taco.cloud.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class HomeController {
    private final String HOME_JSON_SUB_KEY_NAME = "dat";
    private final String HOME_JSON_NUMBER_KEY_NAME = "number";
    private final String HOME_JSON_TEXT_KEY_NAME = "text";
    private final String HOME_JSON_SUB_STRUCT_KEY_NAME = "sub";
    private final String HELLO_VIEW_NAME = "hello-view.html";

    @GetMapping(value = "/home", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> home() {
        Map<String, Object> result = new HashMap<String, Object>();

        Map<String, Object> sub = new HashMap<String, Object>();
        sub.put(HOME_JSON_SUB_KEY_NAME, 1);

        result.put(HOME_JSON_NUMBER_KEY_NAME, 1);
        result.put(HOME_JSON_TEXT_KEY_NAME, "test");
        result.put(HOME_JSON_SUB_STRUCT_KEY_NAME, sub);

        return result;
    }

    @GetMapping(value = "/hello")
    public String hello() {
        return HELLO_VIEW_NAME;
    }
}
