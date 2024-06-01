package com.example.forhealth.presentation.registration_module


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.forhealth.data.database.App
import com.example.forhealth.data.models.ProfileItem
import com.example.forhealth.data.repositories.ProfileRepository
import com.example.forhealth.network.MainApiModule
import com.example.forhealth.network.models.ProfileApiItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/*
data class Profile(
    val userName: String="",
    val userPassword: String="",
    val userHeight: String="",
    val userWeight: String="",
    val userGender: String="",
    val userToken: String="",
    val responseStatus: Boolean=true,
)
*/

class RegistrationViewModel(private val profileRepository: ProfileRepository):ViewModel() {

    private val _registrationState = MutableStateFlow<ProfileApiItem>(ProfileApiItem())
    val registrationState = _registrationState.asStateFlow()

    fun updateRegistrationState(
    field: String,
    value: String
    ) {
        _registrationState.update { currentState ->
            when (field) {
                "userName" -> currentState.copy(userName = value)
                "userPassword" -> currentState.copy(userPassword = value)
                "userHeight" -> currentState.copy(userHeight = value)
                "userWeight" -> currentState.copy(userWeight = value)
                "userGender" -> currentState.copy(userGender = value)
                else -> currentState // Если передано неизвестное поле, вернуть текущее состояние
            }
        }
    }

    var loginState by mutableStateOf(true)
    var responseState by mutableStateOf(true)
    var registrationComplete by mutableStateOf(false)

    fun registerProfile() {
        viewModelScope.launch {
            try {
                val response = MainApiModule.retrofitService.registerUserOnServer(_registrationState.value)
                if (response.isSuccessful) {
                    // Получение данных профиля из ответа сервера
                    val profileFromServer = response.body()

                    profileFromServer?.let {
                        val profileForLocalDatabase = ProfileItem(
                            userName = it.userName,
                            userHeight = it.userHeight,
                            userWeight = it.userWeight,
                            userGender = it.userGender,
                            userToken = it.userToken
                        )

                        // Вставка профиля в локальную базу данных
                        profileRepository.insertProfile(profileForLocalDatabase)
                    }

                    _registrationState.update {
                        ProfileApiItem(
                            userName = "",
                            userPassword = "",
                            userHeight = "",
                            userWeight = "",
                            userGender = "",
                            userToken = ""
                        )
                    }
                    responseState = true
                    registrationComplete = true
                } else {
                    responseState = false
                    registrationComplete = false
                }
            } catch (e: Exception) {
                responseState = false
                registrationComplete = false
            }
        }
    }

    fun fetchProfile() {
        viewModelScope.launch {
            try {
                val response = MainApiModule.retrofitService.getUserFromServer(userName = _registrationState.value.userName, userPassword = _registrationState.value.userPassword)
                if (response.isSuccessful) {
                    val profileFromServer = response.body()

                    profileFromServer?.let {
                        val profileForLocalDatabase = ProfileItem(
                            userName = it.userName,
                            userHeight = it.userHeight,
                            userWeight = it.userWeight,
                            userGender = it.userGender,
                            userToken = it.userToken
                        )

                        // Вставка профиля в локальную базу данных
                        profileRepository.insertProfile(profileForLocalDatabase)
                    }

                    _registrationState.update {
                        ProfileApiItem(
                            userName = "",
                            userPassword = "",
                            userHeight = "",
                            userWeight = "",
                            userGender = "",
                            userToken = ""
                        )
                    }
                    responseState = true
                    registrationComplete = true

                } else {
                    responseState = false
                    registrationComplete = false
                }
            } catch (e: Exception) {
                responseState = false
                registrationComplete = false
            }
        }
    }


    companion object{
        val factory: ViewModelProvider.Factory = object: ViewModelProvider.Factory{
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val database = (checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]) as App).profileRepository
                return RegistrationViewModel(database) as T
            }
        }
    }
}