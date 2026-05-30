package com.example.lab4_morozova_ai234

sealed class Screen(val route: String) {
    object List : Screen("projects_list")
    object Add : Screen("add_project")
    object Details : Screen("details/{projectId}") {
        fun createRoute(projectId: Int) = "details/$projectId" // Тепер приймає Int
    }
}