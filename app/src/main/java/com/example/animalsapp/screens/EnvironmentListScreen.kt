package com.example.animalsapp.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.animalsapp.components.EnvironmentCard
import com.example.animalsapp.models.Environment
import com.example.animalsapp.service.AnimalService
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun EnvironmentListScreen(
    innerPadding : PaddingValues,
    navController : NavController
) {
    var BASE_URL = "https://animals.juanfrausto.com/api/"
    val scope = rememberCoroutineScope()
    var environments by remember {
        mutableStateOf(listOf<Environment>())
    }
    var isLoading by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(key1 = true) {
        scope.launch {
            try {
                val retrofitBuilder = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                val animalService = retrofitBuilder.create(AnimalService::class.java)
                val result = animalService.getEnvironments()
                environments = result
                isLoading = false
                Log.i("Response", result.toString())
            }
            catch (e: Exception) {
                Log.e("ERROR", e.toString())
            }
        }
    }

    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(vertical = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Ambientes",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(24.dp))

            Column (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                environments.forEach { environment ->
                    EnvironmentCard(
                        environment = environment
                    ) { environmentRegresado ->
                        Log.i("AmbientePresionado", environmentRegresado.id.toString())
                        navController.navigate("environment-list/${environmentRegresado.id}")
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
}