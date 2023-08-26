package com.vimalcvs.bottalkai.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash_screen")
    object StartChat : Screen("start_chat_screen")
    object Chat : Screen("chat_screen")
    object AiAssistants : Screen("ai_assistants_screen")
    object History : Screen("history_screen")
    object Settings : Screen("settings_screen")
    object DeleteHistory : Screen("delete_history_screen")
    object Languages : Screen("languages_screen")
    object Logout : Screen("logout_screen")
    object Upgrade : Screen("upgrade_screen")
}