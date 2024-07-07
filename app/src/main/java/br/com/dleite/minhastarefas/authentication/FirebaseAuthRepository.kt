package br.com.dleite.minhastarefas.authentication

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.tasks.await
import br.com.dleite.minhastarefas.domain.errorModel.DataError
import br.com.dleite.minhastarefas.domain.errorModel.ResultRoot

data class AuthResult(
    val currentUser: FirebaseUser? = null,
    val isInitLoading: Boolean = true
)

class FirebaseAuthRepository(
    private val firebaseAuth: FirebaseAuth
) {

    private val _currentUser = MutableStateFlow(AuthResult())
    val currentUser = _currentUser.asStateFlow()

    init {
        firebaseAuth.addAuthStateListener { firebaseAuth ->
            _currentUser.update {
                it.copy(
                    currentUser = firebaseAuth.currentUser,
                    isInitLoading = false
                )
            }
        }
    }

    suspend fun signUp(email: String, password: String): ResultRoot<AuthResult, DataError> {
        return try {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .await()
            ResultRoot.Success(AuthResult(currentUser = firebaseAuth.currentUser))
        } catch (e: FirebaseAuthException) {
            when (e.errorCode) {
                "ERROR_EMAIL_ALREADY_IN_USE" -> ResultRoot.Error(DataError.Network.ERROR_EMAIL_ALREADY_IN_USE)
                else -> ResultRoot.Error(DataError.Network.UNKNOWN)
            }
        }
    }

    suspend fun signIn(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .await()
    }

    fun signOut() {
        firebaseAuth.signOut()
    }


}