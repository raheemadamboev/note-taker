package xyz.teamgravity.notetaker.feature_note.domain.use_case

import xyz.teamgravity.notetaker.feature_note.data.repository.NoteRepository
import xyz.teamgravity.notetaker.feature_note.domain.model.NoteModel

class DeleteNoteUseCase(private val repository: NoteRepository) {

    suspend operator fun invoke(note: NoteModel) = repository.delete(note)
}