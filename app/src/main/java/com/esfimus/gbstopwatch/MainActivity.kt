package com.esfimus.gbstopwatch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.esfimus.gbstopwatch.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var ui: ActivityMainBinding

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        initView()
    }

    private fun initView() {
        with (ui) {
            CoroutineScope(Dispatchers.Main + SupervisorJob()).launch {
                stopwatchController.ticker.collect {
                    textField.text = it
                }
            }

            startButton.setOnClickListener { stopwatchController.start() }
            pauseButton.setOnClickListener { stopwatchController.pause() }
            stopButton.setOnClickListener { stopwatchController.stop() }
        }
    }
}