package uk.ac.tees.mad.tapnote.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import uk.ac.tees.mad.tapnote.presentation.home.HomeScreen
import uk.ac.tees.mad.tapnote.presentation.splash.SplashScreen
import uk.ac.tees.mad.tapnote.presentation.auth.AuthScreen
import uk.ac.tees.mad.tapnote.presentation.components.TapNoteScaffold
import uk.ac.tees.mad.tapnote.presentation.home.HomeViewModel
import uk.ac.tees.mad.tapnote.presentation.note.NoteDetailScreen
import uk.ac.tees.mad.tapnote.presentation.note.NoteDetailViewModel
import uk.ac.tees.mad.tapnote.presentation.quicknote.QuickNoteScreen
import uk.ac.tees.mad.tapnote.presentation.quicknote.QuickNoteViewModel
import uk.ac.tees.mad.tapnote.presentation.settings.SettingsScreen
import uk.ac.tees.mad.tapnote.presentation.settings.SettingsViewModel
import uk.ac.tees.mad.tapnote.presentation.splash.SplashViewModel

@Composable
fun TapNoteNavGraph() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Splash
    ) {

        composable<Splash> {
            val viewModel: SplashViewModel = viewModel()

            SplashScreen(
                viewModel = viewModel,
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

            TapNoteScaffold(
                showTopBar = true,
                onSettingsClick = { navController.navigate(Settings) }
            ) { padding ->
                HomeScreen(
                    modifier = Modifier.padding(padding),
                    viewModel = viewModel,
                    onAddNoteClick = {
                        navController.navigate(NoteDetail(noteId = -1L))
                    },
                    onNoteClick = { note ->
                        navController.navigate(NoteDetail(noteId = note.id))
                    }
                )
            }
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

            TapNoteScaffold(
                showTopBar = true,
                onSettingsClick = { navController.navigate(Settings) }
            ) { padding ->
                NoteDetailScreen(
                    viewModel = viewModel,
                    noteId = noteId,
                    onBack = { navController.popBackStack() },
                    modifier = Modifier.padding(padding),
                )
            }
        }

        composable<Settings> {

            val viewModel: SettingsViewModel = viewModel()

            TapNoteScaffold(
                showTopBar = true,
                onSettingsClick = {}
            ) { padding ->
                SettingsScreen(
                    viewModel = viewModel,
                    onLogout = {
                        viewModel.logout()
                        navController.navigate(Auth) {
                            popUpTo<Home> { inclusive = true }
                        }
                    },
                    modifier = Modifier.padding(padding),
                )
            }
        }

        composable<QuickNote> {

            val viewModel: QuickNoteViewModel = viewModel()

            QuickNoteScreen(
                viewModel = viewModel,
                onClose = { navController.popBackStack() }
            )
        }
    }
}