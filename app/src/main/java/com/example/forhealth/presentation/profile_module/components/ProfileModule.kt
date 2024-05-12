package com.example.forhealth.presentation.profile_module.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.forhealth.R
import com.example.forhealth.data.models.ProfileItem
import com.example.forhealth.navigation.NavBar
import com.example.forhealth.navigation.Screen
import com.example.forhealth.presentation.profile_module.ProfileViewModel

@Preview
@Composable
fun ProfileModulePreview()
{
    SettingsCard()
}

@Composable
fun ProfileModule(navController: NavHostController, modifier:Modifier = Modifier, profileViewModel: ProfileViewModel = viewModel(factory=ProfileViewModel.factory))
{
    val profile = profileViewModel.profile.collectAsState(initial = ProfileItem(id = 0, userName = "", userWeight = "", userHeight = ""))

    if(profileViewModel.editCardIsOpen)
    {
        EditCard(profileItem = profile.value, profileViewModel = profileViewModel, modifier = modifier)
    }

    Scaffold(
        bottomBar = {
            NavBar(navController = navController,modifier=modifier)
        }
    ) { innerPadding ->
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier
            .padding(innerPadding)
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)) {
            if(profile.value != null)
            {
                UserCard(profileItem = profile.value, profileViewModel = profileViewModel, modifier = modifier)
            }
            else
            {
                DefaultUserCard(navController=navController)
            }
            Spacer(modifier = modifier.height(16.dp))
            SettingsCard(modifier = modifier.weight(1f))
            TextButton(onClick = { /*TODO*/ }, modifier = modifier.height(40.dp)) {
                Text(text = "выйти из аккаунта", fontSize = 16.sp)
            }
            TextButton(onClick = { profileViewModel.deleteProfileFromDatabase(profile.value) }, modifier = modifier.height(40.dp)) {
                Text(text = "удалить аккаунт",color=Color.Red, fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun UserCard(modifier:Modifier = Modifier, profileItem: ProfileItem, profileViewModel: ProfileViewModel)
{

    Card(modifier= modifier
        .height(180.dp))
    {
        Row(verticalAlignment = Alignment.CenterVertically,modifier= modifier
            .background(color = Color.Gray)
            .padding(start = 16.dp, end = 16.dp)) {
            IconButton(onClick = { profileViewModel.editCardIsOpen = true },modifier = modifier
                .width(32.dp)
                .height(32.dp)) {
                Icon(Icons.Filled.Create, contentDescription = "Edit Icon")
            }
            Box(modifier = modifier
                .weight(1f)) {
                Text(
                    text = "id: ${profileItem.id.toString()}",
                    fontFamily = FontFamily.SansSerif,
                    color = Color.White,
                    modifier=modifier.align(alignment = Alignment.Center)
                )
            }
            Spacer(modifier = modifier
                .width(32.dp)
                .height(32.dp))
        }
        Row(horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
            ,modifier= modifier
                .weight(1f)
                .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
                .fillMaxWidth()) {
            Image(
                painter = painterResource(id = R.drawable.loading_image),
                contentDescription = null,
                modifier= modifier
                    .clip(RoundedCornerShape(75.dp)))
            Spacer(modifier = modifier.width(8.dp))
            Column(verticalArrangement = Arrangement.SpaceBetween
                ,modifier = modifier
                    .fillMaxHeight()
                    .weight(1f)){
                TextCard(text = profileItem.userName, modifier = modifier.padding(end = 8.dp))
                TextCard(text = "Рост: ${profileItem.userHeight} см", modifier = modifier.padding(start = 8.dp))
                TextCard(text = "Вес: ${profileItem.userWeight} кг", modifier = modifier.padding(end = 8.dp))
            }
        }
    }
}

@Composable
fun TextCard(modifier:Modifier=Modifier, text:String)
{
    Box(modifier = modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(50.dp))
        .background(color = Color.White)) {
        Text(
            text = text,
            fontSize = 20.sp,
            fontFamily = FontFamily.SansSerif,
            modifier =Modifier
                .padding(start=8.dp)
        )
    }
}

@Composable
fun SettingsCard(modifier:Modifier = Modifier)
{
    Card(modifier=modifier) {
        Column(modifier = modifier
            .padding(16.dp)) {
            Row {
                Box(modifier = modifier
                    .clip(RoundedCornerShape(50.dp))
                    .background(color = Color.White)) {
                    Text(
                        text = "Настройки",
                        fontSize = 20.sp,
                        fontFamily = FontFamily.SansSerif,
                        textAlign = TextAlign.Center,
                        modifier =modifier
                            .padding(start=8.dp,end=8.dp)
                            .fillMaxWidth()
                    )
                }
                Spacer(modifier = modifier.weight(0.5f))
            }

        }
    }
}


@Composable
fun EditCard(profileItem: ProfileItem, profileViewModel: ProfileViewModel,modifier: Modifier=Modifier) {
    profileViewModel.name = profileItem.userName
    profileViewModel.weight = profileItem.userWeight
    profileViewModel.height = profileItem.userHeight
    Dialog(onDismissRequest = {}) {
        Card(modifier = modifier.padding(16.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text("Редактировать профиль",fontFamily = FontFamily.SansSerif)
                Spacer(modifier.height(8.dp))
                OutlinedTextField(value = profileViewModel.name,
                    singleLine = true,
                    label = {Text(text="Имя")},
                    onValueChange = {word->profileViewModel.OnFieldChanged("name",word)},
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),)
                Spacer(modifier.height(8.dp))
                OutlinedTextField(value = profileViewModel.weight,
                    singleLine = true,
                    label = {Text(text="Вес")},
                    onValueChange = {word->profileViewModel.OnFieldChanged("weight",word)},
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),)
                Spacer(modifier.height(8.dp))
                OutlinedTextField(value = profileViewModel.height,
                    singleLine = true,
                    label = {Text(text="Рост")},
                    onValueChange = {word->profileViewModel.OnFieldChanged("height",word)},
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),)
                Spacer(modifier.height(8.dp))
                TextButton(onClick = { profileViewModel.updateProfileInDatabase(profileItem)}, modifier = modifier.align(alignment = Alignment.End)) {
                    Text(text = "ОK",fontFamily = FontFamily.SansSerif,)
                }
            }
        }
    }
}

@Composable
fun DefaultUserCard(modifier:Modifier = Modifier, navController: NavHostController)
{
    Card(modifier= modifier
        .height(180.dp))
    {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = modifier
                .height(32.dp)
                .fillMaxWidth()
                .background(color = Color.Gray))
            Row(verticalAlignment = Alignment.CenterVertically
                ,modifier= modifier
                    .weight(1f)
                    .padding(16.dp)
                    .fillMaxWidth()){
                Image(
                    painter = painterResource(id = R.drawable.loading_image),
                    contentDescription = null,
                    modifier= modifier
                        .clip(RoundedCornerShape(75.dp)))
                Box(modifier = modifier.weight(1f))
                {
                    TextButton(onClick = { navController.navigate(Screen.Greeting.rout) },modifier.align(alignment = Alignment.Center)) {
                        Icon(Icons.Filled.Create, contentDescription = "Edit Icon")
                        Text(text = "Создать аккаунт")
                    }
                }

            }
        }
    }
}

