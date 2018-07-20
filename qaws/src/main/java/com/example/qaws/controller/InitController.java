package com.example.qaws.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InitController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<String> init() {
        return ResponseEntity.ok("QA Web Services are here");
    }
}
