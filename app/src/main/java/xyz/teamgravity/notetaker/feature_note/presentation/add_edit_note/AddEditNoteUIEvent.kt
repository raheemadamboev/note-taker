package xyz.teamgravity.notetaker.feature_note.presentation.add_edit_note

sealed class AddEditNoteUIEvent {
    data class ShowSnackbar(val message: String) : AddEditNoteUIEvent()
    object SaveNote : AddEditNoteUIEvent()
}
