package br.com.dleite.minhastarefas.ui.states

import br.com.dleite.minhastarefas.models.Task

data class TasksListUiState(
    val tasks: List<Task> = emptyList(),
    val onTaskDoneChange: (Task) -> Unit = {},
    val user: String? = null
)
