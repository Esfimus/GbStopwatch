package com.esfimus.gbstopwatch.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.esfimus.gbstopwatch.R
import com.esfimus.gbstopwatch.databinding.ActivityMainBinding
import com.esfimus.gbstopwatch.ui.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var ui: ActivityMainBinding
    private val model: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
        initView()
    }

    private fun initView() {
        model.collectData()
        with (ui) {
            model.stopwatchLiveData.observe(this@MainActivity) { textField.text = it }

            model.isRunning.observe(this@MainActivity) {
                if (it) {
                    startButton.setImageResource(R.drawable.icon_pause)
                    startButton.setOnClickListener { model.pauseStopwatch() }
                } else {
                    startButton.setImageResource(R.drawable.icon_play)
                    startButton.setOnClickListener { model.startStopwatch() }
                }
            }

            stopButton.setOnClickListener { model.stopStopwatch() }
        }
    }
}