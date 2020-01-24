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
import android.content.*
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
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Handler
import android.os.Message
import androidx.localbroadcastmanager.content.LocalBroadcastManager


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
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);

        super.onDestroy()
    }
    //tts playertől fogadás
    private val mMessageReceiver = object: BroadcastReceiver() {
       override fun onReceive(context:Context, intent:Intent) {
            // Get extra data included in the Intent
            val aktualismondatindex = intent.getIntExtra("aktualismondatindex",0)
           val aktualisfejezetindex = intent.getIntExtra("aktualisfejezetindex",0)
           val bongeszoallapot = intent.getIntExtra("bongeszoallapot",0)



            //Log.d("receiver", "Got message: " + message)
        }
    }
//tts playernek küldés
    fun sendPlayerActions(eventtype:String,value:Int) {
        //Log.d("sender", "Broadcasting message");
        var intent:Intent  =Intent("tts_sendPlayerActions");
        // You can also include some extra data.
        intent.putExtra("eventtype", eventtype);
    intent.putExtra("value", value);
    LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        //tts playertől fogadáshoz kell
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                IntentFilter("tts_state"));


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ttsprogram)
        val message = intent.getStringExtra(EXTRA_MESSAGE)
        var mondatadatbazis: MondatDatabase = MondatDatabase.getInstance(this)
        ttstext = findViewById(R.id.ttstext)
        var ttstitle: EditText = findViewById(R.id.ttstitle)
        var mSeekBarSpeed: SeekBar = findViewById(R.id.speedseekbar)
        var mSeekBarPitch: SeekBar = findViewById(R.id.pitchseekbar)
        var speakclick: ImageButton = findViewById(R.id.playtts)
        var nextbutton: ImageButton = findViewById(R.id.nextbutton)
        var  previousbutton: ImageButton = findViewById(R.id. previousbutton)
        var stopbutton: ImageButton = findViewById(R.id.stopbutton)
        var fejezetlista: ListView = findViewById(R.id.fejezetlista)
        var startbutton: Button = findViewById(R.id.startbutton)


        val builder = AlertDialog.Builder(this)
        mSeekBarPitch.max = 30
        mSeekBarPitch.progress = 10
        mSeekBarSpeed.max = 40
        mSeekBarSpeed.progress = 10
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

                sendPlayerActions("speed",i)
                Toast.makeText(applicationContext, "Speed: "+i.toFloat()/10, Toast.LENGTH_LONG).show()

            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })
        mSeekBarPitch.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                Toast.makeText(applicationContext, "Pitch: "+i.toFloat()/10, Toast.LENGTH_LONG).show()

                sendPlayerActions("pitch",i)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })

        startbutton.setOnClickListener {
            if(ttstitle.text.toString()=="") {
                builder.setMessage("Please load a file!")
                builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialogInterface, i -> dialogInterface.cancel() })
                builder.show()
            }else if(mondatadatbazis.sleepDatabaseDao.fajlnevfoglalt(ttstitle.text.toString())==null){
                builder.setMessage("File not found!")
                builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialogInterface, i -> dialogInterface.cancel() })
                builder.show()

            }else {

                val serviceIntent = Intent(this, PlayerService::class.java)
                // serviceIntent.putExtra("inputExtra", input)
             serviceIntent.putExtra("cim", ttstitle.text.toString())
                serviceIntent.putExtra("speed", ttstitle.text.toString())

                ContextCompat.startForegroundService(this, serviceIntent)
            }

        }
        speakclick.setOnClickListener {

            sendPlayerActions("playpause",0)
        }
        nextbutton.setOnClickListener {

            sendPlayerActions("next",0)
        }
        previousbutton.setOnClickListener {

            sendPlayerActions("previous",0)
        }


        stopbutton.setOnClickListener {
            stopService(Intent(this, PlayerService::class.java))

        }

    }
}
