package com.arges.diaryofawesomeness.web;

import com.arges.diaryofawesomeness.data.NoteRepository;
import com.arges.diaryofawesomeness.model.Note;
import com.arges.diaryofawesomeness.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping(path = "/note", produces = "application/json")
public class PositiveEventController {

    @Autowired
    private NoteRepository noteRepo;

    @PostMapping("/{noteId}")
    public ResponseEntity<Note> addPositiveEventsToNote(@PathVariable("noteId") Long noteId,
                                                  @RequestBody List<String> events,
                                                  @AuthenticationPrincipal User user) {
        Note note = noteRepo.findById(noteId).get();
        if(note.getUser().equals(user)) {
            note.getPositiveEvents().addAll(events);
            Note savedNote = noteRepo.save(note);
            return new ResponseEntity<>(savedNote, HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
}
