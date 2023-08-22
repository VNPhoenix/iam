package vn.dangdnh.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${app.base-url}")
public class HomeController {

    @GetMapping(value = "/home", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("Welcome!");
    }
}
