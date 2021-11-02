package xyz.teamgravity.notetaker.feature_note.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import xyz.teamgravity.notetaker.feature_note.data.repository.NoteRepository
import xyz.teamgravity.notetaker.feature_note.domain.model.NoteModel
import xyz.teamgravity.notetaker.feature_note.domain.util.NoteOrder
import xyz.teamgravity.notetaker.feature_note.domain.util.OrderType

class GetNotesUseCase(private val repository: NoteRepository) {

    operator fun invoke(order: NoteOrder = NoteOrder.Date(OrderType.Descending)): Flow<List<NoteModel>> {
        return repository.getNotes().map { notes ->
            when (order) {

                is NoteOrder.Title -> {
                    when (order.order) {
                        OrderType.Ascending -> notes.sortedBy { it.title.lowercase() }
                        OrderType.Descending -> notes.sortedByDescending { it.title.lowercase() }
                    }
                }

                is NoteOrder.Date -> {
                    when (order.order) {
                        OrderType.Ascending -> notes.sortedBy { it.timestamp.time }
                        OrderType.Descending -> notes.sortedByDescending { it.timestamp.time }
                    }
                }

                is NoteOrder.Color -> {
                    when (order.order) {
                        OrderType.Ascending -> notes.sortedBy { it.color }
                        OrderType.Descending -> notes.sortedByDescending { it.color }
                    }
                }
            }
        }
    }
}