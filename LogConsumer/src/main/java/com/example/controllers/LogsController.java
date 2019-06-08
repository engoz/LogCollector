package com.example.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class LogsController {

    @RequestMapping("/")
    public String logs(){
        return "/logs";
    }

}
