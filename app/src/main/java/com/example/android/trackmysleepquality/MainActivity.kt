package com.example.android.trackmysleepquality

//import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.widget.EditText
import android.content.Intent
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.database.Mondat
import com.example.android.trackmysleepquality.database.MondatDatabase


class MainActivity : AppCompatActivity() {







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
