package xyz.teamgravity.notetaker.injection

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.teamgravity.notetaker.feature_note.data.data_source.DatabaseConst
import xyz.teamgravity.notetaker.feature_note.data.data_source.NoteDao
import xyz.teamgravity.notetaker.feature_note.data.data_source.NoteDatabase
import xyz.teamgravity.notetaker.feature_note.data.repository.NoteRepository
import xyz.teamgravity.notetaker.feature_note.domain.repository.NoteRepositoryImpl
import xyz.teamgravity.notetaker.feature_note.domain.use_case.DeleteNoteUseCase
import xyz.teamgravity.notetaker.feature_note.domain.use_case.GetNotesUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase =
        Room.databaseBuilder(app, NoteDatabase::class.java, DatabaseConst.NAME).build()

    @Provides
    @Singleton
    fun provideNoteDao(db: NoteDatabase) = db.noteDao()

    @Provides
    fun provideNoteRepository(noteDao: NoteDao): NoteRepository = NoteRepositoryImpl(noteDao)

    @Provides
    fun provideGetNotesUseCase(noteRepository: NoteRepository): GetNotesUseCase = GetNotesUseCase(noteRepository)

    @Provides
    fun provideDeleteNoteUseCase(noteRepository: NoteRepository): DeleteNoteUseCase = DeleteNoteUseCase(noteRepository)
}