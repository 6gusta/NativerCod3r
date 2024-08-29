package com.example.demo.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class Homepage {




    @GetMapping("/message")
    public String getMessage() {
        return "Hello, World!";
    }
    
}
