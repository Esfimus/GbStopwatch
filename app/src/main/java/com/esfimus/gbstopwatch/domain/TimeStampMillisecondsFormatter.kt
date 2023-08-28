package com.esfimus.gbstopwatch.domain

class TimeStampMillisecondsFormatter {
    fun format(timeStamp: Long): String {
        val millisecondsFormatted = (timeStamp % 1000).pad(3)
        val secondsFormatted = ((timeStamp / 1000) % 60).pad(2)
        val minutesFormatted = ((timeStamp / 1000 / 60) % 60).pad(2)
        val hoursFormatted = ((timeStamp / 1000 / 60 / 60) % 60).pad(2)
        return "$hoursFormatted:$minutesFormatted:$secondsFormatted:$millisecondsFormatted"
    }

    private fun Long.pad(desiredLength: Int) = this.toString().padStart(desiredLength, '0')
}