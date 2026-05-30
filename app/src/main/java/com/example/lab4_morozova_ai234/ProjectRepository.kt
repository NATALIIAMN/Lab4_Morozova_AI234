package com.example.lab4_morozova_ai234

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class ProjectRepository(private val projectDao: ProjectDao) {

    val allProjects: Flow<List<ProjectEntity>> = projectDao.getAllProjects()

    fun getProjectById(id: Int): Flow<ProjectEntity?> {
        return projectDao.getProjectById(id)
    }

    suspend fun insert(project: ProjectEntity) {
        withContext(Dispatchers.IO) {
            projectDao.insertProject(project)
        }
    }

    suspend fun update(project: ProjectEntity) {
        withContext(Dispatchers.IO) {
            projectDao.updateProject(project)
        }
    }

    suspend fun delete(project: ProjectEntity) {
        withContext(Dispatchers.IO) {
            projectDao.deleteProject(project)
        }
    }
}