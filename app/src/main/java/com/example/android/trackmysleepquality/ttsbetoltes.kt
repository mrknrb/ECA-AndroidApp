package com.example.android.trackmysleepquality

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import android.widget.AdapterView
import java.util.Arrays.asList
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import com.example.android.trackmysleepquality.database.MondatDatabase
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T






class ttsbetoltes : AppCompatActivity() {


    private lateinit var listView: ListView
    /*
    override fun onCreateContextMenu(menu:ContextMenu , v:ListView , menuInfo: AdapterView.AdapterContextMenuInfo) {
        if (v.getId() == R.id.listview) {
           var lv:ListView  = v
           var acmi: AdapterView.AdapterContextMenuInfo  =  menuInfo
           var obj  =  lv.getItemAtPosition(acmi.position);

            menu.add("One");
            menu.add("Two");
            menu.add("Three");
            menu.add(obj.name);
        }
    }
    */


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ttsbetoltes)
        var mondatadatbazis: MondatDatabase = MondatDatabase.getInstance(this@ttsbetoltes)
        val friendsListView = findViewById<ListView>(R.id.listview)

        val myFriends = mondatadatbazis.sleepDatabaseDao.getAllFile()

        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, myFriends)

        friendsListView.setAdapter(arrayAdapter)

        friendsListView.setOnItemClickListener(object : AdapterView.OnItemClickListener {
            override fun onItemClick(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {

                val intent = Intent(this@ttsbetoltes, ttsprogram::class.java)
               intent.putExtra(EXTRA_MESSAGE, myFriends[i])

                //intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                //intent.setClassName(this@ttsbetoltes,"com.example");
                finish();
                overridePendingTransition(0, 0);
                startActivity(intent);
                overridePendingTransition(0, 0);

               // Toast.makeText(applicationContext,  myFriends[i]+"Opened", Toast.LENGTH_LONG).show()

            }


        })
        friendsListView.setOnItemLongClickListener(object : AdapterView.OnItemLongClickListener {
            override fun onItemLongClick(adapterView: AdapterView<*>, view: View, i: Int, l: Long): Boolean {

                val builder = AlertDialog.Builder(this@ttsbetoltes)
                builder.setCancelable(true)
                builder.setMessage("Do you want to delete this?")
                builder.setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, itt -> dialogInterface.cancel() })
                builder.setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, itt ->


                    mondatadatbazis.sleepDatabaseDao.deletefile(myFriends[i])
                    Toast.makeText(applicationContext,  myFriends[i]+" deleted" , Toast.LENGTH_LONG).show()
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);

                })
                builder.show()

                return true
            }


        })


    }
}
