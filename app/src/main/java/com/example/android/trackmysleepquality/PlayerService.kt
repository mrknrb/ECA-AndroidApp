package com.example.android.trackmysleepquality

import android.app.Notification
import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
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


class PlayerService : Service(), OnAudioVolumeChangedListener {
    private var mediaSession: MediaSessionCompat? = null
    internal lateinit var myTts: TextToSpeech


    internal var fejezetekszama: Int = -1
    internal var mondatokszama: Int = -1
    internal var aktualisfejezetindex = 0
    internal var aktualismondatindex = 0
    internal lateinit var fejezetek: List<String>
    internal lateinit var mondatok: List<String>
    internal var bongeszoallapot = true
    internal var cim: String = ""
    var mondatadatbazis: MondatDatabase = MondatDatabase.getInstance(this)
    override fun onAudioVolumeChanged(currentVolume: Int, maxVolume: Int) {
        Toast.makeText(applicationContext, "play", Toast.LENGTH_SHORT).show()

    }

    var kozepsoduplakattelozoido = 0L
    fun playpause() {

        var kozepsoduplakattjelenido = System.currentTimeMillis()
        if ((kozepsoduplakattjelenido - kozepsoduplakattelozoido) > 800) {
            if (bongeszoallapot) {

                myTts.speak((aktualisfejezetindex + 1).toString() + fejezetek[aktualisfejezetindex], TextToSpeech.QUEUE_FLUSH, null)

            } else {

                myTts.speak(mondatok[aktualismondatindex], TextToSpeech.QUEUE_FLUSH, null)


            }

        } else {
            if (bongeszoallapot) {
                mondatok = mondatadatbazis.sleepDatabaseDao.getAllMondatFileEsFejezetAlapjan(cim, fejezetek[aktualisfejezetindex])
                for (mondat in mondatok) {
                    System.out.println("mrk" + mondat)
                    mondatokszama = mondatokszama + 1
                }


                var aktualisfejezetszoveg = aktualisfejezetindex + 1
                var fejezetszamaszoveg = fejezetekszama + 1
                myTts.stop()
                myTts.speak("Opening" + aktualisfejezetszoveg.toString() + "Out of" + fejezetszamaszoveg.toString() + fejezetek[aktualisfejezetindex], TextToSpeech.QUEUE_FLUSH, null)

                bongeszoallapot = false
            } else {
                var aktualisfejezetszoveg = aktualisfejezetindex + 1
                var fejezetszamaszoveg = fejezetekszama + 1
                myTts.stop()
                myTts.speak("Closing" + aktualisfejezetszoveg.toString() + "Out of" + fejezetszamaszoveg.toString() + fejezetek[aktualisfejezetindex], TextToSpeech.QUEUE_FLUSH, null)


                bongeszoallapot = true
            }


            // myTts.speak("change mod", TextToSpeech.QUEUE_FLUSH, null)


        }
        kozepsoduplakattelozoido = System.currentTimeMillis()


    }

    fun next() {

        if (bongeszoallapot) {
            if (aktualisfejezetindex < fejezetekszama) {
                aktualisfejezetindex++

                myTts.speak((aktualisfejezetindex + 1).toString() + fejezetek[aktualisfejezetindex], TextToSpeech.QUEUE_FLUSH, null)
            } else {
                var aktualisfejezetszoveg = aktualisfejezetindex + 1
                var fejezetszamaszoveg = fejezetekszama + 1

                myTts.speak("Last Part:" + aktualisfejezetszoveg.toString() + "Out of" + fejezetszamaszoveg.toString() + fejezetek[aktualisfejezetindex], TextToSpeech.QUEUE_FLUSH, null)
            }
        } else {
            if (aktualismondatindex < mondatokszama) {
                aktualismondatindex++

                myTts.speak(mondatok[aktualismondatindex], TextToSpeech.QUEUE_FLUSH, null)
            } else {
                var aktualismondatszoveg = aktualismondatindex + 1
                var mondatokszamaszoveg = mondatokszama + 1

                myTts.speak("Last Sentence:" + aktualismondatszoveg.toString() + "Out of" + mondatokszamaszoveg.toString() + mondatok[aktualismondatindex], TextToSpeech.QUEUE_FLUSH, null)
            }
        }


    }

    fun previous() {
        if (bongeszoallapot) {
            if (aktualisfejezetindex > 0) {
                aktualisfejezetindex--

                myTts.speak((aktualisfejezetindex + 1).toString() + fejezetek[aktualisfejezetindex], TextToSpeech.QUEUE_FLUSH, null)
            } else {
                var aktualisfejezetszoveg = aktualisfejezetindex + 1
                var fejezetszamaszoveg = fejezetekszama + 1

                myTts.speak("First Part:" + aktualisfejezetszoveg + "Out of" + fejezetszamaszoveg + fejezetek[aktualisfejezetindex], TextToSpeech.QUEUE_FLUSH, null)
            }
        } else {
            if (aktualismondatindex > 0) {
                aktualismondatindex--

                myTts.speak(mondatok[aktualismondatindex], TextToSpeech.QUEUE_FLUSH, null)
            } else {
                var aktualismondatszoveg = aktualismondatindex + 1
                var mondatokszamaszoveg = mondatokszama + 1

                myTts.speak("First Sentence:" + aktualismondatszoveg.toString() + "Out of" + mondatokszamaszoveg.toString() + mondatok[aktualismondatindex], TextToSpeech.QUEUE_FLUSH, null)
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
                        KeyEvent.KEYCODE_MEDIA_NEXT -> next()
                        KeyEvent.KEYCODE_MEDIA_PREVIOUS -> previous()
                    }
                    startService(Intent(applicationContext, PlayerService::class.java))
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
    override fun onCreate() {
        super.onCreate()

        //Inflate the chat head layout we created
      var mChatHeadView = LayoutInflater.from(this).inflate(R.layout.layout_chat_head, null);


       //Add the view to the window.
       var params : WindowManager.LayoutParams  =  WindowManager.LayoutParams(
               WindowManager.LayoutParams.WRAP_CONTENT,
               WindowManager.LayoutParams.WRAP_CONTENT,
               WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
               WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
               PixelFormat.TRANSLUCENT);

       //Specify the chat head position
//Initially view will be added to top-left corner
       params.gravity = Gravity.TOP
       params.x = 0;
       params.y = 100;

       //Add the view to the window
        val mWindowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
       mWindowManager.addView(mChatHeadView, params);





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
            }
        })
        myTts.setSpeechRate(1f)

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
        //if (mChatHeadView != null) mWindowManager.removeView(mChatHeadView)
        // mediaSession!!.release()
    }
/*
    companion object {
        val TAG = "MPS"
        internal var mondatadatbazis: MondatDatabase? = null
    }*/
}