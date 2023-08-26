package com.vimalcvs.bottalkai.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.vimalcvs.bottalkai.common.Constants.TRANSITION_ANIMATION_DURATION
import com.vimalcvs.bottalkai.common.click
import com.vimalcvs.bottalkai.ui.theme.Green
import com.vimalcvs.bottalkai.ui.theme.Urbanist

@Composable
fun BottomNavigationBar(
    navController: NavController,
    bottomBarState: MutableState<Boolean>
) {

    val items = listOf(
        BottomNavItem.Chat,
        BottomNavItem.AiAssistants,
        BottomNavItem.History,
        BottomNavItem.Settings
    )

    AnimatedVisibility(
        visible = bottomBarState.value,
        enter = slideInVertically(
            initialOffsetY = { it },
            animationSpec = tween(TRANSITION_ANIMATION_DURATION)
        ),
        exit = slideOutVertically(
            targetOffsetY = { it },
            animationSpec = tween(TRANSITION_ANIMATION_DURATION)
        ),
        content = {

            BottomNavigation(
                elevation = 0.dp,
                backgroundColor = MaterialTheme.colors.background,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(MaterialTheme.colors.background)

            ) {

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { item ->
                    val selected =
                        currentDestination?.hierarchy?.any { it.route == item.route } == true

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .weight(1f)
                            .heightIn(56.dp)
                            .click {
                                navController.navigate(item.route) {
                                    // Pop up to the start destination of the current navigation graph
                                    popUpTo(navController.graph.id) {
                                        saveState = true
                                    }
                                    launchSingleTop = false
                                    restoreState = true
                                }
                            }) {

                        Icon(
                            painter = painterResource(if (selected) item.icon_filled else item.icon),
                            contentDescription = stringResource(id = item.title),
                            tint = if (selected) Green else MaterialTheme.colors.onSurface
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = stringResource(id = item.title),
                            fontFamily = Urbanist,
                            fontSize = 11.sp,
                            fontWeight = if (selected) FontWeight.W700 else FontWeight.W600,
                            color = if (selected) Green else MaterialTheme.colors.onSurface,
                        )
                    }

                }
            }

        })


}