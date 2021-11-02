package xyz.teamgravity.notetaker.feature_note.domain.util

sealed class NoteOrder(val order: OrderType) {
    class Title(order: OrderType) : NoteOrder(order)
    class Date(order: OrderType) : NoteOrder(order)
    class Color(order: OrderType) : NoteOrder(order)

    fun copy(order: OrderType ) = when(this) {
        is Title -> Title(order)
        is Date -> Date(order)
        is Color -> Color(order)
    }
}
