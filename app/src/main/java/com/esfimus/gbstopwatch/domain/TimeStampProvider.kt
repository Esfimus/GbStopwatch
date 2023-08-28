package com.esfimus.gbstopwatch.domain

interface TimeStampProvider {
    fun getMilliseconds(): Long
}