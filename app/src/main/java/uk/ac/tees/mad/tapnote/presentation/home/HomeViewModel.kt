package uk.ac.tees.mad.tapnote.presentation.home

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import uk.ac.tees.mad.tapnote.data.local.database.TapNoteDatabase
import uk.ac.tees.mad.tapnote.data.local.entity.NoteEntity
import uk.ac.tees.mad.tapnote.data.repository.NotesRepository
import uk.ac.tees.mad.tapnote.presentation.utils.toUiModel

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val database = TapNoteDatabase.getInstance(application)
    private val repository = NotesRepository(database.noteDao())

    var isLoading by mutableStateOf(true)
        private set

    val notes: StateFlow<List<NoteUiModel>> =
        repository.getNotes()
            .map { list -> list.map { it.toUiModel() } }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    init {
        viewModelScope.launch {
            repository.getNotes().collect {
                isLoading = false
            }
        }
    }

    fun deleteNote(note: NoteUiModel) {
        viewModelScope.launch {
            repository.deleteNote(
                NoteEntity(
                    id = note.id,
                    content = note.content,
                    timestamp = System.currentTimeMillis()
                )
            )
        }
    }
}