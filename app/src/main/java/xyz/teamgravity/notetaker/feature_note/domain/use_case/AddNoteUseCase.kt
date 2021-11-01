package xyz.teamgravity.notetaker.feature_note.domain.use_case

import xyz.teamgravity.notetaker.feature_note.data.repository.NoteRepository
import xyz.teamgravity.notetaker.feature_note.domain.model.InvalidNoteException
import xyz.teamgravity.notetaker.feature_note.domain.model.NoteModel

class AddNoteUseCase(private val repository: NoteRepository) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: NoteModel) {
        if (note.title.isBlank()) {
            throw InvalidNoteException("The title of note can't be empty!")
        }

        if (note.content.isBlank()) {
            throw InvalidNoteException("The content of note can't be empty!")
        }

        repository.insert(note)
    }
}