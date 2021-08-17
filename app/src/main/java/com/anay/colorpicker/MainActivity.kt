package com.anay.colorpicker

import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.core.content.edit
import kotlin.String

class MainActivity : AppCompatActivity() {

    private lateinit var redSeekBarPreference: SeekBar
    private lateinit var blueSeekBarPreference: SeekBar
    private lateinit var greenSeekBarPreference: SeekBar
    private lateinit var displayColorView: EditText
    private lateinit var colorDisplayCircle: ImageView
    private lateinit var setButton: Button
    private var colorToDisplay = 0x000000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val prefs : SharedPreferences = getPreferences(MODE_PRIVATE)
        setContentView(R.layout.activity_main)
        colorDisplayCircle = findViewById(R.id.colorDisplay)
        redSeekBarPreference = findViewById(R.id.seekBarRed)
        blueSeekBarPreference = findViewById(R.id.seekBarBlue)
        greenSeekBarPreference = findViewById(R.id.seekBarGreen)
        displayColorView = findViewById(R.id.colorView)
        setButton = findViewById(R.id.set_button)
        val redProgressView: TextView = findViewById(R.id.red_progress)
        val greenProgressView: TextView = findViewById(R.id.green_progress)
        val blueProgressView: TextView = findViewById(R.id.blue_progress)
        val setColor: TextView = findViewById(R.id.set_color)
        setButton.setOnClickListener {
            if (displayColorView.text.isNotEmpty() && Color.parseColor(displayColorView.text.toString()) != colorToDisplay) {
                colorToDisplay = Color.parseColor(displayColorView.text.toString())
            }
            setColor.text = String.format("#%06X", 0xFFFFFF and colorToDisplay)
            redSeekBarPreference.setProgress(Color.red(colorToDisplay), true)
            greenSeekBarPreference.setProgress(Color.green(colorToDisplay), true)
            blueSeekBarPreference.setProgress(Color.blue(colorToDisplay), true)
            prefs.edit().putInt("color", colorToDisplay).apply()
        }
        redSeekBarPreference.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar, p1: Int, p2: Boolean) {
                colorToDisplay = Color.rgb(
                    p1,
                    Color.green(colorToDisplay),
                    Color.blue(colorToDisplay)
                )
                displayColorView.setText(String.format("#%06X", 0xFFFFFF and colorToDisplay), TextView.BufferType.EDITABLE)
                colorDisplayCircle.setBackgroundColor(colorToDisplay)
                redProgressView.text = p1.toString()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                // do nothing
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                // do nothing
            }
        })
        greenSeekBarPreference.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar, p1: Int, p2: Boolean) {
                colorToDisplay = Color.rgb(
                    Color.red(colorToDisplay),
                    p1,
                    Color.blue(colorToDisplay)
                )
                displayColorView.setText(String.format("#%06X", 0xFFFFFF and colorToDisplay), TextView.BufferType.EDITABLE)
                colorDisplayCircle.setBackgroundColor(colorToDisplay)
                greenProgressView.text = p1.toString()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                // do nothing
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                // do nothing
            }
        })
        blueSeekBarPreference.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar, p1: Int, p2: Boolean) {
                colorToDisplay = Color.rgb(
                    Color.red(colorToDisplay),
                    Color.green(colorToDisplay),
                    p1,
                )
                displayColorView.setText(String.format("#%06X", 0xFFFFFF and colorToDisplay), TextView.BufferType.EDITABLE)
                colorDisplayCircle.setBackgroundColor(colorToDisplay)
                blueProgressView.text = p1.toString()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                // do nothing
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                // do nothing
            }
        })
        if (prefs.contains("color")) {
            colorToDisplay = prefs.getInt("color", 0x000000)
            setButton.callOnClick()
        }
    }
}