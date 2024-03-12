package com.dangdang.boldpaws.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/login")
    public String index() {
        return "login";
    }
    @GetMapping("/")
    public String main() {
        return "main";
    }
    @GetMapping("/error")
    public String error() {
        return "error";
    }
}
