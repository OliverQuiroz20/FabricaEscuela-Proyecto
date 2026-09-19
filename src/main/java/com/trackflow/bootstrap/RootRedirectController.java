package com.trackflow.bootstrap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootRedirectController {

    @GetMapping("/")
    public String raiz() {
        return "redirect:/swagger-ui.html";
    }
}
