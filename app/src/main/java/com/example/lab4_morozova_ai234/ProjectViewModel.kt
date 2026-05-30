package com.example.lab4_morozova_ai234

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProjectViewModel(private val repository: ProjectRepository) : ViewModel() {

    val projects: StateFlow<List<ProjectEntity>> = repository.allProjects.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun addProject(name: String, description: String) {
        viewModelScope.launch {
            repository.insert(ProjectEntity(name = name, description = description))
        }
    }

    fun deleteProject(project: ProjectEntity) {
        viewModelScope.launch {
            repository.delete(project)
        }
    }

    fun updateProgress(project: ProjectEntity, newProgress: Float) {
        viewModelScope.launch {
            repository.update(project.copy(progress = newProgress))
        }
    }

    fun getProjectById(id: Int) = repository.getProjectById(id)
}