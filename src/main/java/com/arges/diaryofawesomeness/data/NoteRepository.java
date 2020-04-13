package com.arges.diaryofawesomeness.data;

import com.arges.diaryofawesomeness.model.Note;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface NoteRepository extends PagingAndSortingRepository<Note, Long> {
}
