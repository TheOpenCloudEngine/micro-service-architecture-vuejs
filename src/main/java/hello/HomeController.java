package hello;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by uengine on 2017. 5. 28..
 */

//@Controller
public class HomeController {

    @GetMapping("/**")
    public String home(Model model) {
        model.addAttribute("message", "Hello, Spring Boot!");
        return "index.html";
    }
}