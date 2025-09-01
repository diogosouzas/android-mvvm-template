package com.arch.template.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object UserDetail : Screen("user_detail/{userId}") {
        fun createRoute(userId: Long) = "user_detail/$userId"
    }
}
