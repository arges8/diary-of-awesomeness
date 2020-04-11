package com.arges.diaryofawesomeness.web;

import com.arges.diaryofawesomeness.data.NoteRepository;
import com.arges.diaryofawesomeness.models.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/note", produces = "application/json")
public class NoteController {

    @Autowired
    private NoteRepository noteRepo;

    @GetMapping
    public String hello() {
        return "Hey beautiful ;)";
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Note postNote(@Valid @RequestBody Note note) {
        return noteRepo.save(note);
    }
}
