package xyz.teamgravity.notetaker.feature_note.data.data_source

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import xyz.teamgravity.notetaker.feature_note.domain.model.NoteModel

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: NoteModel)

    @Delete
    suspend fun delete(note: NoteModel)

    @Query("SELECT * FROM ${DatabaseConst.NOTE_TABLE} WHERE id = :id")
    suspend fun getNote(id: Int): NoteModel?

    @Query("SELECT * FROM ${DatabaseConst.NOTE_TABLE} ORDER BY timestamp DESC")
    fun getNotes(): Flow<List<NoteModel>>
}