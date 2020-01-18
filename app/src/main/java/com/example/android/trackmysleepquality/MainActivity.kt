package com.example.android.trackmysleepquality

//import androidx.appcompat.app.AppCompatActivity
import android.Manifest
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.widget.EditText
import android.content.Intent
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.android.trackmysleepquality.database.Mondat
import com.example.android.trackmysleepquality.database.MondatDatabase
import android.content.Context.ACTIVITY_SERVICE
import androidx.core.content.ContextCompat.getSystemService
import android.app.ActivityManager
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.content.Context
import android.view.KeyEvent.KEYCODE_VOLUME_DOWN
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.KeyEvent
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.app.ActivityCompat.requestPermissions
import android.Manifest.permission
import android.Manifest.permission.CALL_PHONE
import android.Manifest.permission.READ_PHONE_STATE
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.provider.Settings
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.app.ActivityCompat.startActivityForResult
import android.os.Build
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.net.Uri
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.telecom.TelecomManager
import android.widget.TextView


class MainActivity : AppCompatActivity() {




   /*
    fun navigation(view: View){
        startService(Intent(this@MainActivity, ChatHeadService::class.java))
    }
*/

    fun ttsopen(view: View) {
        val intent = Intent(this, ttsprogram::class.java)
        startActivity(intent)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (getSystemService(TelecomManager::class.java).defaultDialerPackage != packageName) {
            Intent(TelecomManager.ACTION_CHANGE_DEFAULT_DIALER)
                    .putExtra(TelecomManager.EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME, packageName)
                    .let(::startActivity)
        }



        findViewById<TextView>(R.id.textView6).setOnClickListener(View.OnClickListener {
           // startService(Intent(this@MainActivity, ChatHeadService::class.java))
            val serviceIntent = Intent(this, ChatHeadService::class.java)
            // serviceIntent.putExtra("inputExtra", input)
            ContextCompat.startForegroundService(this, serviceIntent)
            finish()
        })





    }


}
