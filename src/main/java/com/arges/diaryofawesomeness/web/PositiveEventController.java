package com.arges.diaryofawesomeness.web;

import com.arges.diaryofawesomeness.data.NoteRepository;
import com.arges.diaryofawesomeness.model.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.OK)
    public Note addPositiveEventsToNote(@PathVariable("noteId") Long noteId, @RequestBody List<String> events) {
        Note note = noteRepo.findById(noteId).get();
        note.getPositiveEvents().addAll(events);

        return noteRepo.save(note);
    }
}
