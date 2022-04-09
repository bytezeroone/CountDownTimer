package com.bytezeroone.countdowntimer.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountDownTimerViewModel @Inject constructor(

): ViewModel() {
    //refactoring
    var time by mutableStateOf(60)

    private var isActive = false

    private var timer = 60
    private var timeStamp = 1

    private fun startTimer() {
        if (isActive || time == 0) return
        viewModelScope.launch {
            this@CountDownTimerViewModel.isActive = true
            while (this@CountDownTimerViewModel.isActive) {
                timer -= 1
                time = timer
                delay(1000L)
                if (timer == -1) {
                    this@CountDownTimerViewModel.isActive = false
                }
            }
        }
    }

    private fun pauseTimer() {
        isActive = false
    }

    private fun resetTimer() {
        viewModelScope.coroutineContext.cancelChildren()
        timer = 60
        time = 60
        isActive = false
    }

    fun onEvent(event: CountDownEvent) {
        viewModelScope.launch {
            when (event) {
                is CountDownEvent.OnStartButtonClick -> {
                    startTimer()
                }
                is CountDownEvent.OnStopButtonClick -> {
                    pauseTimer()
                }
                is CountDownEvent.OnRestartButtonClick -> {
                    resetTimer()

                }
            }
        }
    }
}
