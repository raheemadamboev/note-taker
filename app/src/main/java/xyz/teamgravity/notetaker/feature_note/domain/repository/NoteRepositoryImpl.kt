package xyz.teamgravity.notetaker.feature_note.domain.repository

import kotlinx.coroutines.flow.Flow
import xyz.teamgravity.notetaker.feature_note.data.data_source.NoteDao
import xyz.teamgravity.notetaker.feature_note.data.repository.NoteRepository
import xyz.teamgravity.notetaker.feature_note.domain.model.NoteModel

class NoteRepositoryImpl(
    private val dao: NoteDao
) : NoteRepository {

    override suspend fun insert(note: NoteModel) = dao.insert(note)

    override suspend fun delete(note: NoteModel)  = dao.delete(note)

    override suspend fun getNote(id: Int) = dao.getNote(id)

    override fun getNotes() = dao.getNotes()
}