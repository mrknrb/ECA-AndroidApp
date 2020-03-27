package com.example.android.trackmysleepquality

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
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
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.media.MediaPlayer
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.R.attr.name
import android.app.ActivityManager
import android.opengl.Visibility
import android.os.*
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.view.animation.AlphaAnimation
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.text.set
import androidx.core.text.toSpannable
import androidx.core.view.get
import androidx.core.view.isVisible
import kotlin.concurrent.schedule

class ttsprogram : AppCompatActivity() {
    var menu2: Menu? = null
    var fajlcim: String = ""
    var setupallapot = 0
    override fun onBackPressed() {
    }

    fun ttsopen2() {
        val intent = Intent(this, ttsbetoltes::class.java)
        intent.putExtra("setupallapot", setupallapot)
        startActivity(intent)
    }

    fun ttssetupopen() {
        val intent = Intent(this, TTSSetup::class.java)
        intent.putExtra("cim", fajlcim)
        intent.putExtra("setupallapot", setupallapot)

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
    private val mMessageReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            setuploadlayout.isVisible = false
            loadbuttonhatter.setBackgroundResource(R.drawable.gombzold)
            setupbuttonhatter.setBackgroundResource(R.drawable.gombzold)
            // Get extra data included in the Intent
            val aktualismondatindex = intent.getIntExtra("aktualismondatindex", 0)
            val aktualisfejezetindex = intent.getIntExtra("aktualisfejezetindex", 0)
            val bongeszoallapot = intent.getBooleanExtra("bongeszoallapot", true)
            val fejezetekszama = intent.getIntExtra("fejezetekszama", 0)
            val mondatokszama = intent.getIntExtra("mondatokszama", 0)
            val cim = intent.getStringExtra("fajlcim")

            setupallapot = intent.getIntExtra("setupallapot", 0)
            val aktualisfejezet = intent.getStringExtra("aktualisfejezet")
            var aktualisfejezetkiiras: TextView = findViewById(R.id.aktualisfejezetkiiras)
            aktualisfejezetkiiras.text = (aktualisfejezetindex + 1).toString()
            var osszesfejezetkiiras: TextView = findViewById(R.id.osszesfejezetkiiras)
            osszesfejezetkiiras.text = (fejezetekszama + 1).toString()
            var aktualismondatkiiras: TextView = findViewById(R.id.aktualismondatkiiras)
            var osszesmondatkiiras: TextView = findViewById(R.id.osszesmondatkiiras)
            if (bongeszoallapot) {
                aktualismondatkiiras.text = "0"
                osszesmondatkiiras.text = "0"
            } else {
                aktualismondatkiiras.text = (aktualismondatindex + 1).toString()
                osszesmondatkiiras.text = (mondatokszama + 1).toString()
            }
            var fejezetkiiras: TextView = findViewById(R.id.fejezetkiiras)
            fejezetkiiras.text = aktualisfejezet
            if (fajlcim == "") {
                fajlcim = cim
                if (fajlcim != "") {
                    ttstitle.setText(fajlcim)
                    listafrissito(fajlcim, aktualisfejezetindex, true)
                }
            }
            if (bongeszoallapot) {
                var ttstext2: EditText = findViewById(R.id.ttstext2)
                ttstext2.isVisible = false
                var mondatmod: LinearLayout = findViewById(R.id.mondatmod)
                mondatmod.isVisible = false
                //mondatmod.setBackgroundColor(Color.BLACK)
                var fejezetmod: LinearLayout = findViewById(R.id.fejezetmod)
                var fejezetlista: ListView = findViewById(R.id.fejezetlista)
                fejezetlista.isVisible = true
                listafrissito(fajlcim, aktualisfejezetindex, true)
                ttstext2.setText("")
            } else {
                val szovegvisszaallitott = SpannableStringBuilder()
                var mondatadatbazis: MondatDatabase = MondatDatabase.getInstance(applicationContext)
                var mondatokafejezetben = mondatadatbazis.sleepDatabaseDao.getAllMondatFileEsFejezetAlapjan(cim, aktualisfejezet)
                var karakterekszama: Int = 0
                var stringlength: Int = 0
                var szamolja = true
                mondatokafejezetben.forEachIndexed { i, l ->
                    var string = (i + 1).toString() + ". " + l + System.getProperty("line.separator")
                    var spanstring = string.toSpannable()

                    if (i == aktualismondatindex) {
                        stringlength = string.length
                        szovegvisszaallitott.append(spanstring)
                        szamolja = false
                    } else {
                        szovegvisszaallitott.append(spanstring)
                    }
                    if (szamolja) {
                        karakterekszama = karakterekszama + string.length
                    }
                }
//todo görgessen oda a mondathoz, miután módosította
                szovegvisszaallitott.setSpan(BackgroundColorSpan(Color.parseColor("#e7ed3e")), karakterekszama, karakterekszama + stringlength, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                var scrolly = ttstext2.scrollY
                ttstext2.setText(szovegvisszaallitott)
                // Toast.makeText(applicationContext,scrolly.toString(), Toast.LENGTH_LONG).show()
                //ttstext2.scrollTo(0,scrolly)
                // ttstext2.scrollBy(0,scrolly)
                ttstext2.isVisible = true
                //  ttstext2.y=scrolly.toFloat()
                ttstext2.scrollTo(0, scrolly)
                ttstext2.scrollBy(0, scrolly)
                var fejezetmod: LinearLayout = findViewById(R.id.fejezetmod)
                var fejezetlista: ListView = findViewById(R.id.fejezetlista)
                fejezetlista.isVisible = false

                ttstext2.setBackgroundColor(Color.TRANSPARENT)
                var mondatmod: LinearLayout = findViewById(R.id.mondatmod)
                mondatmod.isVisible = true
            }
            fajlcim = cim
        }
    }

