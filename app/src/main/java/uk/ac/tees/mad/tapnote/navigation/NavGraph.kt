package uk.ac.tees.mad.tapnote.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import uk.ac.tees.mad.tapnote.presentation.home.HomeScreen
import uk.ac.tees.mad.tapnote.presentation.SplashScreen
import uk.ac.tees.mad.tapnote.presentation.auth.AuthScreen
import uk.ac.tees.mad.tapnote.presentation.note.NoteDetailScreen
import uk.ac.tees.mad.tapnote.presentation.note.NoteDetailUiModel

@Composable
fun TapNoteNavGraph() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Splash
    ) {

        composable<Splash> {
            SplashScreen(
                onNavigateToHome = {
                    navController.navigate(Home) {
                        popUpTo<Splash> { inclusive = true }
                    }
                },
                onNavigateToAuth = {
                    navController.navigate(Auth) {
                        popUpTo<Splash> { inclusive = true }
                    }
                }
            )
        }

        composable<Home> {
            HomeScreen(
                onNoteClick = { note ->
                    navController.navigate(
                        NoteDetail(noteId = note.id)
                    )
                },
                onAddNoteClick = {
                    navController.navigate(
                        NoteDetail(noteId = -1L)
                    )
                }
            )
        }

        composable<Auth> {
            AuthScreen(
                onAuthSuccess = {
                    navController.navigate(Home) {
                        popUpTo<Auth> { inclusive = true }
                    }
                }
            )
        }

        composable<NoteDetail> { backStackEntry ->
            val noteId = backStackEntry.arguments
                ?.getLong("noteId") ?: -1L

            val note = if (noteId == -1L) {
                NoteDetailUiModel(
                    id = -1,
                    content = "",
                    timestamp = "New note"
                )
            } else {
                NoteDetailUiModel(
                    id = noteId,
                    content = "Loaded note content for id=$noteId",
                    timestamp = "Loaded from DB"
                )
            }

            NoteDetailScreen(
                note = note,
                onEditClick = { }
            )
        }

    }
}
