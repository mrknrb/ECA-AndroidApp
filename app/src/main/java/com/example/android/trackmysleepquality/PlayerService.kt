package com.example.android.trackmysleepquality

import android.app.Notification
import android.app.Service
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.speech.tts.TextToSpeech
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.widget.Toast

import androidx.core.app.NotificationCompat

import com.example.android.trackmysleepquality.database.Mondat
import com.example.android.trackmysleepquality.database.MondatDatabase
import java.util.Locale

import android.provider.AlarmClock.EXTRA_MESSAGE
import com.example.android.trackmysleepquality.App.CHANNEL_ID
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.R.attr.name
import android.graphics.PixelFormat
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.R.attr.name
import android.os.Build
import android.view.*
import android.telephony.TelephonyManager
import android.telephony.PhoneStateListener
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.R.attr.name
import android.content.*
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.android.trackmysleepquality.OngoingCall.state


class PlayerService : Service(), OnAudioVolumeChangedListener, AudioManager.OnAudioFocusChangeListener {
    private var mediaSession: MediaSessionCompat? = null
    internal lateinit var myTts: TextToSpeech
    var phoneStateListener: PhoneStateListener? = null
    internal var fejezetekszama: Int = -1
    internal var mondatokszama: Int = -1
    internal var aktualisfejezetindex = 0
    internal var aktualismondatindex = 0
    internal lateinit var fejezetek: List<String>
    internal lateinit var mondatok: List<String>
    internal var bongeszoallapot = true
    internal var cim: String = ""
    lateinit var audioManager: AudioManager
    var mondatadatbazis: MondatDatabase = MondatDatabase.getInstance(this)
    override fun onAudioVolumeChanged(currentVolume: Int, maxVolume: Int) {
        Toast.makeText(applicationContext, "volume changed", Toast.LENGTH_SHORT).show()

    }

    override fun onAudioFocusChange(focusState: Int) {
        //Invoked when the audio focus of the system is updated.
        /* val mMediaPlayer: MediaPlayer
         mMediaPlayer = MediaPlayer.create(applicationContext, R.raw.silent_sound)
         mMediaPlayer.setOnCompletionListener { mMediaPlayer.release() }*/

        when (focusState) {
            AudioManager.AUDIOFOCUS_GAIN -> {
                /*todo ha csak simán elveszíti a fókuszt, majd visszaszerzi, ne nyissa meg*/
                /*todo az audiofocust stabilan, minden esetben szerezze vissza*/
                // valtofunction()
            }
            AudioManager.AUDIOFOCUS_LOSS -> {
                //requestAudioFocus()
            }
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> {
                // requestAudioFocus()
            }
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK -> {
                //  requestAudioFocus()
            }
        }
    }

