package com.arges.diaryofawesomeness.security;

import com.arges.diaryofawesomeness.data.NoteRepository;
import com.arges.diaryofawesomeness.model.Note;
import com.arges.diaryofawesomeness.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("userSecurity")
public class UserSecurity {

    private NoteRepository noteRepo;

    public UserSecurity(NoteRepository noteRepo) {
        this.noteRepo = noteRepo;
    }

    public boolean hasNoteId(Authentication authentication, Long noteId) {
        Optional<Note> optionalNote = noteRepo.findById(noteId);
        if(optionalNote.isPresent()) {
            Note note = optionalNote.get();
            User user = (User) authentication.getPrincipal();

            if(note.getUser().equals(user))
                return true;
        }

        return false;
    }
}
