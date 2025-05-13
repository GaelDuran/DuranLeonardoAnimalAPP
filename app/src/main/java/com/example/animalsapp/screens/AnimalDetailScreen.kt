package com.example.animalsapp.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.example.animalsapp.models.Animal
import com.example.animalsapp.service.AnimalService
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun AnimalDetailScreen( innerPadding: PaddingValues, animalId: String){
    var BASE_URL = "https://animals.juanfrausto.com/api/"
    val scope = rememberCoroutineScope()
    var animal by remember {
        mutableStateOf<Animal?>(null)
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
                animal = animalService.getAnimalById(animalId)
                Log.i("Animal Detail Screen", animal.toString())
                isLoading = false
            } catch (e: Exception) {
                Log.e("ERROR", e.toString())
            }
        }
    }
    if (isLoading) {
        Box() {  }
    }
}