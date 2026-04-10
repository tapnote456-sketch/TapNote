package uk.ac.tees.mad.tapnote.presentation.quicknote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import uk.ac.tees.mad.tapnote.ui.theme.TapNoteTheme

class QuickNoteActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TapNoteTheme {

                val vm: QuickNoteViewModel = viewModel()

                QuickNoteScreen(
                    viewModel = vm,
                    onClose = { finish() }
                )
            }
        }
    }
}
