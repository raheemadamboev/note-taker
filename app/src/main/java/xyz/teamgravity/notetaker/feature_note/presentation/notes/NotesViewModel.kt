package xyz.teamgravity.notetaker.feature_note.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import xyz.teamgravity.notetaker.feature_note.domain.model.NoteModel
import xyz.teamgravity.notetaker.feature_note.domain.use_case.AddNoteUseCase
import xyz.teamgravity.notetaker.feature_note.domain.use_case.DeleteNoteUseCase
import xyz.teamgravity.notetaker.feature_note.domain.use_case.GetNotesUseCase
import xyz.teamgravity.notetaker.feature_note.domain.util.NoteOrder
import xyz.teamgravity.notetaker.feature_note.domain.util.OrderType
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val getNotes: GetNotesUseCase,
    private val deleteNote: DeleteNoteUseCase,
    private val addNote: AddNoteUseCase
) : ViewModel() {

    private val _state = mutableStateOf(NotesState())
    val state: State<NotesState> = _state

    private var fetchNotesJob: Job? = null
    private var recentlyDeletedNote: NoteModel? = null

    init {
        fetchNotes(NoteOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.Order -> {
                if (state.value.noteOrder::class == event.noteOrder::class && state.value.noteOrder.order == event.noteOrder.order) return
                fetchNotes(event.noteOrder)
            }

            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    deleteNote(event.note)
                    recentlyDeletedNote = event.note
                }
            }

            NotesEvent.RestoreNote -> {
                viewModelScope.launch {
                    addNote(recentlyDeletedNote ?: return@launch)
                    recentlyDeletedNote = null
                }
            }

            NotesEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun fetchNotes(noteOrder: NoteOrder) {
        fetchNotesJob?.cancel()
        fetchNotesJob = getNotes(noteOrder).onEach { notes ->
            _state.value = state.value.copy(
                notes = notes,
                noteOrder = noteOrder
            )
        }.launchIn(viewModelScope)
    }
}