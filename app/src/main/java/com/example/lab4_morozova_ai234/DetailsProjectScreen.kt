package com.example.lab4_morozova_ai234

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsProjectScreen(
    projectId: Int,
    viewModel: ProjectViewModel,
    onBack: () -> Unit,
    onDelete: (ProjectEntity) -> Unit
) {
    val project by viewModel.getProjectById(projectId).collectAsState(initial = null)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Деталі проекту", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF4A90E2))
            )
        }
    ) { padding ->
        project?.let { currentProject ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = currentProject.name, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text(text = currentProject.description, fontSize = 16.sp, color = Color.Gray)

                Spacer(modifier = Modifier.height(32.dp))

                Text(text = "Прогрес", fontWeight = FontWeight.Medium)
                Text(text = "${currentProject.progress.toInt()}%", fontSize = 32.sp, fontWeight = FontWeight.Bold)

                Slider(
                    value = currentProject.progress,
                    onValueChange = { viewModel.updateProgress(currentProject, it) },
                    valueRange = 0f..100f,
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = onBack,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black)
                ) {
                    Text("Назад")
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = { onDelete(currentProject) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Red)
                ) {
                    Text("Видалити")
                }
            }
        } ?: Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Проект не знайдено")
        }
    }
}