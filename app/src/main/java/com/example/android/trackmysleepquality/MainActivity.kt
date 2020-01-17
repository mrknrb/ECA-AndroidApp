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
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.app.ActivityCompat.requestPermissions
import android.Manifest.permission
import android.Manifest.permission.CALL_PHONE
import android.Manifest.permission.READ_PHONE_STATE
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.provider.Settings


class MainActivity : AppCompatActivity() {

    fun navigation(view: View){
        startActivity( Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION))

    }


    fun ttsopen(view: View) {
        val intent = Intent(this, ttsprogram::class.java)
        startActivity(intent)
    }
    private val PERMISSION_REQUEST_READ_PHONE_STATE = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_DENIED) {
                val permissions = arrayOf<String>(Manifest.permission.READ_PHONE_STATE, Manifest.permission.CALL_PHONE)
                requestPermissions(permissions, PERMISSION_REQUEST_READ_PHONE_STATE)
            }
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_REQUEST_READ_PHONE_STATE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission granted: $PERMISSION_REQUEST_READ_PHONE_STATE", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Permission NOT granted: $PERMISSION_REQUEST_READ_PHONE_STATE", Toast.LENGTH_SHORT).show()
                }

                return
            }
        }
    }

}
