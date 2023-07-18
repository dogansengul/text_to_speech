package com.example.texttospeechtutorial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Toast
import com.example.texttospeechtutorial.databinding.ActivityMainBinding
import org.w3c.dom.Text
import java.util.Locale

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var binding: ActivityMainBinding? = null
    private var textToSpeech: TextToSpeech? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        textToSpeech = TextToSpeech(this, this)

        binding?.button?.setOnClickListener {
            if(binding!!.editTextText.text.isEmpty()) {
                Toast.makeText(this, "Enter a message to speak.", Toast.LENGTH_SHORT).show()
            } else {
                speakOut(binding?.editTextText?.text.toString())
            }
        }

    }

    private fun speakOut(text: String) {
        textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
        if(textToSpeech != null) {
            textToSpeech?.stop()
            textToSpeech?.shutdown()
        }

    }

    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS) {
            val result = textToSpeech?.setLanguage(Locale.US)

            if(result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA) {
                Toast.makeText(this, "The language is not supported.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}