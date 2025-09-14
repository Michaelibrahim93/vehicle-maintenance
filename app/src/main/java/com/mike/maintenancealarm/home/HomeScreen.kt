package com.mike.maintenancealarm.home

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mike.maintenancealarm.home.navhosts.HomeNavHost
import com.mike.maintenancealarm.home.navhosts.HomeTabRoute
import com.mike.resources.R

@Composable
fun HomeScreenComposable(
    rootNavController: NavController
) {
    var selectedTab by rememberSaveable { mutableStateOf(HomeTab.HOME) }
    val tabNavController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigationBar(
                selectedTab = selectedTab,
                onItemClick = {
                    if (selectedTab == it) return@BottomNavigationBar

                    selectedTab = it
                    navigateToTab(it, tabNavController)
                }
            )
        }
    ) { contentPadding ->
        HomeNavHost(
            rootNavController = rootNavController,
            tabNavController = tabNavController,
            contentPadding = contentPadding
        )
    }
}

private fun navigateToTab(tab: HomeTab, navController: NavHostController) {
    navController.navigate(
        when (tab) {
            HomeTab.HOME -> HomeTabRoute.Vehicles
            HomeTab.PROFILE -> HomeTabRoute.Profile
        }
    ) {
        // Avoid building up a large stack of destinations on the back stack as users select items
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }
        // Avoid building multiple copies of the same destination when reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
    }
}

@Composable
fun BottomNavigationBar(
    selectedTab: HomeTab,
    onItemClick: (HomeTab) -> Unit
) {
    NavigationBar {
        HomeTab.entries.forEach { item ->
            NavigationBarItem(
                selected = selectedTab == item,
                onClick = { onItemClick(item) },
                icon = {
                    Icon(
                        painter = if (selectedTab == item) item.selectedIcon()
                        else item.unSelectedIcon(),
                        contentDescription = item.title()
                    )
                },
                label = {
                    Text(text = item.title())
                }
            )
        }
    }
}

enum class HomeTab(
    @field:StringRes
    val titleRes: Int,
    @field:DrawableRes
    val selectedIconRes: Int,
    @field:DrawableRes
    val unSelectedIconRes: Int,
) {
    HOME(
        titleRes = R.string.home,
        selectedIconRes = R.drawable.ic_home_selected,
        unSelectedIconRes = R.drawable.ic_home_unselected
    ),
    PROFILE(
        titleRes = R.string.profile,
        selectedIconRes = R.drawable.ic_profile_selected,
        unSelectedIconRes = R.drawable.ic_profile_unselected
    );

    @Composable
    fun title() = stringResource(titleRes)
    @Composable
    fun selectedIcon() = painterResource(id = selectedIconRes)
    @Composable
    fun unSelectedIcon() = painterResource(id = unSelectedIconRes)
}