package uk.ac.tees.mad.tapnote.presentation.note

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uk.ac.tees.mad.tapnote.data.local.database.TapNoteDatabase
import uk.ac.tees.mad.tapnote.data.local.entity.NoteEntity
import uk.ac.tees.mad.tapnote.data.repository.NotesRepository
import uk.ac.tees.mad.tapnote.presentation.util.toDetailUi

class NoteDetailViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository =
        NotesRepository(TapNoteDatabase.getInstance(application).noteDao())

    private val _note = MutableStateFlow<NoteDetailUiModel?>(null)
    val note: StateFlow<NoteDetailUiModel?> = _note

    fun loadNote(id: Long) {
        if (id == -1L) {
            _note.value = NoteDetailUiModel(
                id = -1,
                content = "",
                timestamp = "New note"
            )
            return
        }

        viewModelScope.launch {
            val entity = repository.getNoteById(id)
            entity?.let {
                _note.value = it.toDetailUi()
            }
        }
    }

    fun save(content: String) {
        viewModelScope.launch {
            val current = _note.value ?: return@launch

            if (current.id == -1L) {
                repository.insertNote(
                    NoteEntity(
                        content = content,
                        timestamp = System.currentTimeMillis()
                    )
                )
            } else {
                repository.updateNote(current.id, content)
            }
        }
    }

    fun delete() {
        viewModelScope.launch {
            val current = _note.value ?: return@launch
            if (current.id != -1L) {
                repository.deleteNote(
                    NoteEntity(
                        id = current.id,
                        content = current.content,
                        timestamp = System.currentTimeMillis()
                    )
                )
            }
        }
    }
}
