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
import kotlinx.coroutines.launch

class RegistrationViewModel(private val profileRepository: ProfileRepository):ViewModel() {

    val profile = profileRepository.getProfile()

    var name:String by mutableStateOf("")
    var password:String by mutableStateOf("")
    var weight:String by mutableStateOf("")
    var height:String by mutableStateOf("")

    fun addProfileToDatabase()
    {
        val profileItem:ProfileItem = ProfileItem(userName = name, userHeight = height, userWeight = weight)
        viewModelScope.launch {
            profileRepository.insertProfile(profileItem)}
    }

    fun checkProfileInDatabase(profileItem: ProfileItem?):Boolean
    {
        if(profileItem!=null && profileItem.userName == name)
        {
            name=""
            password=""
            return true
        }
        return false
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