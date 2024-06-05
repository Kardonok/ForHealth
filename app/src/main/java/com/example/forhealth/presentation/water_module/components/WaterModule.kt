package com.example.forhealth.presentation.water_module.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.forhealth.R
import com.example.forhealth.data.models.WaterItem
import com.example.forhealth.navigation.NavBar
import com.example.forhealth.presentation.water_module.WaterViewModel
import kotlin.math.sin

@Preview(showBackground = true)
@Composable
fun WaterModulePreview()
{
    //WaterModule()
}

@Composable
fun WaterModule(waterViewModel: WaterViewModel= viewModel(factory = WaterViewModel.factory), navController: NavHostController,modifier: Modifier = Modifier)
{
    val waterState by waterViewModel.waterState.collectAsState()

    if(waterState.addWaterCardIsOpen)
    {
        ChooseWaterVolumeCard(waterViewModel = waterViewModel)
    }


    Scaffold(
        bottomBar = {
            NavBar(navController = navController)
        }
    ) { innerPadding ->
        Column(modifier = modifier.padding(innerPadding)) {
            Drop(
                usedWaterAmount = waterState.usedWaterAmount,
                onAddWaterClick = { waterViewModel.waterCardIsOpen(true) },
                totalWaterAmount = waterState.totalWaterAmount,
                modifier = modifier
                    .weight(0.5f)
                    .padding(16.dp)
            )
            RecordListCard(
                waterViewModel = waterViewModel,
                modifier = modifier
                    .weight(0.5f)
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun RecordListCard(waterViewModel: WaterViewModel,modifier:Modifier=Modifier) {
    val waterItemList = waterViewModel.waterItemList.collectAsState(initial = emptyList())
    Column(modifier = modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color(0xFF3F81FF),
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                )
                .padding(vertical = 8.dp) // Добавляем вертикальные отступы
        ) {
            Text(
                text = "Записи",
                modifier = Modifier.align(Alignment.Center),
                color = Color.White, // Задаем цвет текста белым для лучшего контраста
                fontFamily = FontFamily.SansSerif,
                fontSize = 20.sp
            )
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(16.dp)
            .background(color = Color.White))
        Box(modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color(0xFFCEDFFF),
                shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
            )
            .padding(vertical = 16.dp) // Добавляем вертикальные отступы)
        )
        {
            LazyColumn{
                items(waterItemList.value){ waterItem ->
                    SingleRecordCard(waterItem=waterItem,waterViewModel=waterViewModel)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun SingleRecordCard(waterItem:WaterItem,waterViewModel: WaterViewModel,modifier:Modifier=Modifier)
{
    Row(verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.SpaceBetween,modifier = modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Spacer(modifier = modifier.width(16.dp))
            Icon(painterResource(id = R.drawable.cup), contentDescription = null)
            Spacer(modifier = modifier.width(8.dp))
            Text(text = waterViewModel.timeFormatter(waterItem.drinkingTime))
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "${waterItem.waterAmount.toString()} ml")
            Spacer(modifier = modifier.width(8.dp))
            Icon(Icons.Filled.Clear, contentDescription = null,modifier=modifier.clickable { waterViewModel.deleteFromDatabase(waterItem) })
            Spacer(modifier = modifier.width(16.dp))
        }
    }
}

@Composable
fun ChooseWaterVolumeCard(waterViewModel: WaterViewModel, modifier:Modifier=Modifier)
{
    Dialog(onDismissRequest = { /*TODO*/ }) {
        Card(modifier = modifier) {
            Column(modifier = modifier
                .padding(16.dp)
                .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Выбор объема")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        OutlinedButton(
                            onClick = { waterViewModel.addToDatabase(50) },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "50 ml")
                        }
                        OutlinedButton(
                            onClick = { waterViewModel.addToDatabase(150) },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "150 ml")
                        }
                        OutlinedButton(
                            onClick = { waterViewModel.addToDatabase(250) },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "250 ml")
                        }
                    }
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        OutlinedButton(
                            onClick = { waterViewModel.addToDatabase(100) },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "100 ml")
                        }
                        OutlinedButton(
                            onClick = { waterViewModel.addToDatabase(200) },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "200 ml")
                        }
                        OutlinedButton(
                            onClick = { waterViewModel.addToDatabase(500) },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "500 ml")
                        }
                    }
                }

                Text(text = "Свое значение")
                OutlinedTextField(
                    value = waterViewModel.textFieldValue,
                    onValueChange = { newValue ->
                        waterViewModel.updateTextFieldValue(newValue)
                    },
                    leadingIcon = { Icon(painterResource(id = R.drawable.cup), contentDescription = null) },
                    label = { Text(text = "ml") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
                TextButton(onClick = { waterViewModel.addToDatabase(waterViewModel.customWaterAmount) },modifier=modifier.align(alignment = Alignment.End)) {
                    Text(text = "ОK",fontFamily = FontFamily.SansSerif)
                }
            }
        }
    }
}



