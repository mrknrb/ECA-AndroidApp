package com.example.android.trackmysleepquality

//import androidx.appcompat.app.AppCompatActivity
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




class MainActivity : AppCompatActivity() {

    private fun isMyServiceRunning(serviceClass: Class<*>): Boolean {
        val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }


    fun ttsopen(view: View) {
        val intent = Intent(this, ttsprogram::class.java)
       // val editText = findViewById(R.id.editText) as EditText
      //  val message = editText.text.toString()
      //  intent.putExtra(EXTRA_MESSAGE, message)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       if( isMyServiceRunning(TTSService::class.java)==true){

           val intent = Intent(this, ttsprogram::class.java)
           // val editText = findViewById(R.id.editText) as EditText
           //  val message = editText.text.toString()
           //  intent.putExtra(EXTRA_MESSAGE, message)

           finish();
           startActivity(intent)
       }


/*
        val db = Room.databaseBuilder(
            applicationContext,
            Adatbazis::class.java, "Adatbazis"
        ).build()
        val mondat= Mondat(0,4,"dfgdfgg",3,"hsgfhdfgh","dfbdfb",4)
db.adatbazisdao.insert(mondat)

        val testmondat:Mondat?
        testmondat  =db.adatbazisdao.utolsomondat()
System.out.println(testmondat)
        */
    }
}
