package com.example.android.trackmysleepquality

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import kotlinx.android.synthetic.main.activity_ttsprogram.*
import android.app.Activity
import android.app.AlertDialog
import android.app.PendingIntent.getActivity
import android.content.Context
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import java.util.*
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.AsyncTask
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.example.android.trackmysleepquality.database.Mondat
import com.example.android.trackmysleepquality.database.MondatDatabase
import android.content.DialogInterface
import android.content.Intent
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.app.ComponentActivity
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.provider.AlarmClock.EXTRA_MESSAGE
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.media.MediaPlayer
import android.widget.*


class ttsprogram : AppCompatActivity() {
    private var mTTS: TextToSpeech? = null
    var menu2: Menu? = null
    var ttstext: EditText? = null
    lateinit var myTts: TextToSpeech
    override fun onBackPressed() {
        var myTts: TextToSpeech
        moveTaskToBack(true)
    }

    fun ttsopen2() {
        val intent = Intent(this, ttsbetoltes::class.java)
        // val editText = findViewById(R.id.editText) as EditText
        //  val message = editText.text.toString()
        //  intent.putExtra(EXTRA_MESSAGE, message)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.ttsmenu, menu)
        menu2 = menu




        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        var mondatadatbazis: MondatDatabase = MondatDatabase.getInstance(this)
        var ttstitle: EditText = findViewById(R.id.ttstitle)
        return when (item.itemId) {
            R.id.edit -> {
                Toast.makeText(this, "edit", Toast.LENGTH_LONG).show()
               // menu2?.findItem(R.id.edit)?.isVisible = false
//todo editnél ugorjon a load activitybe , nyissa meg az adott fájlt esetleg a megfelelő mondathozis ugorhat
                true
            }
            R.id.settings -> {
                val intent = Intent(this, MainActivity::class.java)
                // val editText = findViewById(R.id.editText) as EditText
                //  val message = editText.text.toString()
                //  intent.putExtra(EXTRA_MESSAGE, message)
                startActivity(intent)
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
        val message = intent.getStringExtra(EXTRA_MESSAGE)
        var mondatadatbazis: MondatDatabase = MondatDatabase.getInstance(this)

        myTts = TextToSpeech(applicationContext, TextToSpeech.OnInitListener { status ->
            if (status != TextToSpeech.ERROR) {
                myTts.setLanguage(Locale.US)
            }
        })

        ttstext = findViewById(R.id.ttstext)

        var mSeekBarSpeed: SeekBar = findViewById(R.id.speedseekbar)
        var mSeekBarVolume: SeekBar = findViewById(R.id.volumeseekbar)
        var mSeekBarPitch: SeekBar = findViewById(R.id.pitchseekbar)
        var speakclick: ImageButton = findViewById(R.id.playtts)
        var stopbutton: ImageButton = findViewById(R.id.stopbutton)

        var fejezetlista: ListView = findViewById(R.id.fejezetlista)
        fun listafrissito(){

            val myFriends = mondatadatbazis.sleepDatabaseDao.getAllFejezetFileAlapjan(message)

            val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, myFriends)

            fejezetlista.setAdapter(arrayAdapter)

            fejezetlista.setOnItemClickListener(object : AdapterView.OnItemClickListener {
                override fun onItemClick(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                    //TODO: töltse be a szöveget
                }


            })

        }
        if (message == null) {
        } else {
            Toast.makeText(applicationContext, message + " loaded", Toast.LENGTH_LONG).show()
            ttstitle.setText(message)
            listafrissito()
            //folytasd a szöveg betöltését
        }
        mSeekBarPitch.max = 20
        mSeekBarPitch.progress = 10
        mSeekBarSpeed.max = 40
        mSeekBarSpeed.progress = 10
        mSeekBarVolume.max = 40
        mSeekBarVolume.progress = 10

        val loadbutton:Button=findViewById(R.id.loadbutton)
        loadbutton.setOnClickListener{
            ttsopen2()
        }


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
            //speak()


           // var serviceIntent = Intent(this@ttsprogram, PlayerService::class.java)
           // serviceIntent.putExtra(EXTRA_MESSAGE, "A notification fasza")
           // startService(serviceIntent)

            startService(Intent(this, PlayerService::class.java))

        }
        stopbutton.setOnClickListener {
            //speak()


          //  var serviceIntent = Intent(this@ttsprogram, PlayerService::class.java)
          //  stopService(serviceIntent)

            stopService(Intent(this, PlayerService::class.java))

        }

    }
}