    //tts playernek küldés
    fun sendPlayerActions(eventtype: String, value: Int) {
        //Log.d("sender", "Broadcasting message");
        var intent: Intent = Intent("tts_sendPlayerActions");
        // You can also include some extra data.
        intent.putExtra("eventtype", eventtype);
        intent.putExtra("value", value);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    var elozofejezet = 0
    fun listafrissito(message: String, kijeloltfejezet: Int, csakfrissites: Boolean) {
        if (csakfrissites) {
            //todo cseréld ki recyclerviewra, mert a listviewnál nem lehet index szerint get-elni
            //fejezetlista.get(kijeloltfejezet)?.setBackgroundColor(Color.YELLOW)
            //  fejezetlista?.getChildAt(elozofejezet)?.setBackgroundColor(Color.TRANSPARENT)
            //  fejezetlista?.getChildAt(kijeloltfejezet)?.setBackgroundColor(Color.YELLOW)
            //   elozofejezet=kijeloltfejezet
        } else {
            var mainlayout: ConstraintLayout = findViewById(R.id.mainlayout)
            mainlayout.setBackgroundColor(Color.parseColor("#E9E9E9"))//ez nem #9e9e9e
            var mondatadatbazis: MondatDatabase = MondatDatabase.getInstance(this)
            var fejezetek = mondatadatbazis.sleepDatabaseDao.getAllFejezetFileAlapjan(message)
            var fejezetek2 = ArrayList<String>()
            fejezetek.forEachIndexed { j, k ->
                fejezetek2.add((j + 1).toString() + ". " + fejezetek[j])
            }
            val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, fejezetek2)
            var fejezetlista: ListView = findViewById(R.id.fejezetlista)
            fejezetlista.setAdapter(arrayAdapter)
            fejezetlista.setOnItemClickListener(object : AdapterView.OnItemClickListener {
                override fun onItemClick(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                    sendPlayerActions("jumpfejezet", i)
                }
            })
            //  fejezetek[kijeloltfejezet]
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        //tts playertől fogadáshoz kell
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, IntentFilter("tts_state"));

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_ttsprogram)
        var tipus = intent.getStringExtra("tipus")
        var mondatadatbazis: MondatDatabase = MondatDatabase.getInstance(this)
        var ttstext2: EditText = findViewById(R.id.ttstext2)
        ttstext2.isVisible = false
        var ttstitle: EditText = findViewById(R.id.ttstitle)
        var mSeekBarSpeed: SeekBar = findViewById(R.id.speedseekbar)
        var mSeekBarPitch: SeekBar = findViewById(R.id.pitchseekbar)
        var speakclick: ImageButton = findViewById(R.id.playtts)
        var nextbutton: ImageButton = findViewById(R.id.nextbutton)
        var previousbutton: ImageButton = findViewById(R.id.previousbutton)
        var stopbutton: ImageButton = findViewById(R.id.stopbutton)
        var fejezetlista: ListView = findViewById(R.id.fejezetlista)
        val loadbuttonhatter: LinearLayout = findViewById(R.id.loadbuttonhatter)
        val setupbuttonhatter: LinearLayout = findViewById(R.id.setupbuttonhatter)
        val message = intent.getStringExtra("cim")
        setupallapot = intent.getIntExtra("setupallapot", 0)
        if (message != null && message != "") {
            //Toast.makeText(applicationContext, message + " loaded", Toast.LENGTH_LONG).show()
            loadbuttonhatter.setBackgroundResource(R.drawable.gombzold)
            setupbuttonhatter.setBackgroundResource(R.drawable.gombzold)
            fajlcim = message
            speakclick.setBackgroundColor(Color.parseColor("#d32f2f"))
            ttstitle.setText(message)


            listafrissito(message, 0, false)


            mainlayout.setBackgroundColor(Color.parseColor("#9e9e9e"))
        } else if (fajlcim != "") {
            ttstitle.setText(fajlcim)
            listafrissito(message, 0, false)
            mainlayout.setBackgroundColor(Color.parseColor("#9e9e9e"))
            speakclick.setBackgroundColor(Color.parseColor("#d32f2f"))
            loadbuttonhatter.setBackgroundResource(R.drawable.gombzold)
            setupbuttonhatter.setBackgroundResource(R.drawable.gombzold)
        } else {
            var mainlayout: ConstraintLayout = findViewById(R.id.mainlayout)
            mainlayout.setBackgroundColor(Color.parseColor("#9e9e9e"))
            loadbuttonhatter.setBackgroundResource(R.drawable.gombpiros)

            setupbuttonhatter.setBackgroundResource(R.drawable.gombpiros)
        }


