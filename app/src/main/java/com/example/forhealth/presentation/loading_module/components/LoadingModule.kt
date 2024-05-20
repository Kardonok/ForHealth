package com.example.forhealth.presentation.loading_module.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.forhealth.R
import com.example.forhealth.data.repositories.DataStoreRepository
import com.example.forhealth.navigation.Screen
import kotlinx.coroutines.delay


@Preview(showBackground = true)
@Composable
fun LoadingModulePreview(modifier:Modifier=Modifier)
{
    //LoadingModule()
    Box(modifier = modifier.fillMaxSize())
    {
        Text(text = "Привет!", fontSize = 40.sp, fontStyle = FontStyle.Italic,modifier = modifier.align(alignment = Alignment.Center))
        //Image(modifier = modifier.align(alignment = Alignment.Center),painter = painterResource(id = R.drawable.loading_image), contentDescription = "Loading")
    }
}

@Composable
fun LoadingModule(navController: NavHostController, dataStoreRepository: DataStoreRepository, modifier: Modifier = Modifier)
{
    val isFirstLaunch by dataStoreRepository.isFirstLaunch.collectAsState(initial = null)

    if (isFirstLaunch != null) {
        LaunchedEffect(Unit) {
            if (isFirstLaunch == true) {
                navController.navigate(Screen.Greeting.route){
                    popUpTo(Screen.Loading.route) { inclusive = true }
                }
                dataStoreRepository.saveLaunchPreference(false)
            } else {
                navController.navigate(Screen.Profile.route){
                    popUpTo(Screen.Loading.route) { inclusive = true }
                }
            }
        }
    }
    Box(modifier = modifier.fillMaxSize())
    {
        Text(text = "Привет!", fontSize = 40.sp, fontStyle = FontStyle.Italic,modifier = modifier.align(alignment = Alignment.Center))
        //Image(modifier = modifier.align(alignment = Alignment.Center),painter = painterResource(id = R.drawable.loading_image), contentDescription = "Loading")
    }
}