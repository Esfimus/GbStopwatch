package com.esfimus.gbstopwatch

class StopwatchStateHolder(
    private val stopwatchStateCalculator: StopwatchStateCalculator,
    private val elapsedTimeCalculator: ElapsedTimeCalculator,
    private val timeStampMillisecondsFormatter: TimeStampMillisecondsFormatter
) {
    private var currentState: StopwatchState = StopwatchState.Paused(0)

    fun start() {
        currentState = stopwatchStateCalculator.calculateRunningState(currentState)
    }

    fun pause() {
        currentState = stopwatchStateCalculator.calculatePausedState(currentState)
    }

    fun stop() {
        currentState = StopwatchState.Paused(0)
    }

    fun getStringTimeRepresentation(): String {
        val elapsedTime =
            when (val currentState = currentState) {
                is StopwatchState.Paused -> currentState.elapsedTime
                is StopwatchState.Running -> elapsedTimeCalculator.calculate(currentState)
            }
        return timeStampMillisecondsFormatter.format(elapsedTime)
    }
}