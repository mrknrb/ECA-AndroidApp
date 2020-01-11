package com.example.android.trackmysleepquality

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.widget.EditText
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import kotlinx.android.synthetic.main.activity_ttsprogram.*
import android.app.Activity
import android.app.PendingIntent.getActivity
import android.content.Context
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.SeekBar
import java.util.*
import android.widget.ImageButton
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.AsyncTask
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.example.android.trackmysleepquality.database.Mondat
import com.example.android.trackmysleepquality.database.MondatDatabase


class ttsprogram : AppCompatActivity() {
    private var mTTS: TextToSpeech? = null
    var menu2: Menu? = null
    var ttstext: EditText? = null
    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.ttsmenu, menu)
        menu2 = menu
        return true
    }

    private fun disableEditText(editText: EditText?) {
        editText?.isFocusable = false
        // editText.isEnabled = false
        editText?.isFocusableInTouchMode = false
        editText?.isCursorVisible = false
        //editText.keyListener = null
        editText?.setBackgroundColor(Color.GRAY)
        val view = this.currentFocus


        view?.let { v ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }

    private fun enableEditText(editText: EditText?) {
        //editText.isFocusable = true
        editText?.isFocusable = true
        editText?.isFocusableInTouchMode = true
        // editText.isEnabled = false
        editText?.isCursorVisible = true
        //editText.keyListener =null
        editText?.setBackgroundColor(Color.TRANSPARENT)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection

        var ttstitle: EditText = findViewById(R.id.ttstitle)
        return when (item.itemId) {
            R.id.edit -> {
                Toast.makeText(this, "edit", Toast.LENGTH_LONG).show()
                menu2?.findItem(R.id.save)?.isVisible = true
                menu2?.findItem(R.id.edit)?.isVisible = false
                enableEditText(ttstext)
                enableEditText(ttstitle)
                true
            }
            R.id.load -> {
                Toast.makeText(this, "load", Toast.LENGTH_LONG).show()
                true
            }
            R.id.save -> {
                Toast.makeText(this, "save", Toast.LENGTH_LONG).show()
                menu2?.findItem(R.id.save)?.isVisible = false
                menu2?.findItem(R.id.edit)?.isVisible = true

                disableEditText(ttstext)
                disableEditText(ttstitle)

                var mondatadatbazis: MondatDatabase = MondatDatabase.getInstance(this)
                ttstext = findViewById(R.id.ttstext)
                ttstitle = findViewById(R.id.ttstitle)
                var teljesszoveg = ttstext?.text.toString()
                var cim = ttstitle?.text.toString()

                //System.out.println("mrk2"+teljesszoveg+cim)
                val mondatokarray = teljesszoveg.split("#")
                // System.out.println("mrk2"+mondatokarray[0])

                mondatokarray.forEachIndexed { j, k ->

                    val mondat5 =   k.substringAfter(System.getProperty("line.separator"),"ures")
                    val fejezet5 =   k.substringBefore(System.getProperty("line.separator"),"ures")
            //   val fejezet5 = k.split("abc")[0]
            // val mondat5 = k.split("[\\\\r\\\\n]+")


                   //val mondat5 = k.substring(k.indexOf("\n")+1)
                  //  val fejezet =   k.substring(0, k.indexOf("\n"))
                    System.out.println("mrk1"+k)
          System.out.println("mrk1"+fejezet5)
        System.out.println("mrk1"+mondat5)
                   // System.out.println("mrk1"+fejezet.get(1))
                    //var fejezetcim = fejezet.get(0)
/*
                    var mondatokstring = fejezetesmondat.get(1)

                   var mondatokarray2 = mondatokstring.split(".")


                   mondatokarray2.forEachIndexed { i, s ->
                       var mondat = Mondat(0, 0, i, s + ".", j, fejezetcim, cim, 0)

                       mondatadatbazis.sleepDatabaseDao.insert(mondat)
                   }
*/
                }


/*
                Toast.makeText(this, "mukodik", Toast.LENGTH_LONG).show()
                var mondatok = mondatadatbazis.sleepDatabaseDao.getAllMondat()

                mondatok.forEach {
                    var fej = it.mondat
                    //Toast.makeText(this,fej,Toast.LENGTH_LONG).show()
                    System.out.println("mrk" + fej)
                }
*/



                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onDestroy() {
        if (mTTS != null) {
            mTTS?.stop()
            mTTS?.shutdown()
        }
        super.onDestroy()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ttsprogram)

        var mSeekBarSpeed: SeekBar = findViewById(R.id.speedseekbar)
        var mSeekBarVolume: SeekBar = findViewById(R.id.volumeseekbar)
        var mSeekBarPitch: SeekBar = findViewById(R.id.pitchseekbar)
        var speakclick: ImageButton = findViewById(R.id.playtts)
        ttstext = findViewById(R.id.ttstext)
        mSeekBarPitch.max = 20
        mSeekBarPitch.progress = 10
        mSeekBarSpeed.max = 40
        mSeekBarSpeed.progress = 10
        mSeekBarVolume.max = 40
        mSeekBarVolume.progress = 10


        mSeekBarSpeed.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                // Display the current progress of SeekBar
                Toast.makeText(applicationContext, i.toString(), Toast.LENGTH_LONG).show()

                mTTS?.setSpeechRate(i.toFloat() / 10)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Do something
                // Toast.makeText(applicationContext, "start tracking", Toast.LENGTH_SHORT).show()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // Do something
                //  Toast.makeText(applicationContext, "stop tracking", Toast.LENGTH_SHORT).show()
            }
        })
        mSeekBarPitch.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                // Display the current progress of SeekBar
                mTTS?.setPitch(i.toFloat() / 10)
                Toast.makeText(applicationContext, i.toString(), Toast.LENGTH_LONG).show()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Do something
                //  Toast.makeText(applicationContext, "start tracking", Toast.LENGTH_SHORT).show()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // Do something
                //  Toast.makeText(applicationContext, "stop tracking", Toast.LENGTH_SHORT).show()
            }
        })
        mSeekBarVolume.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                // Display the current progress of SeekBar

            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Do something
                //   Toast.makeText(applicationContext, "start tracking", Toast.LENGTH_SHORT).show()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // Do something
                //   Toast.makeText(applicationContext, "stop tracking", Toast.LENGTH_SHORT).show()
            }
        })
        mTTS = TextToSpeech(this, TextToSpeech.OnInitListener { status ->

            if (status == TextToSpeech.SUCCESS) {
                val result = mTTS?.setLanguage(Locale.ENGLISH)
                Log.i("ddd", status.toString())
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("TTS", "Language not supported")
                } else {
                    playtts.isEnabled = true
                }
            } else {
                Log.e("TTS", "Initialization failed")
            }
        })
        fun speak() {
            var text = ttstext?.text.toString()
            var pitch = mSeekBarPitch
            mTTS?.speak(text, TextToSpeech.QUEUE_FLUSH, null)
            /*
           val btn = findViewById<View>(R.id.playtts) as ImageButton
            btn.setImageResource()
           */
        }
        speakclick.setOnClickListener {
            speak()

        }


    }
}
