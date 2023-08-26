package com.vimalcvs.bottalkai.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet
import com.vimalcvs.bottalkai.common.Constants
import com.vimalcvs.bottalkai.common.Constants.TRANSITION_ANIMATION_DURATION
import com.vimalcvs.bottalkai.ui.assistants.AiAssistantsScreen
import com.vimalcvs.bottalkai.ui.chat.ChatScreen
import com.vimalcvs.bottalkai.ui.history.DeleteHistoryBottomSheet
import com.vimalcvs.bottalkai.ui.history.HistoryScreen
import com.vimalcvs.bottalkai.ui.language.LanguageScreen
import com.vimalcvs.bottalkai.ui.settings.LogoutBottomSheet
import com.vimalcvs.bottalkai.ui.settings.SettingsScreen
import com.vimalcvs.bottalkai.ui.splash.SplashScreen
import com.vimalcvs.bottalkai.ui.startchat.StartChatScreen
import com.vimalcvs.bottalkai.ui.upgrade.PurchaseHelper
import com.vimalcvs.bottalkai.ui.upgrade.UpgradeScreen

@OptIn(ExperimentalMaterialNavigationApi::class)
@ExperimentalAnimationApi
@Composable
fun NavGraph(
    navController: NavHostController,
    bottomBarState: MutableState<Boolean>,
    darkMode: MutableState<Boolean>,
    purchaseHelper: PurchaseHelper
) {

    val paddingBottom =
        animateDpAsState(
            if (bottomBarState.value) 56.dp else 0.dp,
            animationSpec = tween(TRANSITION_ANIMATION_DURATION)
        )

    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .padding(bottom = paddingBottom.value)

    ) {
        bottomSheet(route = Screen.DeleteHistory.route) {
            DeleteHistoryBottomSheet(onConfirmClick = {
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set(Constants.IS_DELETE, true)
                navController.popBackStack()
            }, onCancelClick = {
                navController.popBackStack()
            })
        }

        bottomSheet(route = Screen.Logout.route) {
            LogoutBottomSheet(onConfirmClick = {
                navController.navigate(Screen.Splash.route) {
                    popUpTo(Screen.Splash.route) {
                        inclusive = true
                    }
                }
            }, onCancelClick = {
                navController.popBackStack()
            })
        }



        composable(
            route = Screen.Splash.route
        ) {
            SplashScreen(
                navigateToStartChat = {
                    navController.navigate(Screen.StartChat.route) {
                        popUpTo(Screen.Splash.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(
            route = Screen.StartChat.route,
            enterTransition = {
                when (initialState.destination.route) {
                    "${Screen.Chat.route}?name={name}&role={role}&examples={examples}&id={id}" ->
                        slideInHorizontally(
                            initialOffsetX = { fullWidth -> fullWidth },
                            animationSpec = tween(TRANSITION_ANIMATION_DURATION)
                        )

                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    "${Screen.Chat.route}?name={name}&role={role}&examples={examples}&id={id}" ->
                        slideOutHorizontally(
                            targetOffsetX = { fullWidth -> -fullWidth },
                            animationSpec = tween(TRANSITION_ANIMATION_DURATION)
                        )

                    else -> null
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    "${Screen.Chat.route}?name={name}&role={role}&examples={examples}&id={id}" ->
                        slideInHorizontally(
                            initialOffsetX = { fullWidth -> -fullWidth },
                            animationSpec = tween(TRANSITION_ANIMATION_DURATION)
                        )

                    else -> null
                }
            }
        ) {
            StartChatScreen(
                navigateToChat = { name, role, examples ->
                    val examplesString = examples?.joinToString(separator = "|")
                    navController.navigate("${Screen.Chat.route}?name=$name&role=$role&examples=$examplesString")
                },
                navigateToUpgrade = {
                    navController.navigate(Screen.Upgrade.route)
                },
                purchaseHelper = purchaseHelper
            )
        }

        composable(route = "${Screen.Chat.route}?name={name}&role={role}&examples={examples}&id={id}",
            popEnterTransition = {
                when (initialState.destination.route) {
                    Screen.StartChat.route ->
                        slideInHorizontally(
                            initialOffsetX = { fullWidth -> fullWidth },
                            animationSpec = tween(TRANSITION_ANIMATION_DURATION)
                        )

                    Screen.AiAssistants.route ->
                        slideInHorizontally(
                            initialOffsetX = { fullWidth -> fullWidth },
                            animationSpec = tween(TRANSITION_ANIMATION_DURATION)
                        )

                    Screen.History.route ->
                        slideInHorizontally(
                            initialOffsetX = { fullWidth -> fullWidth },
                            animationSpec = tween(TRANSITION_ANIMATION_DURATION)
                        )

                    else -> null
                }
            },
            enterTransition = {
                when (initialState.destination.route) {
                    Screen.StartChat.route ->
                        slideInHorizontally(
                            initialOffsetX = { fullWidth -> fullWidth },
                            animationSpec = tween(TRANSITION_ANIMATION_DURATION)
                        )

                    Screen.AiAssistants.route ->
                        slideInHorizontally(
                            initialOffsetX = { fullWidth -> fullWidth },
                            animationSpec = tween(TRANSITION_ANIMATION_DURATION)
                        )

                    Screen.History.route ->
                        slideInHorizontally(
                            initialOffsetX = { fullWidth -> fullWidth },
                            animationSpec = tween(TRANSITION_ANIMATION_DURATION)
                        )

                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    Screen.StartChat.route ->
                        slideOutHorizontally(
                            targetOffsetX = { fullWidth -> -fullWidth },
                            animationSpec = tween(TRANSITION_ANIMATION_DURATION)
                        )

                    Screen.AiAssistants.route ->
                        slideOutHorizontally(
                            targetOffsetX = { fullWidth -> -fullWidth },
                            animationSpec = tween(TRANSITION_ANIMATION_DURATION)
                        )

                    Screen.History.route ->
                        slideOutHorizontally(
                            targetOffsetX = { fullWidth -> -fullWidth },
                            animationSpec = tween(TRANSITION_ANIMATION_DURATION)
                        )

                    else -> null
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    Screen.StartChat.route ->
                        slideOutHorizontally(
                            targetOffsetX = { fullWidth -> fullWidth },
                            animationSpec = tween(TRANSITION_ANIMATION_DURATION)
                        )

                    Screen.AiAssistants.route ->
                        slideOutHorizontally(
                            targetOffsetX = { fullWidth -> fullWidth },
                            animationSpec = tween(TRANSITION_ANIMATION_DURATION)
                        )

                    Screen.History.route ->
                        slideOutHorizontally(
                            targetOffsetX = { fullWidth -> fullWidth },
                            animationSpec = tween(TRANSITION_ANIMATION_DURATION)
                        )

                    else -> null
                }
            }) {

            var exampleList: List<String> = emptyList()

            if (it.arguments?.getString("examples") != "null") {
                exampleList =
                    it.arguments?.getString("examples")?.split("|")?.toTypedArray()?.toList()
                        ?: emptyList()
            }


            ChatScreen(
                navigateToBack = {
                    navController.popBackStack()
                },
                navigateToUpgrade = {
                    navController.navigate(Screen.Upgrade.route)
                },
                it.arguments?.getString("name"),
                exampleList
            )
        }

        composable(route = Screen.AiAssistants.route,
            enterTransition = {
                when (initialState.destination.route) {
                    "${Screen.Chat.route}?name={name}&role={role}&examples={examples}&id={id}" ->
                        slideInHorizontally(
                            initialOffsetX = { fullWidth -> fullWidth },
                            animationSpec = tween(TRANSITION_ANIMATION_DURATION)
                        )

                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    "${Screen.Chat.route}?name={name}&role={role}&examples={examples}&id={id}" ->
                        slideOutHorizontally(
                            targetOffsetX = { fullWidth -> -fullWidth },
                            animationSpec = tween(TRANSITION_ANIMATION_DURATION)
                        )

                    else -> null
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    "${Screen.Chat.route}?name={name}&role={role}&examples={examples}&id={id}" ->
                        slideInHorizontally(
                            initialOffsetX = { fullWidth -> -fullWidth },
                            animationSpec = tween(TRANSITION_ANIMATION_DURATION)
                        )

                    else -> null
                }
            }
        ) {
            AiAssistantsScreen(
                navigateToChat = { name, role, examples ->
                    val examplesString = examples.joinToString(separator = "|")
                    navController.navigate("${Screen.Chat.route}?name=$name&role=$role&examples=$examplesString")
                }
            )
        }

        composable(route = Screen.History.route) {

            val screenResult = navController.currentBackStackEntry
                ?.savedStateHandle

            HistoryScreen(
                navigateToChat = { name, role, examples, id ->
                    val examplesString = examples?.joinToString(separator = "|")
                    navController.navigate("${Screen.Chat.route}?name=$name&role=$role&examples=$examplesString&id=$id")
                },
                navigateToDeleteConversations = {
                    navController.navigate(Screen.DeleteHistory.route)
                },
                savedStateHandle = screenResult
            )
        }

        composable(route = Screen.Settings.route,
            enterTransition = {
                when (initialState.destination.route) {
                    Screen.Languages.route ->
                        slideInHorizontally(
                            initialOffsetX = { fullWidth -> fullWidth },
                            animationSpec = tween(TRANSITION_ANIMATION_DURATION)
                        )

                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    Screen.Languages.route ->
                        slideOutHorizontally(
                            targetOffsetX = { fullWidth -> -fullWidth },
                            animationSpec = tween(TRANSITION_ANIMATION_DURATION)
                        )

                    else -> null
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    Screen.Languages.route ->
                        slideInHorizontally(
                            initialOffsetX = { fullWidth -> -fullWidth },
                            animationSpec = tween(TRANSITION_ANIMATION_DURATION)
                        )

                    else -> null
                }
            }) {
            SettingsScreen(
                darkMode = darkMode,
                navigateToLanguages = {
                    navController.navigate(Screen.Languages.route)
                },
                navigateToUpgrade = {
                    navController.navigate(Screen.Upgrade.route)
                },
                purchaseHelper = purchaseHelper
            )
        }

        composable(route = Screen.Languages.route,
            enterTransition = {
                when (initialState.destination.route) {
                    Screen.Settings.route ->
                        slideInHorizontally(
                            initialOffsetX = { fullWidth -> fullWidth },
                            animationSpec = tween(TRANSITION_ANIMATION_DURATION)
                        )

                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    Screen.Settings.route ->
                        slideOutHorizontally(
                            targetOffsetX = { fullWidth -> -fullWidth },
                            animationSpec = tween(TRANSITION_ANIMATION_DURATION)
                        )

                    else -> null
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    Screen.Settings.route ->
                        slideOutHorizontally(
                            targetOffsetX = { fullWidth -> fullWidth },
                            animationSpec = tween(TRANSITION_ANIMATION_DURATION)
                        )

                    else -> null
                }
            }) {

            LanguageScreen(
                navigateToBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(route = Screen.Upgrade.route,
            enterTransition = {
                when (initialState.destination.route) {
                    Screen.Settings.route ->
                        slideInVertically(
                            initialOffsetY = { fullWidth -> fullWidth },
                            animationSpec = tween(TRANSITION_ANIMATION_DURATION)
                        )

                    Screen.StartChat.route ->
                        slideInVertically(
                            initialOffsetY = { fullWidth -> fullWidth },
                            animationSpec = tween(TRANSITION_ANIMATION_DURATION)
                        )

                    "${Screen.Chat.route}?name={name}&role={role}&examples={examples}&id={id}" ->
                        slideInVertically(
                            initialOffsetY = { fullWidth -> fullWidth },
                            animationSpec = tween(TRANSITION_ANIMATION_DURATION)
                        )

                    else -> null
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    Screen.Settings.route ->
                        slideOutVertically(
                            targetOffsetY = { fullWidth -> fullWidth },
                            animationSpec = tween(TRANSITION_ANIMATION_DURATION)
                        )

                    Screen.StartChat.route ->
                        slideOutVertically(
                            targetOffsetY = { fullWidth -> fullWidth },
                            animationSpec = tween(TRANSITION_ANIMATION_DURATION)
                        )

                    "${Screen.Chat.route}?name={name}&role={role}&examples={examples}&id={id}" ->
                        slideOutVertically(
                            targetOffsetY = { fullWidth -> fullWidth },
                            animationSpec = tween(TRANSITION_ANIMATION_DURATION)
                        )

                    else -> null
                }
            }) {

            UpgradeScreen(
                purchaseHelper,
                navigateToBack = {
                    navController.popBackStack()
                }
            )
        }
    }

}
