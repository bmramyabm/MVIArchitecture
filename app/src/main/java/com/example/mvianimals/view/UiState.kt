package com.example.mvianimals.view

import com.example.mvianimals.model.Animal

sealed class UiState {
    object Idle : UiState()
    object Loading: UiState()
    data class Animals(val animals: List<Animal>): UiState()
    data class Error(val message : String?): UiState()
}