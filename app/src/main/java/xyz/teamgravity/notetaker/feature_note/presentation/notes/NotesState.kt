package xyz.teamgravity.notetaker.feature_note.presentation.notes

import xyz.teamgravity.notetaker.feature_note.domain.model.NoteModel
import xyz.teamgravity.notetaker.feature_note.domain.util.NoteOrder
import xyz.teamgravity.notetaker.feature_note.domain.util.OrderType

data class NotesState(
    val notes: List<NoteModel> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