@Composable
fun Drop(
    usedWaterAmount:Int,
    onAddWaterClick: () -> Unit,
    totalWaterAmount:Int,

    modifier: Modifier=Modifier
)
{
    val waterPercentage = animateFloatAsState(
        targetValue = usedWaterAmount.toFloat()/totalWaterAmount.toFloat(),
        label="Water animation",
        animationSpec = tween(durationMillis = 2000)
    ).value
    val textStyle = animateIntAsState(
        targetValue = usedWaterAmount,
        label="Used water amount",
        animationSpec = tween(durationMillis = 5000)
    ).value
    val textPrecentage = animateIntAsState(
        targetValue = ((usedWaterAmount/totalWaterAmount.toFloat())*100).toInt(),
        label="Used water amount",
        animationSpec = tween(durationMillis = 5000)
    ).value
    Box(modifier = modifier
        .fillMaxSize()
        .aspectRatio(1f)
        .padding(32.dp)) {
        Canvas(
            modifier= modifier.fillMaxSize()
        ) {
            val width = size.width
            val height = size.height

            val dropPath = Path().apply {
                moveTo(x = width / 2, 0f)
                quadraticBezierTo(
                    x1 = width * 0.05f,
                    y1 = height * 0.35f,
                    x2 = width * 0.15f,
                    y2 = height * 0.7f
                )
                quadraticBezierTo(
                    x1 = width * 0.25f,
                    y1 = height * 0.95f,
                    x2 = width * 0.5f,
                    y2 = height * 0.95f
                )
                quadraticBezierTo(
                    x1 = width * 0.75f,
                    y1 = height * 0.95f,
                    x2 = width * 0.85f,
                    y2 = height * 0.7f
                )
                quadraticBezierTo(x1 = width * 0.95f, y1 = height * 0.35f, x2 = width / 2, y2 = 0f)

                close()
            }

            clipPath(dropPath)
            {
                val waterPosition = ((1f - waterPercentage) * size.width)
                val waveHeight = 5.dp.toPx()
                val waveLength = size.width / 4 // Количество волн
                val waterPath = Path().apply {
                    moveTo(x = 0f, y = waterPosition)
                    for (x in 0 until size.width.toInt()) {
                        val y =
                            waterPosition - waveHeight * sin((x / waveLength) * (2 * Math.PI)).toFloat()
                        lineTo(x = x.toFloat(), y = y)
                    }
                    lineTo(x = size.width, y = height * 0.95f)
                    lineTo(x = 0f, y = height * 0.95f)
                    close()
                }
                // Создание линейного градиента
                val gradient = Brush.linearGradient(
                    colors = listOf( Color(0xFF00C2FF),Color(0xFF00C2FF)),
                    start = Offset(0f, 0f),
                    end = Offset(size.height, size.height)
                )

                // Рисование пути с использованием градиента
                drawPath(
                    path = waterPath,
                    brush = gradient
                )
                /*
                val waterPosition = ((1f-waterPercentage) * size.width)
                val waterPath = Path().apply {
                    moveTo(x=0f, y = waterPosition)
                    lineTo(x=size.width,y=waterPosition)
                    lineTo(x=width,y= height*0.95f)
                    lineTo(x=0f,y= height*0.95f)
                    close()
                }
                drawPath(
                    path = waterPath,
                    color = Color(0xFFA1D2FD)
                )
               */
            }
            drawPath(
                path = dropPath,
                color = Color.Black,
                style = Stroke(
                    width = 2.dp.toPx(),
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round
                )
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally,modifier= Modifier
            .align(alignment = Alignment.Center)
            .fillMaxHeight()) {
            Spacer(modifier = Modifier.weight(0.4f))
            Column( horizontalAlignment = Alignment.CenterHorizontally,modifier = Modifier.weight(0.6f)){
                Text(fontSize = 50.sp, fontWeight = FontWeight.Medium, fontFamily = FontFamily.SansSerif, text = "${textPrecentage}%")
                Text(fontSize = 15.sp, fontWeight = FontWeight.Medium, fontFamily = FontFamily.SansSerif, text = "$textStyle / $totalWaterAmount")
            }
        }
        IconButton(onClick = onAddWaterClick,modifier= Modifier
            .align(alignment = Alignment.BottomCenter)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                width = 2.dp,
                color = Color.Black,
                shape = RoundedCornerShape(16.dp)
            )) {
            Icon(Icons.Filled.Add, contentDescription = "Add Icon")
        }
    }
}