package com.bytezeroone.countdowntimer.presentation

sealed class CountDownEvent {
    object OnStartButtonClick: CountDownEvent()
    object OnStopButtonClick: CountDownEvent()
    object OnRestartButtonClick: CountDownEvent()
}