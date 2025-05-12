package com.example.animalsapp.service

import com.example.animalsapp.models.Animal
import com.example.animalsapp.models.Environment
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface AnimalService {

    @GET("animals")
    suspend fun getAnimals(): List<Animal>

    @GET("animals/{id}")
    suspend fun getAnimalById( @Path("id") id:String) : Animal

    @GET("animals")
    suspend fun getAnimalByEnvironmentId(
        @Query("environmentId") environmentId: String,
    ) : List<Animal>

    @GET("environments")
    suspend fun getEnvironments(): List<Environment>

    @GET("environments/{id}")
    suspend fun getEnvironmentById ( @Path("id") id: String) : Environment
}