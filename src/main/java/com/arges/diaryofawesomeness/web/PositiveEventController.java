package com.arges.diaryofawesomeness.web;

import com.arges.diaryofawesomeness.data.NoteRepository;
import com.arges.diaryofawesomeness.model.Note;
import com.fasterxml.jackson.databind.node.TextNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<Object> removePositiveEvent(@PathVariable("noteId") Long noteId,
                                                      @RequestBody TextNode eventToDelete) {
        Note note = noteRepo.findById(noteId).get();
        boolean eventDeleted = note.getPositiveEvents().remove(eventToDelete.asText());

        if(eventDeleted) {
            Note noteSaved = noteRepo.save(note);
            return new ResponseEntity<>(noteSaved, HttpStatus.OK);
        }

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("eventToDelete", eventToDelete);
        body.put("message", "Event Not Found");
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

}
