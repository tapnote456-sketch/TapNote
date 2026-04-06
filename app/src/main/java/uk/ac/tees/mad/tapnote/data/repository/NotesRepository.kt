package uk.ac.tees.mad.tapnote.data.repository

import kotlinx.coroutines.flow.Flow
import uk.ac.tees.mad.tapnote.data.local.dao.NoteDao
import uk.ac.tees.mad.tapnote.data.local.entity.NoteEntity

class NotesRepository(
    private val noteDao: NoteDao
) {

    fun getNotes(): Flow<List<NoteEntity>> =
        noteDao.getAllNotes()

    suspend fun getNoteById(id: Long): NoteEntity? =
        noteDao.getNoteById(id)

    suspend fun insertNote(note: NoteEntity) =
        noteDao.insertNote(note)

    suspend fun deleteNote(note: NoteEntity) =
        noteDao.deleteNote(note)

    suspend fun updateNote(id: Long, content: String) =
        noteDao.updateNote(id, content)

    suspend fun clearAllNotes() =
        noteDao.clearAllNotes()
}
