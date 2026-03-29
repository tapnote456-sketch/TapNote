package uk.ac.tees.mad.tapnote.presentation.home

import uk.ac.tees.mad.tapnote.data.local.entity.NoteEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun NoteEntity.toUiModel(): NoteUiModel {
    val formatter = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
    return NoteUiModel(
        id = id,
        content = content,
        timestamp = formatter.format(Date(timestamp))
    )
}
