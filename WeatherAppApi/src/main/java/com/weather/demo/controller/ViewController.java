package com.weather.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;  // Use Model to pass attributes to Thymeleaf view
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    // Route for streaming the YouTube video
    @GetMapping("/tony")
    public String streamTony() {
        return "tony";  
    }
}