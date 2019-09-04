package com.sicau.forum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @RequestMapping(path = "/")
    public String index() {
        return "index";
    }
}
