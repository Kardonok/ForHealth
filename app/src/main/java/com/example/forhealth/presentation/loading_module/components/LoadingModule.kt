package com.example.forhealth.presentation.loading_module.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.forhealth.R


@Preview
@Composable
fun LoadingModulePreview()
{
    LoadingModule()
}

@Composable
fun LoadingModule(modifier: Modifier = Modifier)
{
    Box(modifier = modifier.fillMaxSize())
    {
        Image(modifier = modifier.align(alignment = Alignment.Center),painter = painterResource(id = R.drawable.loading_image), contentDescription = "Loading")
    }
}