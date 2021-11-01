package xyz.teamgravity.notetaker.feature_note.presentation.notes

import xyz.teamgravity.notetaker.feature_note.domain.model.NoteModel
import xyz.teamgravity.notetaker.feature_note.domain.util.NoteOrder

sealed class NotesEvent {
    data class Order(val noteOrder: NoteOrder) : NotesEvent()
    data class DeleteNote(val note: NoteModel) : NotesEvent()
    object RestoreNote : NotesEvent()
    object ToggleOrderSection : NotesEvent()
}