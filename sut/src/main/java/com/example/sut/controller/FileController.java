package com.example.sut.controller;

import com.example.sut.entities.SaveFileParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/file")
public class FileController {

    private static final String FILE_NAME = "sut-file.txt";

    @Autowired
    private ServletContext servletContext;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<String> saveFile(@RequestBody SaveFileParameters parameters) {
        Path path = Paths.get(servletContext.getAttribute("javax.servlet.context.tempdir") + "/" + FILE_NAME);
        String text = "Text: " + parameters.getText();
        try {
            Files.write(path, text.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File not saved!");
        }
        return ResponseEntity.ok("Saved!");
    }

    @RequestMapping(value = "/read", method = RequestMethod.GET)
    public ResponseEntity<String> readFile() {
        Path path = Paths.get(servletContext.getAttribute("javax.servlet.context.tempdir") + "/" + FILE_NAME);
        try {
            return ResponseEntity.ok(StringUtils.collectionToDelimitedString(Files.readAllLines(path), "\n"));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during reading file!");
        }
    }
}