        if (tipus == "setup") {
            if (setupallapot == 0) {
                setupbuttonhatter.setBackgroundResource(R.drawable.gombpiros)
            } else if (setupallapot > 2) {
                Toast.makeText(applicationContext, setupallapot.toString(), Toast.LENGTH_LONG).show()

                setupbuttonhatter.setBackgroundResource(R.drawable.gombzold)
            } else {
                setupbuttonhatter.setBackgroundResource(R.drawable.gombsarga)
            }
        } else if (tipus == "load") {
            Toast.makeText(applicationContext, " load", Toast.LENGTH_LONG).show()
        }
        val builder = AlertDialog.Builder(this)
        mSeekBarPitch.max = 30
        mSeekBarPitch.progress = 10
        mSeekBarSpeed.max = 40
        mSeekBarSpeed.progress = 10
        sendPlayerActions("activityrestarted", 0)
        val setupbutton: Button = findViewById(R.id.setupbutton)
        setupbutton.setOnClickListener {
            // setupbuttonhatter.setBackgroundResource(R.drawable.gombzold)
            // loadbuttonhatter.setBackgroundResource(R.drawable.gombpiros)
            ttssetupopen()
        }
        val loadbutton: Button = findViewById(R.id.loadbutton)
        loadbutton.setOnClickListener {
            //todo amikor megy a lejátszó, ne tudjon új fájlt betölteni vagy kérdezzen rá
            ttsopen2()
        }
        mSeekBarSpeed.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                sendPlayerActions("speed", i)
                Toast.makeText(applicationContext, "Speed: " + i.toFloat() / 10, Toast.LENGTH_LONG).show()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })
        mSeekBarPitch.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                Toast.makeText(applicationContext, "Pitch: " + i.toFloat() / 10, Toast.LENGTH_LONG).show()

                sendPlayerActions("pitch", i)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })

        speakclick.setOnClickListener {
            sendPlayerActions("playpause", 0)
            var fejezetkiiras: TextView = findViewById(R.id.fejezetkiiras)
            if (ttstitle.text.toString() == "") {
                builder.setMessage("Please load a file!")
                builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialogInterface, i -> dialogInterface.cancel() })
                builder.show()
            } else if (mondatadatbazis.sleepDatabaseDao.fajlnevfoglalt(ttstitle.text.toString()) == null) {
                builder.setMessage("File not found!")
                builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialogInterface, i -> dialogInterface.cancel() })
                builder.show()
            } else if (fejezetkiiras.text == "") {
                mainlayout.setBackgroundColor(Color.parseColor("#E9E9E9"))
                val serviceIntent = Intent(this, PlayerService::class.java)
                // serviceIntent.putExtra("inputExtra", input)
                serviceIntent.putExtra("cim", ttstitle.text.toString())
                serviceIntent.putExtra("speed", ttstitle.text.toString())
                serviceIntent.putExtra("setupallapot", setupallapot)
                ContextCompat.startForegroundService(this, serviceIntent)
                speakclick.setBackgroundColor(Color.TRANSPARENT)
                var setuploadlayout: LinearLayout = findViewById(R.id.setuploadlayout)
                setuploadlayout.isVisible = false
                listafrissito(ttstitle.text.toString(), 0, false)
            }
        }
        nextbutton.setOnClickListener {
            sendPlayerActions("next", 0)
        }
        previousbutton.setOnClickListener {
            sendPlayerActions("previous", 0)
        }

        fun isMyServiceRunning(serviceClass: Class<*>): Boolean {
            val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            for (service in manager.getRunningServices(Integer.MAX_VALUE)) {
                if (serviceClass.name == service.service.className) {
                    return true
                }
            }
            return false
        }
        stopbutton.setOnClickListener {
            if (isMyServiceRunning(PlayerService::class.java)) {
                builder.setTitle("Are you sure?")
                builder.setMessage("Your reading state will be lost!")
                builder.setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, i -> dialogInterface.cancel() })
                builder.setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->
                    mainlayout.setBackgroundColor(Color.parseColor("#9e9e9e"))
                    speakclick.setBackgroundColor(Color.parseColor("#d32f2f"))
                    var setuploadlayout: LinearLayout = findViewById(R.id.setuploadlayout)
                    setuploadlayout.isVisible = true
                    stopService(Intent(this, PlayerService::class.java))
                    var aktualisfejezetkiiras: TextView = findViewById(R.id.aktualisfejezetkiiras)
                    aktualisfejezetkiiras.text = "0"
                    var osszesfejezetkiiras: TextView = findViewById(R.id.osszesfejezetkiiras)
                    osszesfejezetkiiras.text = "0"
                    var aktualismondatkiiras: TextView = findViewById(R.id.aktualismondatkiiras)
                    var osszesmondatkiiras: TextView = findViewById(R.id.osszesmondatkiiras)

                    aktualismondatkiiras.text = "0"
                    osszesmondatkiiras.text = "0"
                    var fejezetkiiras: TextView = findViewById(R.id.fejezetkiiras)
                    fejezetkiiras.text = ""
                    var ttstext2: EditText = findViewById(R.id.ttstext2)
                    ttstext2.setBackgroundColor(Color.TRANSPARENT)
                    var mondatmod: LinearLayout = findViewById(R.id.mondatmod)
                    mondatmod.setBackgroundColor(Color.TRANSPARENT)
                    var fejezetmod: LinearLayout = findViewById(R.id.fejezetmod)
                    fejezetmod.setBackgroundColor(Color.TRANSPARENT)
                    var fejezetlista: ListView = findViewById(R.id.fejezetlista)
                    fejezetlista.setBackgroundColor(Color.TRANSPARENT)
                    ttstext2.setText("")
                })
                builder.show()
            }
        }
    }
}
