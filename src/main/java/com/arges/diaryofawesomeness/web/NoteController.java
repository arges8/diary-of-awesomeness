package com.arges.diaryofawesomeness.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/note")
public class NoteController {

    @GetMapping
    public String hello() {
        return "Hey beautiful ;)";
    }
}
