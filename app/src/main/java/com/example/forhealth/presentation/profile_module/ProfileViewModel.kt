package com.example.forhealth.presentation.profile_module

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

class ProfileViewModel(private val profileRepository: ProfileRepository):ViewModel() {
    val profile = profileRepository.getProfile()

    var editCardIsOpen:Boolean by mutableStateOf(false)

    private val _profileState = MutableStateFlow<ProfileApiItem>(ProfileApiItem())
    val profileState = _profileState.asStateFlow()

    fun updateProfileState(
        field: String,
        value: String
    ) {
        _profileState.update { currentState ->
            when (field) {
                "userName" -> currentState.copy(userName = value)
                "userPassword" -> currentState.copy(userPassword = value)
                "userHeight" -> currentState.copy(userHeight = value)
                "userWeight" -> currentState.copy(userWeight = value)
                "userGender" -> currentState.copy(userGender = value)
                "userToken" -> currentState.copy(userToken = value)
                else -> currentState // Если передано неизвестное поле, вернуть текущее состояние
            }
        }
    }

    fun updateProfile(token:String)
    {
        viewModelScope.launch {
            try {
                val response = MainApiModule.retrofitService.changeUserOnServer(_profileState.value)
                if (response.isSuccessful)
                {
                    val profileFromServer = response.body()

                    profileFromServer?.let {
                        val profileForLocalDatabase = ProfileItem(
                            userName = it.userName,
                            userHeight = it.userHeight,
                            userWeight = it.userWeight,
                            userGender = it.userGender,
                            userToken = token
                        )

                        // Вставка профиля в локальную базу данных
                        profileRepository.updateProfile(profileForLocalDatabase)
                    }
                    _profileState.update {
                        ProfileApiItem(
                            userName = "",
                            userPassword = "",
                            userHeight = "",
                            userWeight = "",
                            userGender = "",
                            userToken = ""
                        )
                    }
                }
                else
                {

                }
            }
            catch (e:Exception)
            {

            }
        }
        editCardIsOpen=false
    }

    fun deleteProfile(name:String,token: String)
    {
        viewModelScope.launch {
            try {
                val response = MainApiModule.retrofitService.deleteUserFromServer(name,token)
                if (response.isSuccessful)
                {
                    val profileForLocalDatabase = ProfileItem(
                        userName = _profileState.value.userName,
                        userHeight = _profileState.value.userHeight,
                        userWeight = _profileState.value.userWeight,
                        userGender = _profileState.value.userGender,
                        userToken = _profileState.value.userToken
                    )
                    profileRepository.deleteProfile(profileForLocalDatabase)
                    _profileState.update {
                        ProfileApiItem(
                            userName = "",
                            userPassword = "",
                            userHeight = "",
                            userWeight = "",
                            userGender = "",
                            userToken = ""
                        )
                    }
                }
                else
                {

                }
            }
            catch (e:Exception)
            {

            }
        }
    }

    fun deleteProfileFromLocalDatabase(profileItem: ProfileItem?)
    {
        if(profileItem!=null)
        {
            viewModelScope.launch{
                profileRepository.deleteProfile(profileItem)
            }
        }
    }

    fun CheckWord(value: String,maxLength:Int):Boolean
    {
        if(value.length<=maxLength)
        {
            return true
        }
        else
        {
            return false
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
                return ProfileViewModel(database) as T
            }
        }
    }
}
