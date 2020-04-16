package com.arges.diaryofawesomeness.web;

import com.arges.diaryofawesomeness.data.NoteRepository;
import com.arges.diaryofawesomeness.model.Note;
import com.fasterxml.jackson.databind.node.TextNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@Validated
@RequestMapping(path = "/note", produces = "application/json")
public class PositiveEventController {

    @Autowired
    private NoteRepository noteRepo;

    @PostMapping("/{noteId}/event")
    @ResponseStatus(HttpStatus.OK)
    public Note addPositiveEventsToNote(@PathVariable("noteId") Long noteId, @RequestBody List<String> events) {
        Note note = noteRepo.findById(noteId).get();
        note.getPositiveEvents().addAll(events);

        return noteRepo.save(note);
    }

    @DeleteMapping("/{noteId}/event")
    @ResponseStatus(HttpStatus.OK)
    public Note removePositiveEvent(@PathVariable("noteId") Long noteId,
                                                      @RequestBody TextNode eventToDelete) {
        Note note = noteRepo.findById(noteId).get();
        boolean eventDeleted = note.getPositiveEvents().remove(eventToDelete.asText());

        if(eventDeleted)
            return noteRepo.save(note);

        throw new NoSuchElementException("Cannot find event: '" + eventToDelete.asText() + "'");
    }

}
