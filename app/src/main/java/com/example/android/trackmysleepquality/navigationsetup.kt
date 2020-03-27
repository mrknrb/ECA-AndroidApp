package com.example.android.trackmysleepquality

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.bluetooth.BluetoothAdapter
import android.content.ComponentName
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.media.AudioManager
import android.widget.LinearLayout
import java.util.*
import kotlin.concurrent.schedule
import android.net.Uri
import android.provider.AlarmClock
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Build
import android.provider.Settings
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.view.inputmethod.InputMethodManager

class navigationsetup : AppCompatActivity() {

    var setup1=false
    var setup2=false
   var setup3=false
    var setup4=false
    var applicatonoverlaypermissionmegadva = false
    fun isThisKeyboardSelected(context: Context): Boolean {
        val defaultIME = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.DEFAULT_INPUT_METHOD)
        if (TextUtils.isEmpty(defaultIME))
            return false
        val defaultInputMethod = ComponentName.unflattenFromString(defaultIME)
       // Toast.makeText(this, defaultInputMethod.getPackageName(), Toast.LENGTH_SHORT).show()

        return defaultInputMethod.getPackageName().equals("com.example.android.trackmysleepquality");
        // return defaultIME.contains("com.example.android.trackmysleepquality")
    }
   fun keyboardenabledcheck():Boolean{
       val packageLocal = getPackageName()
       val isInputDeviceEnabled = false
       val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
       val list = inputMethodManager.getEnabledInputMethodList()
       var enabled=false
       for (inputMethod in list)
       {
           val packageName = inputMethod.getPackageName()
           if (packageName == packageLocal)
           {
               enabled=true
           }
       }
       return enabled

   }
    private fun isMyServiceRunning(serviceClass: Class<*>): Boolean {
        val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }
    fun setupallapotszinezo(layout1:LinearLayout,layout2:LinearLayout,layout3:LinearLayout,layoutback:LinearLayout) {
        if (setup1==false) {
            layout1.setBackgroundResource(R.drawable.gombpiros)
            layoutback.setBackgroundResource(R.drawable.gombszurke)
        } else if (setup2==false) {
            layout2.setBackgroundResource(R.drawable.gombpiros)
            layoutback.setBackgroundResource(R.drawable.gombszurke)
        } else if (setup3==false) {
            layout3.setBackgroundResource(R.drawable.gombpiros)
            layoutback.setBackgroundResource(R.drawable.gombszurke)
        } else {
            layoutback.setBackgroundResource(R.drawable.gombzold)

        }
    }
    lateinit var mainHandler: Handler
    private val updateTextTask = object : Runnable {
        override fun run() {
            // Toast.makeText(this@ChatHeadAccessibilityService1, "Repeat? ", Toast.LENGTH_SHORT).show()

            var layout1 = findViewById<LinearLayout>(R.id.layout1)
            var layout2 = findViewById<LinearLayout>(R.id.layout2)
            var layout3 = findViewById<LinearLayout>(R.id.layout3)
            var layoutback = findViewById<LinearLayout>(R.id.layoutback)
            if (isThisKeyboardSelected(this@navigationsetup)) {

                setup3=true
                layout3.setBackgroundResource(R.drawable.gombzold)
                stopcheck()
            }else if(!isThisKeyboardSelected(this@navigationsetup)){


                layout3.setBackgroundResource(R.drawable.gombszurke)
                setup3=false
            }
            /**ha mindkettő igaz, akkor valszeg ez is igaz, amikor visszalép, így szebb*/
            if(setup3&&setup2){
                layout1.setBackgroundResource(R.drawable.gombzold)
                setup1=true
            }

            setupallapotszinezo(layout1,layout2,layout3,layoutback)
            mainHandler.postDelayed(this, 1000)
        }
    }

    fun stopcheck(){

        mainHandler.removeCallbacks(updateTextTask)
    }

    override fun onResume() {
        super.onResume()

        var layout1 = findViewById<LinearLayout>(R.id.layout1)
        var layout2 = findViewById<LinearLayout>(R.id.layout2)
        var layout3 = findViewById<LinearLayout>(R.id.layout3)
        var layoutback = findViewById<LinearLayout>(R.id.layoutback)


        if(isMyServiceRunning(ChatHeadAccessibilityService1::class.java)){
            layout2.setBackgroundResource(R.drawable.gombzold)
            setup2=true
            setupallapotszinezo(layout1,layout2,layout3,layoutback)
        } else if(!isMyServiceRunning(ChatHeadAccessibilityService1::class.java)){
            layout2.setBackgroundResource(R.drawable.gombszurke)
            setup2=false
            setupallapotszinezo(layout1,layout2,layout3,layoutback)
        }
        if(isThisKeyboardSelected(this)){

            layout3.setBackgroundResource(R.drawable.gombzold)
            setup3=true
            setupallapotszinezo(layout1,layout2,layout3,layoutback)
        }else if(!isThisKeyboardSelected(this)){


            layout3.setBackgroundResource(R.drawable.gombszurke)
            setup3=false
            setupallapotszinezo(layout1,layout2,layout3,layoutback)
        }
    }


    val ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 5469
    fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                applicatonoverlaypermissionmegadva = false
            } else {
                applicatonoverlaypermissionmegadva = true
            }
        }
    }

    fun grantpermission() {
        val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + getPackageName()))
        startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE) {
            if (!Settings.canDrawOverlays(this)) {
                // You don't have permission
                checkPermission()
            } else {
                applicatonoverlaypermissionmegadva = true
                // Do as per your logic
            }
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                this.finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("ServiceCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigationsetup)


        // getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        val builder = AlertDialog.Builder(this)
        /**Bluetooth*/
        val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        var layout1 = findViewById<LinearLayout>(R.id.layout1)
        var layout2 = findViewById<LinearLayout>(R.id.layout2)
        var layout3 = findViewById<LinearLayout>(R.id.layout3)
        var layoutback = findViewById<LinearLayout>(R.id.layoutback)

        setupallapotszinezo(layout1,layout2,layout3,layoutback)
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

            layout1.setBackgroundResource(R.drawable.gombzold)
            setup1=true
            setupallapotszinezo(layout1,layout2,layout3,layoutback)
        }

        var accessibilitysettings: TextView = findViewById(R.id.accessibilitysettings)
        accessibilitysettings.setOnClickListener {
            checkPermission()
            if (applicatonoverlaypermissionmegadva == false) {
                builder.setMessage("First, you have to grant a permission to use the cursor")
                builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialogInterface, i -> dialogInterface.cancel() })
                builder.setPositiveButton("Set", DialogInterface.OnClickListener { dialogInterface, i ->
                    grantpermission()
                })
                builder.show()
            } else {
                startActivityForResult(Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS), 0);
            }
            //   val intent = Intent()
            // intent.action = "com.android.settings.ACTION_ACCESSIBILITY_SETTINGS"
            // intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            //  startActivity(intent)
        }


        fun keyboardsettings() {
            val enableIntent = Intent(Settings.ACTION_INPUT_METHOD_SETTINGS)
            enableIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            this.startActivity(enableIntent)

        }
        if (isThisKeyboardSelected(this)) {
setup3=true
            layout3.setBackgroundResource(R.drawable.gombzold)
        }
        var keyboardon: TextView = findViewById(R.id.keyboardon)
        keyboardon.setOnClickListener {
            //todo csak, ha nincs engeélyezve


            if (!keyboardenabledcheck()) {
                keyboardsettings()
            } else if(!isThisKeyboardSelected(this)){
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showInputMethodPicker()
            }else{
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showInputMethodPicker()
                layout3.setBackgroundResource(R.drawable.gombzold)
                setup3=true
                setupallapotszinezo(layout1,layout2,layout3,layoutback)

            }

        }
        var backbutton: TextView = findViewById(R.id.backbutton)
        backbutton.setOnClickListener {
            // Toast.makeText(this, isThisKeyboardSelected(this).toString(), Toast.LENGTH_SHORT).show()
            // Toast.makeText(this,.toString(), Toast.LENGTH_SHORT).show()

        }

        mainHandler = Handler(Looper.getMainLooper())
        mainHandler.post(updateTextTask)

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()

        mainHandler.removeCallbacks(updateTextTask)
    }
}
