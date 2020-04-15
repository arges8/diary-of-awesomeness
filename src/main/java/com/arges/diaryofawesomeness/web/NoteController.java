package com.arges.diaryofawesomeness.web;

import com.arges.diaryofawesomeness.data.NoteRepository;
import com.arges.diaryofawesomeness.model.Note;
import com.arges.diaryofawesomeness.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/note", produces = "application/json")
public class NoteController {

    @Autowired
    private NoteRepository noteRepo;

    @GetMapping("/{noteId}")
    public Note getNote(@PathVariable("noteId") Long noteId) {
        return noteRepo.findById(noteId).get();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Note postNote(@Valid @RequestBody Note note, @AuthenticationPrincipal User user) {
        note.setUser(user);
        return noteRepo.save(note);
    }

    @DeleteMapping("/{noteId}")
    public ResponseEntity<Object> deleteNote(@PathVariable("noteId") Long noteId) {
        noteRepo.deleteById(noteId);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", HttpStatus.OK.value());
        body.put("noteId", noteId);
        body.put("message", "Note with id=" + noteId + " deleted");

        return new ResponseEntity<>(body, HttpStatus.OK);
    }
}
