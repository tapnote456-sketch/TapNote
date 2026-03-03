package uk.ac.tees.mad.tapnote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import uk.ac.tees.mad.tapnote.navigation.TapNoteNavGraph
import uk.ac.tees.mad.tapnote.ui.theme.TapNoteTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TapNoteTheme {
                TapNoteNavGraph()
            }
        }
    }
}