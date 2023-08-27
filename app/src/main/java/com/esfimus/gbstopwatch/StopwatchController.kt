package com.esfimus.gbstopwatch

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

const val DEFAULT_TIME = "00:00:00:000"

class StopwatchController(
    private val stopwatchStateHolder: StopwatchStateHolder,
    private val scope: CoroutineScope
) {
    private var job: Job? = null
    private val mutableTicker = MutableStateFlow(DEFAULT_TIME)
    val ticker: StateFlow<String> = mutableTicker

    private fun startJob() {
        scope.launch {
            while(isActive) {
                mutableTicker.value = stopwatchStateHolder.getStringTimeRepresentation()
                delay(20)
            }
        }
    }

    private fun stopJob() {
        scope.coroutineContext.cancelChildren()
        job = null
    }

    private fun clearValue() {
        mutableTicker.value = DEFAULT_TIME
    }

    fun start() {
        if (job == null) startJob()
        stopwatchStateHolder.start()
    }

    fun pause() {
        stopwatchStateHolder.pause()
        stopJob()
    }

    fun stop() {
        stopwatchStateHolder.stop()
        stopJob()
        clearValue()
    }


}