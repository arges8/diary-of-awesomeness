package com.arges.diaryofawesomeness.data;

import com.arges.diaryofawesomeness.models.Note;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface NoteRepository extends PagingAndSortingRepository<Note, Long> {
}
