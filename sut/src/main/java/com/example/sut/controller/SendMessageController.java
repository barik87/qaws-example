package com.example.sut.controller;

import io.restassured.http.ContentType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.sut.entities.Message;

import io.restassured.RestAssured;
import io.restassured.response.Response;

@RestController
@RequestMapping("/message")
public class SendMessageController {

    @Value("${messenger.url}")
    private String messengerUrl;

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public ResponseEntity<String> sendMessage(@RequestBody Message message) {
        RestAssured.baseURI = messengerUrl;
        Response response = RestAssured.with().contentType(ContentType.JSON).body(message).post("/msg/send");
        return ResponseEntity.status(response.statusCode()).body(response.body().print());
    }

}
