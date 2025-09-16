package com.mike.maintenancealarm.home

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mike.core.presentation.utils.compose.DeviceType
import com.mike.core.presentation.utils.compose.rememberDeviceInfo
import com.mike.maintenancealarm.home.navhosts.HomeNavHost
import com.mike.maintenancealarm.home.navhosts.HomeTabRoute
import com.mike.resources.R

@Composable
fun HomeScreenComposable(
    rootNavController: NavController
) {
    var selectedTab by rememberSaveable { mutableStateOf(HomeTab.HOME) }
    val tabNavController = rememberNavController()

    val deviceInfo = rememberDeviceInfo()
    val onTabSelected: (HomeTab) -> Unit = { tab: HomeTab ->
        if (selectedTab != tab) {
            selectedTab = tab
            navigateToTab(tab, tabNavController)
        }
    }

    if (deviceInfo.deviceType == DeviceType.MOBILE) {
        MobileHomeScreen(
            rootNavController = rootNavController,
            selectedTab = selectedTab,
            tabNavController = tabNavController,
            onTabSelected = onTabSelected
        )
    } else {
        DesktopHomeScreen(
            rootNavController = rootNavController,
            selectedTab = selectedTab,
            tabNavController = tabNavController,
            onTabSelected = onTabSelected
        )
    }
}

@Composable
fun DesktopHomeScreen(
    rootNavController: NavController,
    selectedTab: HomeTab,
    tabNavController: NavHostController,
    onTabSelected: (HomeTab) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { contentPadding ->
        Row(
            modifier = Modifier.fillMaxSize()
                .padding(top = contentPadding.calculateTopPadding())
        ){
            NavigationRail(
                modifier = Modifier.background(MaterialTheme.colorScheme.inverseOnSurface)
                    .offset(x = (-1).dp)
            ) {
                Column (
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    HomeTab.entries.forEach { item ->
                        NavigationRailItem(
                            selected = selectedTab == item,
                            onClick = { onTabSelected(item) },
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

            HomeNavHost(
                rootNavController = rootNavController,
                tabNavController = tabNavController,
                contentPadding = PaddingValues(
                    bottom = contentPadding.calculateBottomPadding()
                )
            )
        }
    }
}

@Composable
fun MobileHomeScreen(
    rootNavController: NavController,
    tabNavController: NavHostController,
    selectedTab: HomeTab,
    onTabSelected: (HomeTab) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigationBar(
                selectedTab = selectedTab,
                onItemClick = onTabSelected
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