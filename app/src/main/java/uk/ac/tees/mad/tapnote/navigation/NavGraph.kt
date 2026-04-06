package uk.ac.tees.mad.tapnote.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import uk.ac.tees.mad.tapnote.presentation.home.HomeScreen
import uk.ac.tees.mad.tapnote.presentation.SplashScreen
import uk.ac.tees.mad.tapnote.presentation.auth.AuthScreen
import uk.ac.tees.mad.tapnote.presentation.home.HomeViewModel
import uk.ac.tees.mad.tapnote.presentation.note.NoteDetailScreen
import uk.ac.tees.mad.tapnote.presentation.note.NoteDetailViewModel
import uk.ac.tees.mad.tapnote.presentation.settings.SettingsScreen
import uk.ac.tees.mad.tapnote.presentation.settings.SettingsViewModel

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

            val viewModel: HomeViewModel = viewModel()

            HomeScreen(
                viewModel = viewModel,
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

            val viewModel: NoteDetailViewModel = viewModel()
            val noteId = backStackEntry.arguments?.getLong("noteId") ?: -1L

            NoteDetailScreen(
                viewModel = viewModel,
                noteId = noteId,
                onBack = { navController.popBackStack() }
            )
        }

        composable<Settings> {

            val viewModel: SettingsViewModel = viewModel()

            SettingsScreen(
                viewModel = viewModel,
                onLogout = {
                    viewModel.logout()
                    navController.navigate(Auth) {
                        popUpTo<Home> { inclusive = true }
                    }
                }
            )
        }
    }
}