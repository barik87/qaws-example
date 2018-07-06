package com.example.sut.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InitController {
    @RequestMapping("/")
    public String init() {
        return "<html><head><title>System Under Test</title></head><body><h2>Hello!</h2></body></html>";
    }
}
