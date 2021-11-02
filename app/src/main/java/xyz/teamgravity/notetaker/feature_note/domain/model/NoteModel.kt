package xyz.teamgravity.notetaker.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import xyz.teamgravity.notetaker.feature_note.data.data_source.DatabaseConst
import xyz.teamgravity.notetaker.ui.theme.*
import java.util.*

@Entity(tableName = DatabaseConst.NOTE_TABLE)
data class NoteModel(

    @PrimaryKey
    val id: Int? = null,

    val title: String = "",
    val content: String = "",
    val color: Int = 0,

    val timestamp: Date = Date()
) {

    companion object {
        val noteColors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }
}

class InvalidNoteException(message: String) : Exception(message)
