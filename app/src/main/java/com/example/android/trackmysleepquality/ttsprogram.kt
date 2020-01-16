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
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T


class ttsprogram : AppCompatActivity() {
    var menu2: Menu? = null
    var ttstext: EditText? = null
    override fun onBackPressed() {
    }

    fun ttsopen2() {
        val intent = Intent(this, ttsbetoltes::class.java)
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
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ttsprogram)
        val message = intent.getStringExtra(EXTRA_MESSAGE)
        var mondatadatbazis: MondatDatabase = MondatDatabase.getInstance(this)
        ttstext = findViewById(R.id.ttstext)
        var mSeekBarSpeed: SeekBar = findViewById(R.id.speedseekbar)
        var mSeekBarVolume: SeekBar = findViewById(R.id.volumeseekbar)
        var mSeekBarPitch: SeekBar = findViewById(R.id.pitchseekbar)
        var speakclick: ImageButton = findViewById(R.id.playtts)
        var stopbutton: ImageButton = findViewById(R.id.stopbutton)
        var fejezetlista: ListView = findViewById(R.id.fejezetlista)
        mSeekBarPitch.max = 20
        mSeekBarPitch.progress = 10
        mSeekBarSpeed.max = 40
        mSeekBarSpeed.progress = 10
        mSeekBarVolume.max = 40
        mSeekBarVolume.progress = 10
        fun listafrissito() {
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
        val loadbutton: Button = findViewById(R.id.loadbutton)
        loadbutton.setOnClickListener {
            ttsopen2()
        }
        mSeekBarSpeed.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })
        mSeekBarPitch.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })
        mSeekBarVolume.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })
        speakclick.setOnClickListener {
            val serviceIntent = Intent(this, PlayerService::class.java)
            // serviceIntent.putExtra("inputExtra", input)
            ContextCompat.startForegroundService(this, serviceIntent)
        }
        stopbutton.setOnClickListener {
            stopService(Intent(this, PlayerService::class.java))

        }

    }
}
