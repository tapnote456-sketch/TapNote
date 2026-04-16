package uk.ac.tees.mad.tapnote

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import uk.ac.tees.mad.tapnote.navigation.TapNoteNavGraph
import uk.ac.tees.mad.tapnote.service.ShakeService
import uk.ac.tees.mad.tapnote.ui.theme.TapNoteTheme
import kotlin.jvm.java

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 1)
        }

        val user = FirebaseAuth.getInstance().currentUser

        if (user != null) {
            startForegroundService(Intent(this, ShakeService::class.java))
        }

        setContent {
            TapNoteTheme {
                TapNoteNavGraph()
            }
        }
    }
}