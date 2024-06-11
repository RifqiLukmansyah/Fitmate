@file:Suppress("NAME_SHADOWING")

package com.rifqi.fitmate.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rifqi.fitmate.ui.composables.AppBar
import com.rifqi.fitmate.ui.composables.BottomBar
import com.rifqi.fitmate.ui.screens.detailschedule.DetailScheduleScreen
import com.rifqi.fitmate.ui.screens.detailworkout.DetailWorkoutScreen
import com.rifqi.fitmate.ui.screens.equimentsearch.EquimentSearchScreen
import com.rifqi.fitmate.ui.screens.explore.ExploreScreen
import com.rifqi.fitmate.ui.screens.home.HomeScreen
import com.rifqi.fitmate.ui.screens.interactivelearn.InteractiveLearnScreen
import com.rifqi.fitmate.ui.screens.onboarding.OnBoardingScreen
import com.rifqi.fitmate.ui.screens.profile.ProfileScreen
import com.rifqi.fitmate.ui.screens.profile.ProfileViewModel
import com.rifqi.fitmate.ui.screens.remindersetting.ReminderSettingScreen
import com.rifqi.fitmate.ui.screens.routelist.ScreenRoute
import com.rifqi.fitmate.ui.screens.schedule.ScheduleScreen
import com.rifqi.fitmate.ui.screens.sign_in.GoogleAuthClient
import com.rifqi.fitmate.ui.util.OnBordingPrefence

@Composable
fun FitmateApp(
    navController: NavHostController = rememberNavController(),
    googleAuthUiClient: GoogleAuthClient,
    onSignInClick: () -> Unit,
    onLogoutClick: () -> Unit
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val routesWithBottomBar = listOf(
        ScreenRoute.Home.route,
        ScreenRoute.Profile.route,
        ScreenRoute.Explore.route,
        ScreenRoute.Schedule.route
    )
    val context = LocalContext.current
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        topBar = {

            if (currentRoute == ScreenRoute.Home.route || currentRoute == ScreenRoute.Explore.route) {
                AppBar(
                    navigatToEquimentSearch = {
                        navController.navigate(ScreenRoute.EquimentSearch.route)

                    },
                    navigateToExplore = { newSearchQuery ->
                        searchQuery = newSearchQuery // Update the searchQuery
                        navController.navigate(ScreenRoute.Explore.createRoute(newSearchQuery)) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                inclusive = true
                            }
                            launchSingleTop = false
                            restoreState = false
                        }
                    }

                )
            }
        },
        bottomBar = {

            if (currentRoute in routesWithBottomBar) {
                if (currentRoute != null) {
                    BottomBar(navController = navController, currentRoute = currentRoute)
                }
            }
        }) { paddingValue ->
        NavHost(
            navController = navController,
            startDestination = if (OnBordingPrefence.isOnboardingCompleted(context)) ScreenRoute.Home.route else ScreenRoute.OnBoarding.route,
            modifier = Modifier.padding(paddingValue)
        ) {
            composable(ScreenRoute.Home.route) {
                HomeScreen(
                    navigateToDetail = { workoutId ->
                        navController.navigate(ScreenRoute.DetailWorkout.createRoute(workoutId))
                    },
                    navigateToDetailSchedule = { workoutdate ->
                        navController.navigate(ScreenRoute.DetailSchedule.createRoute(workoutdate))
                    },

                    )
            }
            composable(
                ScreenRoute.DetailWorkout.route,
                arguments = listOf(
                    navArgument("workoutId") { type = NavType.LongType }),
            ) {
                val workoutId = it.arguments?.getLong("workoutId") ?: -1L
                LocalContext.current
                DetailWorkoutScreen(
                    workoutId = workoutId,
                    navigateBack = { navController.navigateUp() },
                    navigateToInteractiveArea = { workoutId ->
                        navController.navigate(ScreenRoute.InteractiveLearn.createRoute(workoutId))
                    }
                )
            }

            composable(ScreenRoute.Explore.route ,                arguments = listOf(
                navArgument("searchQuery") { type = NavType.StringType }),) {
                val searchQuery = it.arguments?.getString("searchQuery") ?: ""

                ExploreScreen(
                    searchQuery = searchQuery,
                    navigateToDetail = { workoutId ->
                        navController.navigate(ScreenRoute.DetailWorkout.createRoute(workoutId))
                    },
                )
            }
            composable(ScreenRoute.OnBoarding.route) {
                OnBoardingScreen(
                    navigateToHome = {
                        navController.navigate(ScreenRoute.Home.route)
                    }
                )
            }
            composable(
                ScreenRoute.InteractiveLearn.route,
                arguments = listOf(
                    navArgument("workoutId") { type = NavType.LongType }),
            ) {
                val workoutId = it.arguments?.getLong("workoutId") ?: -1L
                LocalContext.current
                InteractiveLearnScreen(
                    workoutId = workoutId,
                    navigateToHome = { navController.navigate(ScreenRoute.Home.route) },
                )
            }
            composable(ScreenRoute.Schedule.route) {

                ScheduleScreen(
                    navigateToDetail = { workoutdate ->
                        navController.navigate(ScreenRoute.DetailSchedule.createRoute(workoutdate))
                    },
                    navigateToScheduleSetting = {
                        navController.navigate(ScreenRoute.ReminderSetting.route)
                    }
                )
            }
            composable(
                ScreenRoute.DetailSchedule.route,
                arguments = listOf(
                    navArgument("workoutdate") { type = NavType.StringType }),
            ) {
                val workoutdate = it.arguments?.getString("workoutdate") ?: ""
                DetailScheduleScreen(
                    workoutdate = workoutdate,
                    navigateToDetailSchedule = { workoutId ->
                        navController.navigate(ScreenRoute.DetailWorkout.createRoute(workoutId))
                    },
                    navigateBack = { navController.navigateUp() },
                )
            }

            composable(ScreenRoute.EquimentSearch.route) {
                EquimentSearchScreen(
                    navigateToDetail = { workoutId ->
                        navController.navigate(ScreenRoute.DetailWorkout.createRoute(workoutId))
                    },
                )
            }

            composable(ScreenRoute.ReminderSetting.route) {
                ReminderSettingScreen(
                    navigateBack = { navController.navigateUp() },

                    )
            }
            composable(ScreenRoute.Profile.route) {
                viewModel<ProfileViewModel>()

//                LaunchedEffect(key1 = Unit) {
//                    if (googleAuthUiClient.getSignedInUser() == null) {
//                        navController.navigate("sign_in")
//                    }
//                }

                ProfileScreen(
                    userData = googleAuthUiClient.getSignedInUser(),
                    onSignIn = onSignInClick,
                    onSignOut = onLogoutClick,
                    redirectToHome = {
                        navController.navigate("home")
                    }
                )
            }


        }

    }


}

