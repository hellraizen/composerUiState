package br.com.dleite.minhastarefas.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.dleite.minhastarefas.authentication.FirebaseAuthRepository
import br.com.dleite.minhastarefas.repositories.TasksRepository
import br.com.dleite.minhastarefas.repositories.toTask
import br.com.dleite.minhastarefas.ui.states.TasksListUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TasksListViewModel(
    private val repository: TasksRepository,
    private val firebaseAuthRepository: FirebaseAuthRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<TasksListUiState> =
        MutableStateFlow(TasksListUiState())

    val uiState
        get() = _uiState
            .combine(repository.tasks) { uiState, tasks ->
                uiState.copy(tasks = tasks.map { it.toTask() })
            }.combine(firebaseAuthRepository.currentUser) { uiState, authResult ->
                uiState.copy(user = authResult.currentUser?.email)
            }

    init {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(onTaskDoneChange = { task ->
                    viewModelScope.launch {
                        repository.toggleIsDone(task)
                    }
                })
            }
        }
    }

    fun signOut() {
        firebaseAuthRepository.signOut()
    }

}