package com.example.android.trackmysleepquality

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioManager
import android.widget.LinearLayout
import java.util.*
import kotlin.concurrent.schedule
import android.net.Uri
import android.provider.AlarmClock
import android.widget.Toast

class TTSSetup : AppCompatActivity() {
    var setupallapot = 0
    var fajlcim = ""
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                this.finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ttssetup)
       // getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)

        fajlcim = intent.getStringExtra("cim")
        Toast.makeText(this, fajlcim, Toast.LENGTH_LONG).show()
        setupallapot = intent.getIntExtra("setupallapot", 0)
        /**Bluetooth*/
        val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        var bluetoothbutton: TextView = findViewById(R.id.bluetoothbutton)
        var layout123 = findViewById<LinearLayout>(R.id.layout123)
        var layout455 = findViewById<LinearLayout>(R.id.layout455)
        var layout6 = findViewById<LinearLayout>(R.id.layout6)
        var layout8 = findViewById<LinearLayout>(R.id.layout8)
        var layoutback = findViewById<LinearLayout>(R.id.layoutback)
        var layout7 = findViewById<LinearLayout>(R.id.layout7)
        fun setupallapotszinezo() {
            if (setupallapot == 0) {
                layout123.setBackgroundResource(R.drawable.gombpiros)
            } else if (setupallapot == 1) {
                layout123.setBackgroundResource(R.drawable.gombzold)
                layout455.setBackgroundResource(R.drawable.gombpiros)
            } else if (setupallapot == 2) {
                layout123.setBackgroundResource(R.drawable.gombzold)
                layout455.setBackgroundResource(R.drawable.gombzold)
                layout6.setBackgroundResource(R.drawable.gombpiros)
            } else if (setupallapot == 3) {
                layout123.setBackgroundResource(R.drawable.gombzold)
                layout455.setBackgroundResource(R.drawable.gombzold)
                layout6.setBackgroundResource(R.drawable.gombzold)
                layout7.setBackgroundResource(R.drawable.gombsarga)
                layout8.setBackgroundResource(R.drawable.gombsarga)
                layoutback.setBackgroundResource(R.drawable.gombzold)
            } else if (setupallapot == 4) {
                layout123.setBackgroundResource(R.drawable.gombzold)
                layout455.setBackgroundResource(R.drawable.gombzold)
                layout6.setBackgroundResource(R.drawable.gombzold)
                layout7.setBackgroundResource(R.drawable.gombzold)
                layout8.setBackgroundResource(R.drawable.gombsarga)
                layoutback.setBackgroundResource(R.drawable.gombzold)
            } else if (setupallapot == 5) {
                layout123.setBackgroundResource(R.drawable.gombzold)
                layout455.setBackgroundResource(R.drawable.gombzold)
                layout6.setBackgroundResource(R.drawable.gombzold)
                layout7.setBackgroundResource(R.drawable.gombzold)
                layoutback.setBackgroundResource(R.drawable.gombzold)

                layout8.setBackgroundResource(R.drawable.gombzold)
            }
        }
        setupallapotszinezo()
        bluetoothbutton.setOnClickListener {
            setupallapot = 1
            setupallapotszinezo()
            mBluetoothAdapter.disable()
            //  layout123.setBackgroundColor(Color.parseColor("#5fc51c"))
            Timer("SettingUp55", false).schedule(300) {
                if (mBluetoothAdapter.isEnabled == false) {
                    var audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
                    var minvol = audioManager.getStreamMinVolume(AudioManager.STREAM_MUSIC)
                    // audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, minvol, 0);
                    audioManager.setStreamVolume(
                            AudioManager.STREAM_MUSIC, // Stream type
                            minvol, // Index
                            AudioManager.FLAG_SHOW_UI // Flags
                    );
                    mBluetoothAdapter.enable()
                    //layout123.setBackgroundColor(Color.GREEN)
                    //layout455.setBackgroundColor(Color.RED)
                } else {
                    Timer("SettingU223", false).schedule(500) {
                        if (mBluetoothAdapter.isEnabled == false) {
                            mBluetoothAdapter.disable()
                            var audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
                            var minvol = audioManager.getStreamMinVolume(AudioManager.STREAM_MUSIC)
                            // audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, minvol, 0);
                            audioManager.setStreamVolume(
                                    AudioManager.STREAM_MUSIC, // Stream type
                                    minvol, // Index
                                    AudioManager.FLAG_SHOW_UI // Flags
                            );
                            mBluetoothAdapter.enable()
                            //layout123.setBackgroundColor(Color.GREEN)
                            // layout455.setBackgroundColor(Color.RED)
                        } else {
                            //hibaüzenet
                        }
                    }
                }
            }
        }
        /**Bluetooth settings*/
        var bluetoothsettingspen: TextView = findViewById(R.id.bluetoothsettingspen)
        bluetoothsettingspen.setOnClickListener {
            if (mBluetoothAdapter.isEnabled) {
                val intentOpenBluetoothSettings = Intent()
                intentOpenBluetoothSettings.action = android.provider.Settings.ACTION_BLUETOOTH_SETTINGS
                startActivity(intentOpenBluetoothSettings)
            }
        }
        var done45: TextView = findViewById(R.id.done45)
        done45.setOnClickListener {
            setupallapot = 2
            setupallapotszinezo()
        }
        /**Bluetooth volume max*/
        var bluetoothvolumemax: TextView = findViewById(R.id.bluetoothvolumemax)
        bluetoothvolumemax.setOnClickListener {
            if (mBluetoothAdapter.isEnabled) {
                var audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
                var maxvol = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
                // audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, minvol, 0);
                audioManager.setStreamVolume(
                        AudioManager.STREAM_MUSIC, // Stream type
                        maxvol, // Index
                        AudioManager.FLAG_SHOW_UI // Flags
                );
                setupallapot = 3
                setupallapotszinezo()
            }
        }
        var ttssettings: TextView = findViewById(R.id.ttssettings)
        ttssettings.setOnClickListener {
            val intent = Intent()
            intent.action = "com.android.settings.TTS_SETTINGS"
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            setupallapot = 4
            setupallapotszinezo()
        }
        var volumebooster: TextView = findViewById(R.id.volumebooster)
        volumebooster.setOnClickListener {
            val ctx = this // or you can replace **'this'** with your **ActivityName.this**
            try {
                val i = ctx.packageManager.getLaunchIntentForPackage("feniksenia.app.modulebooster21")
                if (i != null) {
                    ctx.startActivity(i)
                } else {
                    try {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=feniksenia.app.modulebooster21")))
                    } catch (anfe: android.content.ActivityNotFoundException) {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=feniksenia.app.modulebooster21")))
                    }
                }
            } catch (e: PackageManager.NameNotFoundException) {
                // TODO Auto-generated catch block
            }
            setupallapot = 5
            setupallapotszinezo()
        }
        var backbutton: TextView = findViewById(R.id.backbutton)
        backbutton.setOnClickListener {
            val intent = Intent(this@TTSSetup, ttsprogram::class.java)
            intent.putExtra("tipus", "setup");

            intent.putExtra("cim", fajlcim);

            intent.putExtra("setupallapot", setupallapot);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            //intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            //intent.setClassName(this@ttsbetoltes,"com.example");
            // finish();
            // overridePendingTransition(0, 0);
            startActivity(intent);
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@TTSSetup, ttsprogram::class.java)
        intent.putExtra("tipus", "setup");

        intent.putExtra("cim", fajlcim);

        intent.putExtra("setupallapot", setupallapot);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        //intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        //intent.setClassName(this@ttsbetoltes,"com.example");
        // finish();
        // overridePendingTransition(0, 0);
        startActivity(intent);

    }
    override fun onDestroy() {
        super.onDestroy()


}
}
