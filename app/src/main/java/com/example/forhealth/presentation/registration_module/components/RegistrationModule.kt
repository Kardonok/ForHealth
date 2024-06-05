package com.example.forhealth.presentation.registration_module.components


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.forhealth.R
import com.example.forhealth.navigation.Screen
import com.example.forhealth.presentation.registration_module.RegistrationViewModel

@Preview
@Composable
fun RegistrationPreview()
{
    //GreetingCard()
}
@Composable
fun RegistrationModule(navController: NavHostController,modifier: Modifier=Modifier,registrationViewModel: RegistrationViewModel= viewModel(factory= RegistrationViewModel.factory)) {

    val registrationState by registrationViewModel.registrationState.collectAsState()

    if(registrationViewModel.registrationComplete)
    {
        navController.navigate(Screen.Profile.route)
    }

    val rotation = remember { androidx.compose.animation.core.Animatable(initialValue = 0f) }

    LaunchedEffect(registrationViewModel.loginState) {
        rotation.animateTo(
            targetValue = if (registrationViewModel.loginState) 0f else 180f,
            animationSpec = tween(durationMillis = 500)
        )
    }

    Scaffold(
        bottomBar = {
            Box(modifier = modifier.fillMaxWidth())
            {
                TextButton(
                    onClick = { navController.navigate(Screen.Profile.route) },
                    modifier = modifier.align(alignment = Alignment.Center)
                ) {
                    Text(text = "пропустить")
                }
            }
        }
    ) { innerPadding ->

        Row(modifier = modifier
            .padding(innerPadding)
            .fillMaxSize())
        {
            Spacer(modifier = Modifier.weight(0.1f))

            Column(modifier = Modifier
                .fillMaxSize()
                .weight(0.8f)
                .animateContentSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,) {
                Image(
                    modifier = Modifier.graphicsLayer {
                        rotationY = rotation.value
                    },
                    painter = painterResource(if (registrationViewModel.loginState) R.drawable.login_image else R.drawable.registration_image),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Box(modifier = Modifier
                        .weight(0.5f)
                        .background(
                            if (registrationViewModel.loginState) Color(0xFF68B69A) else Color(
                                0xFFD9D9D9
                            )
                        )
                        .clickable {
                            registrationViewModel.loginState = true
                            registrationViewModel.responseState = true
                        })
                    {
                        Text(text = "Вход",
                            color = if(registrationViewModel.loginState) Color.White else Color.Black,
                            fontFamily = FontFamily.SansSerif,
                            fontSize = 20.sp,
                            modifier = Modifier
                                .padding(top = 8.dp, bottom = 8.dp)
                                .align(alignment = Alignment.Center))
                    }
                    Box(modifier = Modifier
                        .weight(0.5f)
                        .background(
                            if (registrationViewModel.loginState) Color(0xFFD9D9D9) else Color(
                                0xFF68B69A
                            )
                        )
                        .clickable {
                            registrationViewModel.loginState = false
                            registrationViewModel.responseState = true
                        })
                    {
                        Text(text = "Регистрация",
                            color = if(registrationViewModel.loginState) Color.Black else Color.White,
                            fontFamily = FontFamily.SansSerif,
                            fontSize = 20.sp,
                            modifier = Modifier
                                .padding(top = 8.dp, bottom = 8.dp)
                                .align(alignment = Alignment.Center))
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                if (!registrationViewModel.responseState)
                {
                    Box(modifier=Modifier.fillMaxWidth())
                    {
                        Text(modifier=Modifier.align(alignment = Alignment.Center),color = Color.Red,text = if (registrationViewModel.loginState) "Ошибка входа" else "Ошибка регистрации")
                    }
                }
                OutlinedTextField(value = registrationState.userName,
                    onValueChange = { registrationViewModel.updateRegistrationState("userName",it) },
                    leadingIcon = { Icon(Icons.Filled.Person, contentDescription = "Login Icon") },
                    label = { Text(text = "Логин") },
                    singleLine = true,)
                OutlinedTextField(value = registrationState.userPassword,
                    onValueChange = { registrationViewModel.updateRegistrationState("userPassword",it) },
                    leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = "Password Icon") },
                    label = { Text(text = "Пароль") },
                    singleLine = true,)
                AnimatedVisibility(visible = !registrationViewModel.loginState,
                    enter = fadeIn()+ expandVertically(),
                    exit = shrinkVertically() + fadeOut()) {
                    Column( modifier = Modifier.animateContentSize()) {
                        OutlinedTextField(
                            value = registrationState.userHeight,
                            onValueChange = { registrationViewModel.updateRegistrationState("userHeight", it) },
                            leadingIcon = { Icon(Icons.Filled.KeyboardArrowUp, contentDescription = "Login Icon") },
                            label = { Text(text = "Рост") },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                        )

                        OutlinedTextField(
                            value = registrationState.userWeight,
                            onValueChange = { registrationViewModel.updateRegistrationState("userWeight", it) },
                            leadingIcon = { Icon(Icons.Filled.FavoriteBorder, contentDescription = "Login Icon") },
                            label = { Text(text = "Вес") },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { if (registrationViewModel.loginState) registrationViewModel.fetchProfile() else registrationViewModel.registerProfile() },
                    colors = ButtonDefaults.buttonColors(Color(0xFF68B69A))
                ) {
                    Text(
                        text = if (registrationViewModel.loginState) "Вход" else "Регистрация",
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.weight(0.1f))
        }
    }
}