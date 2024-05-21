package com.example.forhealth.presentation.woman_health_module.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.forhealth.navigation.NavBar


@Preview(showBackground = true)
@Composable
fun WomanHealthModulePreview()
{
    //WomanHealthModule()
}

@Preview
@Composable
fun CalendarCardPreview()
{
    CalendarCard()
}

@Preview
@Composable
fun InformationCardPreview()
{
    InformationCard()
}


@Composable
fun WomanHealthModule(navController:NavHostController,modifier: Modifier=Modifier)
{
    Scaffold(
        bottomBar = {
            NavBar(navController = navController)
        }
    ) { innerPadding ->
        Column(modifier.padding(innerPadding).padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            InformationCard(modifier=modifier.weight(0.5f))
            Button(
                onClick = { /*TODO*/ },
                modifier.padding(8.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFFE36B6B))
            ) {
                Text(text = "Отметить месячные", color = Color.White, fontSize = 15.sp)
            }
            CalendarCard()
        }
    }
}


@Composable
fun CalendarCard(modifier: Modifier = Modifier)
{
    Column{
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color(0xFFE36B6B),
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                )
                .padding(vertical = 8.dp) // Добавляем вертикальные отступы
        ) {
            Icon(Icons.Filled.KeyboardArrowLeft, contentDescription = null,modifier=Modifier.align(Alignment.CenterStart))
            Text(
                text = "Месяц",
                modifier = Modifier.align(Alignment.Center),
                color = Color.White, // Задаем цвет текста белым для лучшего контраста
                fontFamily = FontFamily.SansSerif,
                fontSize = 20.sp
            )
            Icon(Icons.Filled.KeyboardArrowRight, contentDescription = null,modifier=Modifier.align(Alignment.CenterEnd))
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(16.dp)
            .background(color = Color.White))
        Box(modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFFF8D9D9),
                shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
            )
            .padding(8.dp) // Добавляем вертикальные отступы)
        )
        {
            DateCard()
        }
    }

}

@Composable
fun DateCard(modifier: Modifier = Modifier) {
    Column(modifier = modifier.aspectRatio(1.3f)) {
        Row(horizontalArrangement = Arrangement.SpaceAround, modifier = modifier.fillMaxWidth()) {
            Text(text = "пн", fontWeight = FontWeight.Medium)
            Text(text = "вт", fontWeight = FontWeight.Medium)
            Text(text = "ср", fontWeight = FontWeight.Medium)
            Text(text = "чт", fontWeight = FontWeight.Medium)
            Text(text = "пт", fontWeight = FontWeight.Medium)
            Text(text = "сб", fontWeight = FontWeight.Medium)
            Text(text = "вс", fontWeight = FontWeight.Medium)
        }
        Canvas(modifier = Modifier
            .fillMaxSize()
            .aspectRatio(1.41f)) {
            val width = size.width
            val height = size.height

            val radius = width / (7 * 2)
            val circleRadius = radius * 0.78f
            var dayCounter = 1

            val textPaint = androidx.compose.ui.graphics.Paint().asFrameworkPaint().apply {
                isAntiAlias = true
                textAlign = android.graphics.Paint.Align.CENTER
                textSize = circleRadius * 0.8f // Размер текста
                color = 0xFF000000.toInt() // Черный цвет
            }

            for (iy in (0..4)) {
                for (ix in (0..6)) {

                    val offsetX = radius + (ix * radius * 2)
                    val offsetY = radius + (iy * radius * 2)

                    drawCircle(
                        color = Color.White,
                        center = Offset(offsetX, offsetY),
                        radius = circleRadius,
                    )
                    drawCircle(
                        color = Color.Black,
                        center = Offset(offsetX, offsetY),
                        radius = circleRadius,
                        style = Stroke(width = 3f)
                    )

                    drawContext.canvas.nativeCanvas.drawText(
                        dayCounter.toString(),
                        offsetX,
                        offsetY - (textPaint.ascent() + textPaint.descent()) / 2,
                        textPaint
                    )

                    dayCounter++
                }
            }
        }
    }
}

@Composable
fun InformationCard(modifier: Modifier=Modifier)
{
    Box(modifier = modifier
        .aspectRatio(1f))
    {
        Canvas(modifier = modifier
            .fillMaxSize()
            .aspectRatio(1f)) {
            val width = size.width
            val height = size.height

            val radius = width / 2 - 5f

            drawCircle(
                color = Color(0xFFE36B6B),
                center = Offset(width/2, height/2),
                radius = radius,
            )

            drawCircle(
                color = Color.White,
                center = Offset(width/2, height/2),
                radius = radius-60f,
                style = Stroke(
                    width = 60f,
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 10f), 0f) // Пример пунктира
                )
            )
        }
        Column(modifier = Modifier.align(alignment = Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Ещё", color = Color.White, fontSize = 30.sp)
            Text(text = "2 дня", color = Color.White, fontSize = 40.sp)
            Text(text = "месячных", color = Color.White, fontSize = 30.sp)
        }
    }
}
