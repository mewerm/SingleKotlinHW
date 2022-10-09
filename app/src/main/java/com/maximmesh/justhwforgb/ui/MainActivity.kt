package com.maximmesh.justhwforgb.ui

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.maximmesh.justhwforgb.repository.DataClass
import com.maximmesh.justhwforgb.R
import com.maximmesh.justhwforgb.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dataClass = DataClass(title = "This is just title from Activity")
        binding.resultClick.setTextColor(Color.RED)

        runOnUiThread {
            getButtonOneWork(dataClass)
            getButtonRestartWork()
            getButtonStartCycleWork()
            getButtonElseStartCycleWork()
        }
    }

    private fun loopFirstAndSecond(maxInt: Int) {
        binding.loopActionStatus.setText(R.string.loop_started)

        Thread {
            for (i in 1..maxInt) {
                Log.d("Thread", "Итерация: " + i + "поток: " + Thread.currentThread())
                when {
                    i % 2 == 0 -> {
                        runOnUiThread {
                            binding.loopResult.textAlignment = View.TEXT_ALIGNMENT_TEXT_END
                            binding.loopResult.setText(R.string.loop_yell_two)
                        }
                    }
                    else -> {
                        runOnUiThread {
                            binding.loopResult.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
                            binding.loopResult.setText(R.string.loop_yell_one)
                        }
                    }
                }
                Thread.sleep(500)
            }
            runOnUiThread{
                with(binding) {
                    loopResult.textAlignment = View.TEXT_ALIGNMENT_CENTER
                    loopResult.text = resources.getString(R.string.loop_yell_calculation_is_over)
                    loopActionStatus.text = resources.getString(R.string.loop_finished)
                    buttonElseStartCycle.visibility = View.VISIBLE
                    buttonOne.isEnabled = true
                    buttonRestart.isEnabled = true
                    buttonElseStartCycle.isEnabled = true
                }
            }
        }.start()
    }

    private fun getButtonOneWork(dataClass: DataClass) {
        with(binding) {
            buttonOne.setOnClickListener {

                resultClick.setTextColor(Color.WHITE)
                resultClick.setBackgroundColor(Color.BLACK)

                dataClassTitle.text = dataClass.title
                dataClassDescription.text = dataClass.description
                resultClick.setText(R.string.result_message_button_press)
                buttonOne.visibility = View.GONE
                buttonElseStartCycle.visibility = View.GONE
                buttonRestart.visibility = View.VISIBLE
            }
        }
    }

    private fun getButtonRestartWork() {
        with(binding) {
            buttonRestart.setOnClickListener {
                resultClick.setTextColor(Color.RED)
                resultClick.setBackgroundColor(Color.WHITE)

                resultClick.setText(R.string.waiting_message_button_press)
                buttonOne.visibility = View.VISIBLE
                buttonRestart.visibility = View.GONE
                dataClassTitle.setText(R.string.message_text_empty)
                dataClassDescription.setText(R.string.message_text_empty)

                loopResult.setText(R.string.message_text_empty)
                loopActionStatus.setText(R.string.loop_is_ready_to_start)

                buttonStartCycle.visibility = View.VISIBLE
                buttonStartCycle.isEnabled = true
            }
        }
    }

    private fun getButtonStartCycleWork() {
        with(binding) {
            buttonStartCycle.setOnClickListener {
                buttonStartCycle.visibility = View.GONE
                buttonOne.isEnabled = false
                buttonRestart.isEnabled = false
                loopFirstAndSecond(10)
            }
        }
    }

    private fun getButtonElseStartCycleWork() {
        with(binding) {
            buttonElseStartCycle.setOnClickListener {
                buttonElseStartCycle.visibility = View.GONE
                buttonStartCycle.visibility = View.GONE
                buttonOne.isEnabled = false
                buttonRestart.isEnabled = false
                buttonElseStartCycle.isEnabled = false
                loopFirstAndSecond(10)
            }
        }
    }
}





