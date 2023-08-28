package com.esfimus.gbstopwatch.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.esfimus.gbstopwatch.domain.ElapsedTimeCalculator
import com.esfimus.gbstopwatch.domain.StopwatchController
import com.esfimus.gbstopwatch.domain.StopwatchStateCalculator
import com.esfimus.gbstopwatch.domain.StopwatchStateHolder
import com.esfimus.gbstopwatch.domain.TimeStampMillisecondsFormatter
import com.esfimus.gbstopwatch.domain.TimeStampProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _stopwatchLiveData: MutableLiveData<String> = MutableLiveData()
    val stopwatchLiveData get(): LiveData<String> = _stopwatchLiveData
    private val _isRunning: MutableLiveData<Boolean> = MutableLiveData()
    val isRunning get(): LiveData<Boolean> = _isRunning

    private val timeStampProvider = object : TimeStampProvider {
        override fun getMilliseconds() = System.currentTimeMillis()
    }

    private val stopwatchController = StopwatchController(
        StopwatchStateHolder(
            StopwatchStateCalculator(
                timeStampProvider,
                ElapsedTimeCalculator(timeStampProvider)
            ),
            ElapsedTimeCalculator(timeStampProvider),
            TimeStampMillisecondsFormatter()
        ),
        CoroutineScope(Dispatchers.Main + SupervisorJob())
    )

    fun collectData() {
        _isRunning.value = false
        CoroutineScope(Dispatchers.Main + SupervisorJob()).launch {
            stopwatchController.ticker.collect {
                _stopwatchLiveData.value = it
            }
        }
    }

    fun startStopwatch() {
        stopwatchController.start()
        _isRunning.value = true
    }

    fun pauseStopwatch() {
        stopwatchController.pause()
        _isRunning.value = false
    }

    fun stopStopwatch() {
        stopwatchController.stop()
        _isRunning.value = false
    }
}