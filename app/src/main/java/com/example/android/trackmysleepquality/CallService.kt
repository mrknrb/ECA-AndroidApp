package com.example.android.trackmysleepquality

import android.media.MediaPlayer
import android.speech.tts.TextToSpeech
import android.telecom.Call
import android.telecom.InCallService
import android.widget.Toast
import java.util.*

class CallService : InCallService() {
   // internal lateinit var myTts: TextToSpeech
    override fun onCallAdded(call: Call) {
       //OngoingCall.call!!.disconnect()
       OngoingCall.call = null
       /*
        val mMediaPlayer2: MediaPlayer
        mMediaPlayer2 = MediaPlayer.create(applicationContext, R.raw.silent_sound)
        mMediaPlayer2.setOnCompletionListener { mMediaPlayer2.release() }
        mMediaPlayer2.start()
       */
/*
        myTts = TextToSpeech(applicationContext, TextToSpeech.OnInitListener { status ->
            if (status != TextToSpeech.ERROR) {
                myTts.language = Locale.US
            }
        })
        myTts.setSpeechRate(1f)
        call!!.disconnect()
       myTts.speak("Opening file. Opening file. Opening file", TextToSpeech.QUEUE_FLUSH, null)
*/
        //OngoingCall.call = call
        //CallActivity.start(this, call)

       // Toast.makeText(this, "kurva jóóóóó!!!!!!!", Toast.LENGTH_SHORT).show()
    }

    override fun onCallRemoved(call: Call) {
     // OngoingCall.call = null
    }
}