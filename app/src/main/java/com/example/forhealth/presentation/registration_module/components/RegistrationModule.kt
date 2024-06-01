package com.example.forhealth.presentation.registration_module.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.forhealth.R
import com.example.forhealth.data.models.ProfileItem
import com.example.forhealth.navigation.Screen

import com.example.forhealth.presentation.registration_module.RegistrationViewModel

@Preview
@Composable
fun RegistrationPreview()
{
    //GreetingCard()
}


@Composable
fun GreetingModule(navController: NavHostController, modifier: Modifier=Modifier)
{
    Scaffold(
        bottomBar = {
            Box(modifier = modifier.fillMaxWidth())
            {
                TextButton(onClick = { navController.navigate(Screen.Profile.route) },modifier=modifier.align(alignment = Alignment.Center)){
                    Text(text = "пропустить")
                }
            } },
    ) { innerPadding ->
        Column(modifier = modifier.padding(innerPadding)) {
            Spacer(modifier = modifier.weight(0.2f))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = modifier
                    .weight(0.8f)
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(R.drawable.greeting_image),
                    contentDescription = null
                )
                Spacer(modifier = modifier.height(20.dp))
                Text(
                    fontSize = 32.sp,
                    fontFamily = FontFamily.SansSerif,
                    text = "Добро пожаловать!",
                )
                Spacer(modifier = modifier.height(10.dp))
                Text(fontSize = 20.sp, fontFamily = FontFamily.SansSerif, text = "давайте начнем")
                Spacer(modifier = modifier.height(20.dp))
                Button(onClick = { navController.navigate(Screen.Login.route) },modifier.width(180.dp)) {
                    Text(text = "Войти")
                }
                Spacer(modifier = modifier.height(5.dp))
                FilledTonalButton(onClick = { navController.navigate(Screen.Registration.route) },modifier.width(180.dp)) {
                    Text(text = "Регистрация")
                }
            }
        }
    }
}



@Composable
fun LoginModule(navController: NavHostController,modifier: Modifier=Modifier, registrationViewModel: RegistrationViewModel= viewModel(factory= RegistrationViewModel.factory))
{
    val profile = registrationViewModel.profile.collectAsState(initial = ProfileItem(id = 0, userName = "", userWeight = "", userHeight = ""))

    Scaffold(
        bottomBar = {
            Box(modifier = modifier.fillMaxWidth())
            {
                TextButton(onClick = { navController.navigate(Screen.Profile.route) },modifier=modifier.align(alignment = Alignment.Center)){
                    Text(text = "пропустить")
                }
            } },
    ) { innerPadding ->
        Column(modifier = modifier.padding(innerPadding)) {
            Box(modifier = modifier.weight(0.2f))
            {
                IconButton(onClick = { navController.popBackStack() }){
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Return Icon")
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = modifier
                    .weight(0.8f)
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(R.drawable.login_image),
                    contentDescription = null
                )
                Spacer(modifier = modifier.height(20.dp))
                Text(fontSize = 32.sp, fontFamily = FontFamily.SansSerif, text = "Вход")
                Spacer(modifier = modifier.height(20.dp))
                OutlinedTextField(value = registrationViewModel.name,
                    onValueChange = { registrationViewModel.name=it },
                    leadingIcon = { Icon(Icons.Filled.Person, contentDescription = "Login Icon") },
                    label = { Text(text = "Логин") },
                    singleLine = true,)
                Spacer(modifier = modifier.height(5.dp))
                OutlinedTextField(value = registrationViewModel.password,
                    onValueChange = { registrationViewModel.password=it },
                    leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = "Login Icon") },
                    label = { Text(text = "Пароль") },
                    singleLine = true,)
                Spacer(modifier = modifier.height(10.dp))
                Button(onClick = {  if(registrationViewModel.checkProfileInDatabase(profile.value)) {navController.navigate(Screen.Profile.route) }},modifier= modifier.width(180.dp)) {
                    Text(text = "Вход")
                }
            }
        }
    }
}



@Composable
fun RegistrationModule(navController: NavHostController,modifier: Modifier=Modifier,registrationViewModel: RegistrationViewModel= viewModel(factory= RegistrationViewModel.factory))
{
    Scaffold(
        bottomBar = {
            Box(modifier = modifier.fillMaxWidth())
            {
                TextButton(onClick = { navController.navigate(Screen.Profile.route) },modifier=modifier.align(alignment = Alignment.Center)){
                    Text(text = "пропустить")
                }
            } },
    ) { innerPadding ->

        Column(modifier = modifier.padding(innerPadding)) {
            Box(modifier = modifier.weight(0.2f))
            {
                IconButton(onClick = { navController.popBackStack() }){
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Return Icon")
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = modifier
                    .weight(0.8f)
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(R.drawable.registration_image),
                    contentDescription = null
                )
                Spacer(modifier = modifier.height(20.dp))
                Text(fontSize = 32.sp, fontFamily = FontFamily.SansSerif, text = "Регистрация")
                Spacer(modifier = modifier.height(20.dp))
                OutlinedTextField(value = registrationViewModel.name,
                    onValueChange = { registrationViewModel.name=it },
                    leadingIcon = { Icon(Icons.Filled.Person, contentDescription = "Login Icon") },
                    label = { Text(text = "Логин") },
                    singleLine = true,)
                Spacer(modifier = modifier.height(5.dp))
                OutlinedTextField(value = registrationViewModel.password,
                    onValueChange = { registrationViewModel.password=it },
                    leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = "Login Icon") },
                    label = { Text(text = "Пароль") },
                    singleLine = true,)
                Spacer(modifier = modifier.height(5.dp))
                OutlinedTextField(value = registrationViewModel.height,
                    onValueChange = { registrationViewModel.height=it },
                    leadingIcon = { Icon(Icons.Filled.KeyboardArrowUp, contentDescription = "Login Icon") },
                    label = { Text(text = "Рост") },
                    singleLine = true,)
                Spacer(modifier = modifier.height(5.dp))
                OutlinedTextField(value = registrationViewModel.weight,
                    onValueChange = { registrationViewModel.weight=it },
                    leadingIcon = { Icon(Icons.Filled.FavoriteBorder, contentDescription = "Login Icon") },
                    label = { Text(text = "Вес") },
                    singleLine = true,)
                Spacer(modifier = modifier.height(5.dp))
                Button(onClick = {  registrationViewModel.addProfileToDatabase()
                                    navController.navigate(Screen.Profile.route) },modifier= modifier.width(180.dp)) {
                    Text(text = "Регистрация")
                }
            }
        }
    }
}