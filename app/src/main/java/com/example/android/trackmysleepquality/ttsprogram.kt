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
import android.app.AlertDialog
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
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.example.android.trackmysleepquality.database.Mondat
import com.example.android.trackmysleepquality.database.MondatDatabase
import android.content.DialogInterface
import android.content.Intent
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.provider.AlarmClock.EXTRA_MESSAGE


class ttsprogram : AppCompatActivity() {
    private var mTTS: TextToSpeech? = null
    var menu2: Menu? = null
    var ttstext: EditText? = null
    var editlathato = false


    public override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.
        savedInstanceState.putString("Cim", findViewById<EditText>(R.id.ttstitle).text.toString())
        savedInstanceState.putString("Szoveg", findViewById<EditText>(R.id.ttstext).text.toString())
        savedInstanceState.putInt("Pitch", findViewById<SeekBar>(R.id.pitchseekbar).progress)
        savedInstanceState.putInt("Speed", findViewById<SeekBar>(R.id.speedseekbar).progress)
        savedInstanceState.putInt("Volume", findViewById<SeekBar>(R.id.volumeseekbar).progress)
        Toast.makeText(this, findViewById<EditText>(R.id.ttstitle).text.toString(), Toast.LENGTH_LONG).show()
        savedInstanceState.putString("MyString", "Welcome back to Android")
        // etc.
    }

    public override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.
        findViewById<EditText>(R.id.ttstitle).setText(savedInstanceState.getString("Cim"))
        findViewById<EditText>(R.id.ttstext).setText(savedInstanceState.getString("Szoveg"))
        findViewById<SeekBar>(R.id.pitchseekbar).progress = savedInstanceState.getInt("Pitch")
        findViewById<SeekBar>(R.id.speedseekbar).progress = savedInstanceState.getInt("Speed")
        findViewById<SeekBar>(R.id.volumeseekbar).progress = savedInstanceState.getInt("Volume")
        Toast.makeText(this, savedInstanceState.getString("Cim"), Toast.LENGTH_LONG).show()
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

        if (editlathato == true) {
            menu2?.findItem(R.id.save)?.isVisible = false
            menu2?.findItem(R.id.edit)?.isVisible = true
        }



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
                ttsopen2()
                true
            }
            R.id.save -> {
                Toast.makeText(this, "save", Toast.LENGTH_LONG).show()
                var teljesszoveg = ttstext?.text.toString()
                var cim = ttstitle?.text.toString()
                val builder = AlertDialog.Builder(this)
                builder.setCancelable(true)
                var mondatadatbazis: MondatDatabase = MondatDatabase.getInstance(this)
                fun mentes() {
                    var time: Long = System.currentTimeMillis()
                    menu2?.findItem(R.id.save)?.isVisible = false
                    menu2?.findItem(R.id.edit)?.isVisible = true
                    disableEditText(ttstext)
                    disableEditText(ttstitle)
                    ttstext = findViewById(R.id.ttstext)
                    ttstitle = findViewById(R.id.ttstitle)
                    //System.out.println("mrk2"+teljesszoveg+cim)
                    val mondatokarray = teljesszoveg.split("#")
                    // System.out.println("mrk2"+mondatokarray[0])
                    mondatokarray.forEachIndexed { j, k ->
                        val fejezet = k.substringBefore(System.getProperty("line.separator"), "ures")
                        val mondatokstring = k.substringAfter(System.getProperty("line.separator"), "ures")
                        var mondatokarray2 = mondatokstring.split(".")
                        mondatokarray2.forEachIndexed { i, s ->
                            var mondat = Mondat(0, time, i, s + ".", j, fejezet, cim, 0)
                            mondatadatbazis.sleepDatabaseDao.insert(mondat)
                        }
                    }
                    Toast.makeText(this, "mukodik", Toast.LENGTH_LONG).show()
                    var mondatok = mondatadatbazis.sleepDatabaseDao.getAllMondat()
                    mondatok.forEach {
                        var fej = mondatadatbazis.sleepDatabaseDao.fajlnevfoglalt(cim)?.filename
                        //Toast.makeText(this,fej,Toast.LENGTH_LONG).show()
                        System.out.println("mrk" + fej)
                    }
                }
                if (teljesszoveg == "") {
                    builder.setMessage("The text field is empty!")
                    builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialogInterface, i -> dialogInterface.cancel() })
                    builder.show()
                } else if (teljesszoveg.length < 30) {
                    builder.setMessage("The text is too short!")
                    builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialogInterface, i -> dialogInterface.cancel() })
                    builder.show()
                } else if (cim == "") {
                    builder.setMessage("Please give a title!")
                    builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialogInterface, i -> dialogInterface.cancel() })
                    builder.show()
                } else if (mondatadatbazis.sleepDatabaseDao.fajlnevfoglalt(cim)?.filename == cim) {
                    fun kerdes() {
                        builder.setTitle("")
                        builder.setMessage("Are you sure? Your old file and state will be deleted!")
                        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialogInterface, i -> dialogInterface.cancel() })
                        builder.setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->
                            mondatadatbazis.sleepDatabaseDao.deletefile(cim)
                            mentes()
                        })
                        builder.show()
                    }
                    builder.setTitle("The title is taken!")
                    builder.setMessage("Would you like to override it?")
                    builder.setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, i -> dialogInterface.cancel() })
                    builder.setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->
                        kerdes()
                    })
                    builder.show()
                } else {
                    mentes()
                }
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



        if (message == null) {

        } else {
            Toast.makeText(applicationContext, message + " loaded", Toast.LENGTH_LONG).show()
            ttstext = findViewById(R.id.ttstext)
            disableEditText(ttstext)
            disableEditText(ttstitle)
            editlathato = true

            ttstitle.setText(message)
        }


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
