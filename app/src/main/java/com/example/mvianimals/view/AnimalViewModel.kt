package com.example.mvianimals.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvianimals.api.AnimalRepo
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class AnimalViewModel(private val repo: AnimalRepo): ViewModel() {

    val userIntent = Channel<MainIntent>(Channel.UNLIMITED)

//   private private val _uiState: MutableLiveData<UiState> = MutableLiveData<UiState>()
//    val uiState : LiveData<UiState> = _uiState

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState

    init{
        processEvent()
    }

    private fun processEvent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect { collector ->
                when(collector){
                    is MainIntent.FetchAnimals -> fetchAnimals()
                }

            }

        }
    }

    private fun fetchAnimals() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = try{
                UiState.Animals(repo.getAnimals())
            } catch(e:Exception){
                UiState.Error(e.message)
            }
        }
    }

}