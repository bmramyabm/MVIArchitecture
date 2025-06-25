package com.example.mvianimals.api

class AnimalRepo (val api: AnimalApi){
    suspend fun getAnimals() = api.getAnimals()
}