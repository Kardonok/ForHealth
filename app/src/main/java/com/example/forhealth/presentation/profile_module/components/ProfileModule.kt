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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.forhealth.R
import com.example.forhealth.data.models.ProfileItem
import com.example.forhealth.presentation.profile_module.ProfileViewModel

@Preview
@Composable
fun ProfileModulePreview()
{
    ProfileModule()
}

@Composable
fun ProfileModule(modifier:Modifier = Modifier, profileViewModel: ProfileViewModel = viewModel(factory=ProfileViewModel.factory))
{
    val profile = profileViewModel.profile.collectAsState(initial = ProfileItem(id = 0, userName = "Ginger", userWeight = "100", userHeight = "180"))

    if(profileViewModel.editCardIsOpen)
    {
        EditCard(profileItem = profile.value, profileViewModel = profileViewModel)
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        UserCard(profileItem = profile.value, editCardIsOpen = profileViewModel.editCardIsOpen)
        SettingsCard()
        TextButton(onClick = { /*TODO*/ }) {
            Text(text = "выйти из аккаунта")
        }
        TextButton(onClick = { /*TODO*/ }) {
            Text(text = "удалить аккаунт",color=Color.Red)
        }
    }
}

@Composable
fun UserCard(modifier:Modifier = Modifier, profileItem: ProfileItem, editCardIsOpen:Boolean)
{

    Card(modifier= Modifier
        .height(180.dp))
    //.width(300.dp))
    {
        Row(verticalAlignment = Alignment.CenterVertically,modifier= Modifier
            .background(color = Color.Magenta)
            .padding(start = 16.dp, end = 16.dp)) {
            IconButton(onClick = { !editCardIsOpen },modifier= Modifier
                .width(32.dp)
                .height(32.dp)) {
                Icon(Icons.Filled.Create, contentDescription = "Edit Icon")
            }
            Box(modifier = Modifier
                .weight(1f)) {
                Text(
                    text = profileItem.id.toString(),
                    color = Color.White,
                    modifier=Modifier.align(alignment = Alignment.Center)
                )
            }
            Spacer(modifier = Modifier
                .width(32.dp)
                .height(32.dp))
        }
        Row(horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
            ,modifier= Modifier
                .weight(1f)
                .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
                .fillMaxWidth()) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = null,
                modifier= Modifier
                    .clip(RoundedCornerShape(75.dp)))
            Spacer(modifier = Modifier.width(8.dp))
            Column(verticalArrangement = Arrangement.SpaceBetween
                ,modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)){
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .background(color = Color.White)) {
                    Text(
                        text = profileItem.userName,
                        fontSize = 20.sp,
                        modifier =Modifier
                            .padding(start=8.dp)
                    )
                }
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .background(color = Color.White)) {
                    Text(
                        text = "Рост: ${profileItem.userHeight} см",
                        fontSize = 20.sp,
                        modifier =Modifier
                            .padding(start=8.dp)
                    )
                }
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .background(color = Color.White)) {
                    Text(
                        text = "Вес: ${profileItem.userWeight} кг",
                        fontSize = 20.sp,
                        modifier =Modifier
                            .padding(start=8.dp)
                    )
                }

            }
        }
    }
}

@Composable
fun SettingsCard(modifier:Modifier = Modifier)
{
    Card(modifier) {
        Column(modifier = Modifier
            .padding(16.dp)) {
            Row {
                Box(modifier = Modifier
                    .clip(RoundedCornerShape(50.dp))
                    .background(color = Color.White)) {
                    Text(
                        text = "Настройки",
                        modifier =Modifier
                            .padding(start=8.dp,end=8.dp)
                    )
                }
                Spacer(modifier = Modifier.weight(0.5f))
            }

        }
    }
}


@Composable
fun EditCard(profileItem: ProfileItem, profileViewModel: ProfileViewModel) {
    profileViewModel.name = profileItem.userName
    profileViewModel.weight = profileItem.userWeight
    profileViewModel.height = profileItem.userHeight
    Dialog(onDismissRequest = {}) {
        Card(modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text("Редактировать профиль")
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(value = profileViewModel.name,
                    singleLine = true,
                    label = {Text(text="Имя")},
                    onValueChange = {word->profileViewModel.OnFieldChanged("name",word)},
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),)
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(value = profileViewModel.weight,
                    singleLine = true,
                    label = {Text(text="Вес")},
                    onValueChange = {word->profileViewModel.OnFieldChanged("weight",word)},
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),)
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(value = profileViewModel.height,
                    singleLine = true,
                    label = {Text(text="Рост")},
                    onValueChange = {word->profileViewModel.OnFieldChanged("height",word)},
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),)
                Spacer(Modifier.height(8.dp))
                Button(onClick = { profileViewModel.updateProfileInDatabase(profileItem) }) {
                    Text("Сохранить")
                }
            }
        }
    }
}