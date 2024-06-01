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
import kotlinx.coroutines.launch

//INSERT INTO profile_list VALUES("Дмитрий", "168", "68", "1")
class ProfileViewModel(private val profileRepository: ProfileRepository):ViewModel() {
    val profile = profileRepository.getProfile()

    var editCardIsOpen:Boolean by mutableStateOf(false)

    var name:String by mutableStateOf("")
    var weight:String by mutableStateOf("")
    var height:String by mutableStateOf("")

    fun OnFieldChanged(field: String, value: String) {
        when (field) {
            "name" ->  if(CheckWord(value,10))name = value
            "weight" -> if(CheckWord(value,3))weight = value
            "height" -> if(CheckWord(value,3))height = value
        }
    }

    fun updateProfileInDatabase(profileItem: ProfileItem)
    {
        viewModelScope.launch {
            val updateProfile = profileItem.copy(userName = name, userHeight = height, userWeight = weight)
            profileRepository.updateProfile(updateProfile)
        }
        editCardIsOpen = false
    }

    fun deleteProfileFromDatabase(profileItem: ProfileItem?)
    {
        if(profileItem!=null)
        {
            viewModelScope.launch {
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
