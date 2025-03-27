package example.com.gameserver.user.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping("/error")
    public String handleError() {
        return "error"; // → templates/error.html 렌더링
    }
}