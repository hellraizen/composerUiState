package br.com.dleite.minhastarefas.di

import android.net.ConnectivityManager
import androidx.room.Room
import br.com.dleite.minhastarefas.authentication.FirebaseAuthRepository
import br.com.dleite.minhastarefas.connection.NetworkConnectionViewModel
import br.com.dleite.minhastarefas.database.MinhasTarefasDatabase
import br.com.dleite.minhastarefas.domain.errorModel.UserDataValidator
import br.com.dleite.minhastarefas.repositories.TasksRepository
import br.com.dleite.minhastarefas.repositories.UsersRepository
import br.com.dleite.minhastarefas.ui.viewmodels.AppViewModel
import br.com.dleite.minhastarefas.ui.viewmodels.SignInViewModel
import br.com.dleite.minhastarefas.ui.viewmodels.SignUpViewModel
import br.com.dleite.minhastarefas.ui.viewmodels.TaskFormViewModel
import br.com.dleite.minhastarefas.ui.viewmodels.TasksListViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val androidModule = module {
    single {
        androidContext()
            .getSystemService(ConnectivityManager::class.java)
                as ConnectivityManager
    }
}

val appModule = module {
    viewModelOf(::TaskFormViewModel)
    viewModelOf(::TasksListViewModel)
    viewModelOf(::SignInViewModel)
    viewModelOf(::SignUpViewModel)
    viewModelOf(::AppViewModel)
    viewModelOf(::NetworkConnectionViewModel)
}

val storageModule = module {
    singleOf(::TasksRepository)
    singleOf(::UsersRepository)
    singleOf(::FirebaseAuthRepository)
    singleOf(::UserDataValidator)
    single {
        Room.databaseBuilder(
            androidContext(),
            MinhasTarefasDatabase::class.java, "minhas-tarefas.db"
        ).build()
    }
    single {
        get<MinhasTarefasDatabase>().taskDao()
    }
}

val firebaseModule = module {
    single {
        Firebase.auth
    }
}