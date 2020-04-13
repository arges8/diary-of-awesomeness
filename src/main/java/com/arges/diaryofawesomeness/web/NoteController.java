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
import java.util.HashMap;
import java.util.Map;

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
    public Note postNote(@Valid @RequestBody Note note, @AuthenticationPrincipal User user) {
        note.setUser(user);
        return noteRepo.save(note);
    }

    @DeleteMapping("/{noteId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> deleteNote(@PathVariable("noteId") Long noteId, @AuthenticationPrincipal User user) {
        Note note = noteRepo.findById(noteId).get();
        Map<String, Object> body = new HashMap<>();
        body.put("noteId", noteId);
        if(note.getUser().equals(user)) {
            body.put("result", "Note deleted");
            noteRepo.deleteById(noteId);

            return new ResponseEntity<>(body, HttpStatus.OK);
        }
        body.put("result", "Note was not deleted");
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
