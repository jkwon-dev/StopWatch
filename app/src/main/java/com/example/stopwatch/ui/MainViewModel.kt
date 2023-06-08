package com.example.stopwatch.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.util.Timer
import kotlin.concurrent.fixedRateTimer
import kotlin.concurrent.timer
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

class MainViewModel: ViewModel() {

    private var time: Duration = Duration.ZERO
    private lateinit var timer: Timer

    var seconds by mutableStateOf("00")
    var minutes by mutableStateOf("00")
    var hours by mutableStateOf("00")
    var isPlayning by mutableStateOf(false)


    @OptIn(ExperimentalTime::class)
    fun start() {

        timer = fixedRateTimer(initialDelay = 1000L, period = 1000L) {
            time = time.plus(Duration.seconds(1))
            updateTimeState()
        }

        isPlayning = true
    }

    private fun updateTimeState() {
        time.toComponents { hours, minutes, seconds, _ ->
            this@MainViewModel.seconds = seconds.pad()
            this@MainViewModel.minutes = minutes.pad()
            this@MainViewModel.hours = minutes.pad()
        }
    }

    private fun Int.pad(): String {
        return this.toString().padStart(2, '0')
    }

    private fun Long.pad(): String {
        return this.toString().padStart(2, '0')
    }

    fun pause() {
        timer.cancel()
        isPlayning = false
    }


    fun stop() {
        pause()
        time = Duration.ZERO
        updateTimeState()
    }

}