package com.rifqi.fitmate.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.rifqi.fitmate.R
import com.rifqi.fitmate.ui.model.NavigationItem
import com.rifqi.fitmate.ui.screens.routelist.ScreenRoute

@Composable
fun BottomBar(
    navController: NavHostController,
    currentRoute: String,
    modifier: Modifier = Modifier
) {

    NavigationBar(
        tonalElevation = 1.dp,
        modifier = modifier
    ) {

        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(R.string.bottom_bar_home),
                icon = if (currentRoute == ScreenRoute.Home.route) R.drawable.ic_home_active else R.drawable.ic_home_inactive,
                screen = ScreenRoute.Home

            ),
            NavigationItem(
                title = stringResource(R.string.bottom_bar_explore),
                icon = if (currentRoute == ScreenRoute.Home.route) R.drawable.ic_explore_active else R.drawable.ic_explore_inactive,
                screen = ScreenRoute.Explore

            ),
            NavigationItem(
                title = stringResource(R.string.bottom_bar_schedule),
                icon = if (currentRoute == ScreenRoute.Home.route) R.drawable.ic_schendule_active else R.drawable.ic_schendule_inactive,
                screen = ScreenRoute.Schendule

            ),
            NavigationItem(
                title = stringResource(R.string.bottom_bar_profile),
                icon = if (currentRoute == ScreenRoute.Home.route) R.drawable.ic_profile_active else R.drawable.ic_profile_inactive,
                screen = ScreenRoute.Profile
            ),
        )


        navigationItems.map { item ->

            NavigationBarItem(


                selected = currentRoute == item.screen.route,
                label = {
                    Text(item.title)
                },
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }

                },

                icon = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {

                        Icon(
                            painterResource(id = item.icon),
                            contentDescription = item.title,
                            modifier = Modifier.size(23.5.dp),


                            )
//                        if (currentRoute == item.screen.route) {
//                            Divider(
//                                color = lightblue60,
//                                thickness = 2.38.dp,
//                                modifier = Modifier
//                                    .width(23.dp)
//                                    .height(2.dp)
//                            )
//                        } else {
//                            Divider(
//                                color = Color.Transparent,
//                                thickness = 2.38.dp,
//                                modifier = Modifier
//                                    .width(23.dp)
//                                    .height(2.dp)
//                            )
//
//                        }

                    }
                },

                )
        }
    }
}