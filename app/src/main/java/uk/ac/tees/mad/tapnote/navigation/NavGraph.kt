package uk.ac.tees.mad.tapnote.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import uk.ac.tees.mad.tapnote.presentation.HomeScreen
import uk.ac.tees.mad.tapnote.presentation.SplashScreen
import uk.ac.tees.mad.tapnote.presentation.auth.AuthScreen

@Composable
fun TapNoteNavGraph() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Splash
    ) {

        composable<Splash> {
            SplashScreen(
                onNavigateNext = {
                    navController.navigate(Auth) {
                        popUpTo<Splash> { inclusive = true }
                    }
                }
            )
        }

        composable<Home> {
            HomeScreen()
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
    }
}
