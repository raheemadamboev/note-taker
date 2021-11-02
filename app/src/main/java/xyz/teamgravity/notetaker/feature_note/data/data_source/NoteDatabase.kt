package xyz.teamgravity.notetaker.feature_note.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import xyz.teamgravity.notetaker.feature_note.domain.model.NoteModel

@TypeConverters(Converters::class)
@Database(
    entities = [NoteModel::class],
    version = DatabaseConst.VERSION,
    exportSchema = false
)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao
}