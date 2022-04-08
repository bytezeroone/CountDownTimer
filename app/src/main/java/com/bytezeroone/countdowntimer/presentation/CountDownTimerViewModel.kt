package com.bytezeroone.countdowntimer.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountDownTimerViewModel @Inject constructor(

): ViewModel() {

    var _count = mutableStateOf(60)
    var count: State<Int> = _count

    var stopTimer = mutableStateOf(false)

    private fun simpleFlow(): Flow<Int> = flow {
        for (i in _count.value downTo 0) {
            emit(i)
            Log.d("asdsad2", "i is $i")
            _count.value = i
            delay(1000L)
            Log.d("asdsad3", "count is ${count.value}")
        }
    }

    fun onEvent(event: CountDownEvent) {
        viewModelScope.launch {
            when (event) {
                is CountDownEvent.OnStartButtonClick -> {
                    startTimerFun()
                    //simpleCountDown()
                }
                is CountDownEvent.OnStopButtonClick -> {
                    stopTimerFun()
                    Log.d("asdsad2", "Cancel is ${count.value}")

                }
                is CountDownEvent.OnRestartButtonClick -> {
                    resetTimerFun()

                }
            }
        }
    }

    private fun startTimerFun() {
        viewModelScope.launch {
            simpleFlow()
                .collect {
                    when (stopTimer.value) {
                        true -> cancel()
                        else -> { Unit }
                    }
                }
        }
    }

    private suspend fun stopTimerFun() {
        stopTimer.value = true
        delay(1000L)
        stopTimer.value = false

    }

    private suspend fun resetTimerFun() {
        stopTimerFun()
        _count.value = 60
        startTimerFun()
    }
}
