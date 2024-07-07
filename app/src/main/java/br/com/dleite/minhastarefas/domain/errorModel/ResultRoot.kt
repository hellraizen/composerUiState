package br.com.dleite.minhastarefas.domain.errorModel


typealias RootError = Error

sealed interface ResultRoot<out D, out E : RootError> {
    data class Success<out D, out E : RootError>(val data: D) : ResultRoot<D, E>
    data class Error<out D, out E : RootError>(val error: E) : ResultRoot<D, E>
}