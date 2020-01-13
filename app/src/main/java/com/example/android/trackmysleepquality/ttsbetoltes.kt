package com.example.android.trackmysleepquality

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.widget.*
import com.example.android.trackmysleepquality.database.Mondat


class ttsbetoltes : AppCompatActivity() {
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                this.finish()

                Toast.makeText(applicationContext, "vissza", Toast.LENGTH_LONG).show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

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
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
        val ttstext = findViewById<EditText>(R.id.ttstext)
        val ttstitle = findViewById<EditText>(R.id.ttstitle)
        val savebutton = findViewById<Button>(R.id.savebutton)
        var ttstextModositasElott0 = findViewById<EditText>(R.id.ttstext)
        var ttstextModositasElott = ttstextModositasElott0.text.toString()
        fun listafrissito() {

            val myFriends = mondatadatbazis.sleepDatabaseDao.getAllFile()

            val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, myFriends)

            friendsListView.setAdapter(arrayAdapter)

            friendsListView.setOnItemClickListener(object : AdapterView.OnItemClickListener {
                override fun onItemClick(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {

                    fun szovegvisszaallito() {
                        val szovegvisszaallitott = StringBuilder()
                        ttstitle.setText(myFriends[i])
                        szovegvisszaallitott.toString()
                        //todo szöveg betöltő
                        var fejezetek = mondatadatbazis.sleepDatabaseDao.getAllFejezetFileAlapjan(myFriends[i])
                        fejezetek.forEachIndexed { j, k ->
                            szovegvisszaallitott.append("#" + k + System.getProperty("line.separator"))
                            var mondatokafejezetben = mondatadatbazis.sleepDatabaseDao.getAllMondatFileEsFejezetAlapjan(myFriends[i], k)
                            mondatokafejezetben.forEachIndexed { i, l ->
                                szovegvisszaallitott.append(l)
                            }
                        }
                        ttstext.setText(szovegvisszaallitott.toString())
                        //  overridePendingTransition(0, 0);
                        // Toast.makeText(applicationContext,  myFriends[i]+"Opened", Toast.LENGTH_LONG).show()
                       // ttstextModositasElott=szovegvisszaallitott.toString()
                    }

                    if (ttstextModositasElott == findViewById<EditText>(R.id.ttstext).text.toString()) {
                        szovegvisszaallito()
                    } else {

                        val builder = AlertDialog.Builder(this@ttsbetoltes)
                        builder.setMessage("Would you like to discard your modifications?")
                        builder.setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, i -> dialogInterface.cancel() })
                        builder.setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->
                            szovegvisszaallito()
                        })
                        builder.show()
                    }
                   ttstextModositasElott = findViewById<EditText>(R.id.ttstext).text.toString()

                }
            })
            friendsListView.setOnItemLongClickListener(object : AdapterView.OnItemLongClickListener {
                override fun onItemLongClick(adapterView: AdapterView<*>, view: View, i: Int, l: Long): Boolean {

                    val builder = AlertDialog.Builder(this@ttsbetoltes)
                    builder.setCancelable(true)
                    //builder.setMessage("Do you want to delete this?")
                    builder.setNegativeButton("Edit", DialogInterface.OnClickListener { dialogInterface, itt ->

                        ttstitle.setText(myFriends[i])

                    })
                    builder.setPositiveButton("Delete", DialogInterface.OnClickListener { dialogInterface, itt ->


                        mondatadatbazis.sleepDatabaseDao.deletefile(myFriends[i])
                        Toast.makeText(applicationContext, myFriends[i] + " deleted", Toast.LENGTH_LONG).show()
                        listafrissito()
                        /*
                         finish();
                         overridePendingTransition(0, 0);
                         startActivity(getIntent());
                         overridePendingTransition(0, 0);
                         */
                    })
                    builder.show()
                    return true
                }
            })
        }
        savebutton.setOnClickListener {
            Toast.makeText(this, "save", Toast.LENGTH_LONG).show()
            var teljesszoveg = ttstext?.text.toString()
            var cim = ttstitle?.text.toString()
            val builder = AlertDialog.Builder(this)
            builder.setCancelable(true)

            fun mentes() {
                var time: Long = System.currentTimeMillis()
                //System.out.println("mrk2"+teljesszoveg+cim)
                val mondatokarray = teljesszoveg.split("#")
                // System.out.println("mrk2"+mondatokarray[0])
                mondatokarray.forEachIndexed { j, k ->
                    if (k != "") {
                        System.out.println("mrknrb" + k)
                        val fejezet = k.substringBefore(System.getProperty("line.separator"), "ures")
                        val mondatokstring = k.substringAfter(System.getProperty("line.separator"), "ures")
                        var mondatokarray2 = mondatokstring.split(".")
                        mondatokarray2.forEachIndexed { i, s ->
                            if (s != "") {
                                var mondat = Mondat(0, time, i, s + ".", j, fejezet, cim, 0)
                                mondatadatbazis.sleepDatabaseDao.insert(mondat)
                            }
                        }
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

            fun load(cim: String) {
                val intent = Intent(this@ttsbetoltes, ttsprogram::class.java)
                intent.putExtra(EXTRA_MESSAGE, cim)
                intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP)
                //intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                //intent.setClassName(this@ttsbetoltes,"com.example");
                // finish();
                // overridePendingTransition(0, 0);
                startActivity(intent);
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
            } else if (mondatadatbazis.sleepDatabaseDao.fajlnevfoglalt(cim)?.filename == cim && ttstextModositasElott != findViewById<EditText>(R.id.ttstext).text.toString()) {
                builder.setTitle("The title is taken!")
                builder.setMessage("Would you like to override it? Your old file will be deleted!")
                builder.setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, i -> dialogInterface.cancel() })
                builder.setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->
                    mondatadatbazis.sleepDatabaseDao.deletefile(cim)
                    mentes()
                    load(cim)
                })
                builder.show()
            } else {
                mentes()
                load(cim)

            }
            true
        }


        listafrissito()
    }
}
