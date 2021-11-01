package xyz.teamgravity.notetaker.feature_note.data.repository

import kotlinx.coroutines.flow.Flow
import xyz.teamgravity.notetaker.feature_note.domain.model.NoteModel

interface NoteRepository {

    suspend fun insert(note: NoteModel)

    suspend fun delete(note: NoteModel)

    suspend fun getNote(id: Int): NoteModel?

    fun getNotes(): Flow<List<NoteModel>>
}