package study.studyspring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // 먼저 스프링 컨테이너에 Mapping 있는지 확인하고 없으면 static 정적 리소스 확인한다.
    @GetMapping("/")
    public String home(){
        return "home";
    }


}
