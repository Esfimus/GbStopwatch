package com.esfimus.gbstopwatch.domain

import com.esfimus.gbstopwatch.data.StopwatchState

class ElapsedTimeCalculator(private val timeStampProvider: TimeStampProvider) {
    fun calculate(state: StopwatchState.Running): Long {
        val currentTimeStamp = timeStampProvider.getMilliseconds()
        val timePassedSinceStart =
            if (currentTimeStamp > state.startTime) currentTimeStamp - state.startTime else 0
        return timePassedSinceStart + state.elapsedTime
    }
}