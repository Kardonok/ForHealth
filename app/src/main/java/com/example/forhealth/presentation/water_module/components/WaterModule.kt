package com.example.forhealth.presentation.water_module.components

import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.forhealth.navigation.NavBar
import kotlin.math.sin

@Preview(showBackground = true)
@Composable
fun WaterModulePreview()
{
    WaterModule()
}

@Composable
fun WaterModule(modifier:Modifier=Modifier)
{
    Scaffold(
        bottomBar = {
            //NavBar(navController = navController)
        }
    ) { innerPadding ->
        Column(modifier= modifier.padding(innerPadding)) {
            Drop(
                usedWaterAmount = 800,
                totalWaterAmount = 1000,
                modifier = modifier.weight(0.5f)
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)
            )
            RecordListCard(modifier = modifier.weight(0.5f).padding(16.dp))
        }
    }
}

@Composable
fun RecordListCard(modifier:Modifier=Modifier) {
    Box(modifier= modifier
        .fillMaxSize()
        .border(width = 2.dp, shape = RoundedCornerShape(16.dp), color = Color.Black)) {
        Text(text = "Записи",modifier=modifier.padding(8.dp))
        /*
        LazyColumn {
            items{ item ->
                //SingleRecordCard()
            }
        }
        */
    }
}

@Composable
fun RecommendationsCard(modifier:Modifier=Modifier) {
    Box(modifier= modifier
        .fillMaxSize()
        .border(width = 2.dp, shape = RoundedCornerShape(16.dp), color = Color.Black)) {
        Text(text = "Записи",modifier=modifier.padding(8.dp))
    }
}

@Composable
fun SingleRecordCard(modifier:Modifier=Modifier)
{
    Row(verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.SpaceBetween,modifier = modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Filled.FavoriteBorder, contentDescription = null)
            Spacer(modifier = modifier.width(8.dp))
            Text(text = "12:40")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "100")
            Spacer(modifier = modifier.width(8.dp))
            Icon(Icons.Filled.Clear, contentDescription = null,modifier=modifier.clickable {  })
        }
    }
}

@Composable
fun ChooseWaterVolumeCard()
{
    Dialog(onDismissRequest = { /*TODO*/ }) {
        Card() {
            Column {
                Text(text = "")
                Row {
                    Column {

                    }
                    Column {

                    }
                }
                Text(text = "")
                OutlinedTextField(value = "",
                    onValueChange = {  },
                    leadingIcon = { Icon(Icons.Filled.Person, contentDescription = null) },
                    label = { Text(text = "") },
                    singleLine = true,)
                TextButton(onClick = { }) {
                    Text(text = "ОK",fontFamily = FontFamily.SansSerif,)
                }
            }
        }
    }
}

@Composable
fun Drop(
    usedWaterAmount:Int,
    totalWaterAmount:Int,

    modifier: Modifier=Modifier
)
{
    val waterPercentage = animateFloatAsState(
        targetValue = usedWaterAmount.toFloat()/totalWaterAmount.toFloat(),
        label="Water animation",
        animationSpec = tween(durationMillis = 5000)
    ).value
    Box(modifier = modifier.fillMaxSize()) {
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
                    colors = listOf( Color(0xFF9FCAFF),Color(0xFF91AADA)),
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
                    width = 4.dp.toPx(),
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round
                )
            )
        }
        Text(fontSize = 30.sp, fontWeight = FontWeight.Medium, fontFamily = FontFamily.SansSerif, text = "$usedWaterAmount / $totalWaterAmount",modifier= Modifier
            .align(alignment = Alignment.Center))
        IconButton(onClick = {  },modifier= Modifier
            .align(alignment = Alignment.BottomCenter)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                width = 4.dp,
                color = Color.Black,
                shape = RoundedCornerShape(16.dp)
            )) {
            Icon(Icons.Filled.Add, contentDescription = "Add Icon")
        }
    }
}