    private fun requestAudioFocus(): Boolean {
        val mMediaPlayer: MediaPlayer
        mMediaPlayer = MediaPlayer.create(applicationContext, R.raw.silent_sound)
        mMediaPlayer.setOnCompletionListener { mMediaPlayer.release() }
        mMediaPlayer.start()
        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val result = audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN)
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            //Focus gained
            return true
        }
        //Could not gain focus
        // requestAudioFocus()
        return false
    }

    private fun removeAudioFocus(): Boolean {
        return (AudioManager.AUDIOFOCUS_REQUEST_GRANTED === audioManager.abandonAudioFocus(this))
    }

    var kozepsoduplakattelozoido = 0L
    fun valtofunction() {
        if (bongeszoallapot) {
            mondatokszama=-1
            aktualismondatindex=0
            mondatok = mondatadatbazis.sleepDatabaseDao.getAllMondatFileEsFejezetAlapjan(cim, fejezetek[aktualisfejezetindexellenorzo()])
            for (mondat in mondatok) {
                System.out.println("mrk" + mondat)

                mondatokszama = mondatokszama + 1
            }
            myTts.stop()
            // myTts.speak("Opening" + aktualisfejezetszoveg.toString() + "of" + fejezetszamaszoveg.toString() + fejezetek[aktualisfejezetindex], TextToSpeech.QUEUE_FLUSH, null)
            myTts.speak("Opening file", TextToSpeech.QUEUE_FLUSH, null)
            bongeszoallapot = false
        } else {
            var aktualisfejezetszoveg = aktualisfejezetindex + 1
            var fejezetszamaszoveg = fejezetekszama + 1
            myTts.stop()
            // myTts.speak("Closing" + aktualisfejezetszoveg.toString() + "of" + fejezetszamaszoveg.toString() + fejezetek[aktualisfejezetindex], TextToSpeech.QUEUE_FLUSH, null)
            myTts.speak("Closing file", TextToSpeech.QUEUE_FLUSH, null)

            bongeszoallapot = true
        }

    }

    fun playpause() {
        var kozepsoduplakattjelenido = System.currentTimeMillis()
        sendState();
        if ((kozepsoduplakattjelenido - kozepsoduplakattelozoido) > 800) {
            if (bongeszoallapot) {
                myTts.stop()
                myTts.speak((aktualisfejezetindex + 1).toString() + "of" + (fejezetekszama + 1).toString().plus(",") + fejezetek[aktualisfejezetindexellenorzo()], TextToSpeech.QUEUE_FLUSH, null)

            } else {
                myTts.stop()
                myTts.speak((aktualismondatindex + 1).toString() + "of" + (mondatokszama + 1).toString().plus(",") + mondatok[aktualismondatindexellenorzo()], TextToSpeech.QUEUE_FLUSH, null)
            }
        } else {
            valtofunction()
        }
        kozepsoduplakattelozoido = System.currentTimeMillis()
    }
   fun aktualismondatindexellenorzo(): Int {
      if(aktualismondatindex<0){
          return 0
      }else if(aktualismondatindex>mondatokszama){
          return mondatokszama
      }else{ return aktualismondatindex

      }
   }
    fun aktualisfejezetindexellenorzo(): Int {
        if(aktualisfejezetindex<0){
            return 0
        }else if(aktualisfejezetindex>fejezetekszama){
            return fejezetekszama
        }else{ return aktualisfejezetindex

        }
    }
    fun next() {

        if (bongeszoallapot) {

            if (aktualisfejezetindex < fejezetekszama) {
                aktualisfejezetindex++
                myTts.stop()
                myTts.speak((aktualisfejezetindex + 1).toString() + "of" + (fejezetekszama + 1).toString().plus(",") + fejezetek[aktualisfejezetindexellenorzo()], TextToSpeech.QUEUE_FLUSH, null)

            } else {
                myTts.stop()
                myTts.speak("Last Part:" + (aktualisfejezetindex + 1).toString() + "of" + (fejezetekszama + 1).toString().plus(",") + fejezetek[aktualisfejezetindexellenorzo()], TextToSpeech.QUEUE_FLUSH, null)
            }
        } else {
            if (aktualismondatindex < mondatokszama) {
                aktualismondatindex++
                myTts.stop()
               //todo java.lang.IndexOutOfBoundsException: Index: 7, Size: 5 összeomlott

                myTts.speak((aktualismondatindex + 1).toString() + "of" + (mondatokszama + 1).toString().plus(",") + mondatok[aktualismondatindexellenorzo()], TextToSpeech.QUEUE_FLUSH, null)

            } else {
                myTts.stop()
                myTts.speak("Last Sentence:" + (aktualismondatindex + 1).toString() + "of" + (mondatokszama + 1).toString().plus(",") + mondatok[aktualismondatindexellenorzo()], TextToSpeech.QUEUE_FLUSH, null)
            }
        }


    }

    fun previous() {
        if (bongeszoallapot) {
            if (aktualisfejezetindex > 0) {
                aktualisfejezetindex--
                myTts.stop()
                myTts.speak((aktualisfejezetindex + 1).toString() + "of" + (fejezetekszama + 1).toString().plus(",") + fejezetek[aktualisfejezetindexellenorzo()], TextToSpeech.QUEUE_FLUSH, null)
            } else {
                myTts.stop()
                myTts.speak("First Part:" + (aktualisfejezetindex + 1).toString() + "of" + (fejezetekszama + 1).toString().plus(",") + fejezetek[aktualisfejezetindexellenorzo()], TextToSpeech.QUEUE_FLUSH, null)
            }
        } else {
            if (aktualismondatindex > 0) {
                aktualismondatindex--
                myTts.stop()
                myTts.speak((aktualismondatindex + 1).toString() + "of" + (mondatokszama + 1).toString().plus(",") + mondatok[aktualismondatindexellenorzo()], TextToSpeech.QUEUE_FLUSH, null)
            } else {
                myTts.stop()
                myTts.speak("First Sentence:" + (aktualismondatindex + 1).toString() + "of" + (mondatokszama + 1).toString().plus(",") + mondatok[aktualismondatindexellenorzo()], TextToSpeech.QUEUE_FLUSH, null)
            }
        }
    }

    private val mBinder = LocalBinder()

    private val mMediaSessionCallback = object : MediaSessionCompat.Callback() {
        override fun onMediaButtonEvent(mediaButtonEvent: Intent): Boolean {
            val intentAction = mediaButtonEvent.action
            if (Intent.ACTION_MEDIA_BUTTON == intentAction) {
                val event = mediaButtonEvent.getParcelableExtra<KeyEvent>(
                        Intent.EXTRA_KEY_EVENT)
                        ?: return super.onMediaButtonEvent(mediaButtonEvent)
                val keycode = event.keyCode
                val action = event.action
                if (event.repeatCount == 0 && action == KeyEvent.ACTION_DOWN) {

                    when (keycode) {
                        // Do what you want in here
                        KeyEvent.KEYCODE_MEDIA_PLAY -> {
                            Toast.makeText(applicationContext, "play", Toast.LENGTH_SHORT).show()


                            /*
                                // Stupid Android 8 "Oreo" hack to make media buttons work
                                final MediaPlayer mMediaPlayer;
                                mMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.silent_sound);
                                mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                    @Override
                                    public void onCompletion(MediaPlayer mediaPlayer) {
                                        mMediaPlayer.release();
                                    }
                                });
                                mMediaPlayer.start();
*/
                            // myTts.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                            playpause()
                        }
                        KeyEvent.KEYCODE_MEDIA_PAUSE -> {
                            Toast.makeText(applicationContext, "pause", Toast.LENGTH_SHORT).show()
                            playpause()
                        }
                        KeyEvent.KEYCODE_MEDIA_NEXT -> {
                            /***megcseréltem a nextet a previoussal*/
                            previous()
                            Toast.makeText(applicationContext, "prev", Toast.LENGTH_SHORT).show()
                        }

                        KeyEvent.KEYCODE_MEDIA_PREVIOUS -> {
                            next()
                            Toast.makeText(applicationContext, "next", Toast.LENGTH_SHORT).show()
                        }


                    }
                    /***ez fingom sics, hogy mi*/
                   // startService(Intent(applicationContext, PlayerService::class.java)

                   // )
                    return true
                }
            }
            return false
        }
    }


    inner class LocalBinder : Binder() {
        internal val service: PlayerService
            get() = this@PlayerService
    }

    private fun callStateListener() {
        // Get the telephony manager
        val telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        //Starting listening for PhoneState changes

        requestAudioFocus()
        /*val mMediaPlayer2: MediaPlayer
       mMediaPlayer2 = MediaPlayer.create(applicationContext, R.raw.silent_sound)
       mMediaPlayer2.setOnCompletionListener { mMediaPlayer2.release() }
*/
        phoneStateListener = object : PhoneStateListener() {
            override fun onCallStateChanged(state: Int, incomingNumber: String) {

                // requestAudioFocus()
                // myTts.speak("Opening file Opening file Opening file", TextToSpeech.QUEUE_FLUSH, null)

                when (state) {

                    //if at least one call exists or the phone is ringing
                    //pause the MediaPlayer
                    TelephonyManager.CALL_STATE_OFFHOOK, TelephonyManager.CALL_STATE_RINGING -> {

                        Toast.makeText(applicationContext, "1", Toast.LENGTH_SHORT).show()
                        valtofunction()
                        requestAudioFocus()
                        // requestAudioFocus()

                        // mMediaPlayer2.start()
                    }
                    TelephonyManager.CALL_STATE_IDLE -> {
                        // Phone idle. Start playing.
                        // requestAudioFocus()
                        // mMediaPlayer2.start()
                        //myTts.speak("bdfb gbdfgbdfgb dbgfbdfgbd dfgbdfgbd", TextToSpeech.QUEUE_FLUSH, null)
                        Toast.makeText(applicationContext, "2", Toast.LENGTH_SHORT).show()
                        requestAudioFocus()
                    }


                }
            }
        }
        // Register the listener with the telephony manager
        // Listen for changes to the device call state.
        telephonyManager.listen(phoneStateListener,
                PhoneStateListener.LISTEN_CALL_STATE)
    }

    //tts programnak küldés
    fun sendState() {
        //Log.d("sender", "Broadcasting message");
        var intent: Intent = Intent("tts_state");
        // You can also include some extra data.
        intent.putExtra("aktualismondatindex", aktualismondatindex);
        intent.putExtra("aktualisfejezetindex", aktualisfejezetindex);
        intent.putExtra("bongeszoallapot", bongeszoallapot);



        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    //tts programtól fogadás
    private val mMessageReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            // Get extra data included in the Intent
            val eventtype = intent.getStringExtra("eventtype")
            val value = intent.getIntExtra("value", 0)

            if(eventtype=="speed"){
                myTts.setSpeechRate(value.toFloat()/10)
            }else if(eventtype=="pitch"){
                myTts.setPitch(value.toFloat()/10)
            }else if(eventtype=="playpause"){
                playpause()
            }else if(eventtype=="next"){
                next()
            }else if(eventtype=="previous"){
                previous()
            }



            //Log.d("receiver", "Got message: " + message)
        }
    }

    override fun onCreate() {
        super.onCreate()
        //tts programtól fogadáshoz
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                IntentFilter("tts_sendPlayerActions"));

        //Handle incoming phone calls
        callStateListener()
        /*
        var mView = HUDView(this)

        var LAYOUT_FLAG: Int
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_PHONE;
        }


        val params = WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                LAYOUT_FLAG,
                0,
                //              WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                //                      | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT)
        params.gravity = Gravity.RIGHT or Gravity.TOP
        params.title = "Load Average"
        val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        wm.addView(mView, params)
*/

        myTts = TextToSpeech(applicationContext, TextToSpeech.OnInitListener { status ->
            if (status != TextToSpeech.ERROR) {
                myTts.language = Locale.US
                myTts.setSpeechRate(1F)
            }
        })

        val receiver = ComponentName(packageName, RemoteReceiver::class.java.name)
        mediaSession = MediaSessionCompat(this, "PlayerService", receiver, null)
        mediaSession!!.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS or MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS)
        mediaSession!!.setPlaybackState(PlaybackStateCompat.Builder()
                .setState(PlaybackStateCompat.STATE_PAUSED, 0, 0f)
                .setActions(PlaybackStateCompat.ACTION_PLAY_PAUSE)
                .build())
        mediaSession!!.setCallback(mMediaSessionCallback)

        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        audioManager.requestAudioFocus({
            // Ignore
            // MainActivity.showText("focusChange=" + focusChange);
        }, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN)
        Toast.makeText(applicationContext, "elindult", Toast.LENGTH_SHORT).show()

        mediaSession!!.isActive = true
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {


        // Toast.makeText(applicationContext, intent.getStringExtra("cim"), Toast.LENGTH_SHORT).show()

        //List<Mondat> mondatok=mondatadatbazis.getSleepDatabaseDao().getAllMondatObjectFileAlapjan(cim);
        //public void ttsfunction(cim)

        // ttsEngineMrk.cim= ;
        if (cim == "") {
            cim = intent.getStringExtra("cim")
            var cim2 = intent.getStringExtra("cim")
            fejezetek = mondatadatbazis.sleepDatabaseDao.getAllFejezetFileAlapjan(cim2)
            for (fejezet in fejezetek) {
                fejezetekszama = fejezetekszama + 1
            }

            // System.out.println("mrkfejezetekszama" + fejezetekszama)
            // System.out.println("mrkmondatokszama" + mondatokszama)
            //  System.out.println("mrk1.mondat" + mondatok[0])

            //  Toast.makeText(getApplicationContext(), ttsEngineMrk.cim, Toast.LENGTH_SHORT).show();

            if (mediaSession!!.controller.playbackState.state == PlaybackStateCompat.STATE_PLAYING) {
                mediaSession!!.setPlaybackState(PlaybackStateCompat.Builder()
                        .setState(PlaybackStateCompat.STATE_PAUSED, 0, 0.0f)
                        .setActions(PlaybackStateCompat.ACTION_PLAY_PAUSE).build())
            } else {
                mediaSession!!.setPlaybackState(PlaybackStateCompat.Builder()
                        .setState(PlaybackStateCompat.STATE_PLAYING, 0, 1.0f)
                        .setActions(PlaybackStateCompat.ACTION_PLAY_PAUSE).build())
            }

            val largeIcon = BitmapFactory.decodeResource(resources, R.drawable.ecalogo)
            val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
                    .setContentTitle("Text to Speech")
                    .setSmallIcon(R.drawable.ic_launcher_sleep_tracker_foreground)
                    //.setContentText(input)
                    .setColor(Color.RED)
                    .setLargeIcon(largeIcon)
                    .addAction(R.drawable.ic_sleep_0, "Dislike", null)
                    .addAction(R.drawable.ic_sleep_0, "Previous", null)
                    .addAction(R.drawable.ic_sleep_0, "Pause", null)
                    .addAction(R.drawable.ic_sleep_0, "Next", null)
                    .addAction(R.drawable.ic_sleep_1, "Like", null)
                    .setStyle(androidx.media.app.NotificationCompat.MediaStyle()
                            .setShowActionsInCompactView(2, 3, 4)
                    )
                    .setSubText("Sub Text")
                    .setPriority(NotificationCompat.PRIORITY_LOW)
                    .build()

            startForeground(1, notification)

            // lehet, hogy nem kell, de ez a néma hangos trükk
            val mMediaPlayer: MediaPlayer
            mMediaPlayer = MediaPlayer.create(applicationContext, R.raw.silent_sound)
            mMediaPlayer.setOnCompletionListener { mMediaPlayer.release() }
            mMediaPlayer.start()
        }
        return Service.START_NOT_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(applicationContext, "vege", Toast.LENGTH_SHORT).show()
        Toast.makeText(getBaseContext(), "onDestroy", Toast.LENGTH_LONG).show();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        //if (mChatHeadView != null) mWindowManager.removeView(mChatHeadView)
        // mediaSession!!.release()
    }
/*
    companion object {
        val TAG = "MPS"
        internal var mondatadatbazis: MondatDatabase? = null
    }*/
}