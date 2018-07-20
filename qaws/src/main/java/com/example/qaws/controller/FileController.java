package com.example.qaws.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.qaws.entities.ReadFileParameters;
import com.example.qaws.entities.WriteFileParameters;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private ServletContext servletContext;

    @RequestMapping(value = "/read", method = RequestMethod.POST)
    public ResponseEntity<String> readFile(@RequestBody ReadFileParameters parameters) {
        Path path = Paths.get(servletContext.getAttribute("javax.servlet.context.tempdir") + parameters.getPath());
        try {
            return ResponseEntity.ok(StringUtils.collectionToDelimitedString(Files.readAllLines(path), "\n"));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during reading file!");
        }
    }

    @RequestMapping(value = "/write", method = RequestMethod.POST)
    public ResponseEntity<String> writeFile(@RequestBody WriteFileParameters parameters) {
        Path path = Paths.get(servletContext.getAttribute("javax.servlet.context.tempdir") + parameters.getPath());
        try {
            Files.write(path, parameters.getText().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File not saved!");
        }
        return ResponseEntity.ok("File saved!");
    }
}
