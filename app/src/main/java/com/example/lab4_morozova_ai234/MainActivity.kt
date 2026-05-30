package com.example.lab4_morozova_ai234

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lab4_morozova_ai234.ui.theme.Lab3_Morozova_AI234Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab3_Morozova_AI234Theme {
                val navController = rememberNavController()

                val context = LocalContext.current
                val database = DatabaseProvider.getDatabase(context)
                val repository = ProjectRepository(database.projectDao())

                val projectViewModel: ProjectViewModel = viewModel(
                    factory = ProjectViewModelFactory(repository)
                )

                NavHost(
                    navController = navController,
                    startDestination = Screen.List.route
                ) {
                    composable(Screen.List.route) {
                        ProjectsListScreen(
                            viewModel = projectViewModel,
                            onAddProjectClick = { navController.navigate(Screen.Add.route) },
                            onProjectClick = { id -> navController.navigate(Screen.Details.createRoute(id)) }
                        )
                    }

                    composable(Screen.Add.route) {
                        AddProjectScreen(
                            onAddProject = { name, desc ->
                                projectViewModel.addProject(name, desc)
                                navController.popBackStack()
                            },
                            onCancel = { navController.popBackStack() }
                        )
                    }

                    composable(Screen.Details.route) { backStackEntry ->
                        val projectIdString = backStackEntry.arguments?.getString("projectId") ?: "0"
                        val projectId = projectIdString.toIntOrNull() ?: 0

                        DetailsProjectScreen(
                            projectId = projectId,
                            viewModel = projectViewModel,
                            onBack = { navController.popBackStack() },
                            onDelete = { projectToDelete ->
                                projectViewModel.deleteProject(projectToDelete)
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}