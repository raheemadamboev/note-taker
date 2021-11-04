package xyz.teamgravity.notetaker.feature_note.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import xyz.teamgravity.notetaker.feature_note.domain.model.NoteModel

class NoteRepositoryTestImpl : NoteRepository {

    private val notes = mutableListOf<NoteModel>()

    override suspend fun insert(note: NoteModel) {
        notes.add(note)
    }

    override suspend fun delete(note: NoteModel) {
        notes.remove(note)
    }

    override suspend fun getNote(id: Int): NoteModel? {
        return notes.find { it.id == id }
    }

    override fun getNotes(): Flow<List<NoteModel>> {
        return flow { emit(notes) }
    }
}