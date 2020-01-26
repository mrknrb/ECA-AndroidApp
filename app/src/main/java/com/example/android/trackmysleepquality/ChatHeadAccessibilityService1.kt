package com.example.android.trackmysleepquality

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.annotation.TargetApi
import android.app.Notification
import android.app.Service
import android.content.*
import android.content.res.Resources
import android.graphics.Path
import android.graphics.PixelFormat
import android.graphics.Point
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.view.*
import android.view.accessibility.AccessibilityEvent
import android.widget.ImageView
import android.widget.Toast

import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView

import com.example.android.trackmysleepquality.App.CHANNEL_ID
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*
import java.util.Timer
import kotlin.concurrent.schedule

class ChatHeadAccessibilityService1 : AccessibilityService(), AccessibilityService.SoftKeyboardController.OnShowModeChangedListener {
    var size = Point()
    private var mWindowManager: WindowManager? = null
    private var mChatHeadView: View? = null
    private var mKeyBoardView: View? = null
    private var mediaSession: MediaSessionCompat? = null
    lateinit var audioManager: AudioManager
    var billentyuzetallapot = false
    var dp = convertDpToPixel(1F).toInt()
    var attachedchatheadview = false
    override fun onShowModeChanged(p0: SoftKeyboardController, p1: Int) {
    }

    lateinit var mainHandler: Handler
    private val updateTextTask = object : Runnable {
        override fun run() {
            // Toast.makeText(this@ChatHeadAccessibilityService1, "Repeat? ", Toast.LENGTH_SHORT).show()
            System.out.println("repeat")
            requestAudioFocus()
            val mMediaPlayer: MediaPlayer
            mMediaPlayer = MediaPlayer.create(applicationContext, R.raw.silent_sound)
            mMediaPlayer.setOnCompletionListener { mMediaPlayer.release() }
            mMediaPlayer.start()
            mainHandler.postDelayed(this, 10000)
        }
    }

    private fun requestAudioFocus(): Boolean {
        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val result = audioManager.requestAudioFocus(AudioManager.OnAudioFocusChangeListener { }, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN)
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            //Focus gained
            return true
        }
        //Could not gain focus
        return false
    }


    fun updatecursor(params: WindowManager.LayoutParams) {

        /**todo bug app:id/chat_head_root} not attached to window manager*/
        if (attachedchatheadview) {
            mWindowManager!!.updateViewLayout(mChatHeadView, params)
        }
    }

    val metrics = Resources.getSystem().getDisplayMetrics()

    fun layouttype(): Int {
        var LAYOUT_FLAG = 0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_PHONE;
        }
        return LAYOUT_FLAG
    }

    //Add the view to the window.
    val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            layouttype(),
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT)
    val paramskeyboard = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            layouttype(),
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT)

    fun convertDpToPixel(dp: Float): Float {
        val metrics = Resources.getSystem().getDisplayMetrics()

        val px = dp * (metrics.densityDpi / 160f)
        return Math.round(px).toFloat()
    }

    override fun onAccessibilityEvent(accessibilityEvent: AccessibilityEvent) {

    }

    override fun onInterrupt() {

    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val mMediaPlayer: MediaPlayer
        mMediaPlayer = MediaPlayer.create(applicationContext, R.raw.silent_sound)
        mMediaPlayer.setOnCompletionListener { mMediaPlayer.release() }
        /*
        Context applicationcontext= getApplicationContext();
        Notification notification = new NotificationCompat.Builder(applicationcontext, CHANNEL_ID)
                .setContentTitle("Text to Speech")
                .setSmallIcon(R.drawable.ic_launcher_sleep_tracker_foreground)
                //.setContentText(input)
                .setSubText("Sub Text")
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();

        startForeground(1, notification);
*/
        System.out.println("repeat")

        mainHandler.post(updateTextTask)

        mMediaPlayer.start()


        return super.onStartCommand(intent, flags, startId)
    }

    var previousduplakattelozoido = 0L
    fun previous() {
        var previousduplakattjelenido = System.currentTimeMillis()
        if ((previousduplakattjelenido - previousduplakattelozoido) > 250) {

            if (billentyuzetallapot) {
                sendMessage("balra")

            } else {
                if (params.y > metrics.heightPixels - 25 * dp) {
                    scrollGesture(-1F, metrics.heightPixels / 3.toFloat())
                } else if (params.y > metrics.heightPixels - 70 * dp) {
                    params.y = metrics.heightPixels - dp * 20
                } else {
                    var initialY = params.y
                    params.y = initialY + dp * 50
                    updatecursor(params)

                }
            }
        } else {
            //Toast.makeText(this@ChatHeadAccessibilityService1, "leaccess", Toast.LENGTH_SHORT).show()

            if (billentyuzetallapot) {

                sendMessage("le")
            } else {
                if (params.y > metrics.heightPixels - 25 * dp) {

                    scrollGesture(-1F, metrics.heightPixels / 2.toFloat())
                    Timer("SettingUp1", false).schedule(150) {
                        scrollGesture(-1F, metrics.heightPixels / 2.toFloat())
                        Timer("SettingUp2", false).schedule(150) {
                            scrollGesture(-1F, metrics.heightPixels / 2.toFloat())
                            Timer("SettingUp3", false).schedule(150) {
                                scrollGesture(-1F, metrics.heightPixels / 2.toFloat())
                                Timer("SettingUp3", false).schedule(150) {
                                    scrollGesture(-1F, metrics.heightPixels / 2.toFloat())
                                    Timer("SettingUp3", false).schedule(150) {
                                        scrollGesture(-1F, metrics.heightPixels / 2.toFloat())

                                    }
                                }
                            }
                        }
                    }
                    //scrollGesture(-1F,300F)
                } else if (params.y > metrics.heightPixels - 70 * dp) {
                    params.y = metrics.heightPixels - dp * 20
                } else if (params.y < metrics.heightPixels / 4) {
                    params.y = metrics.heightPixels / 2
                } else {
                    params.y = metrics.heightPixels - dp * 20
                }
                updatecursor(params)
            }
        }
        previousduplakattelozoido = System.currentTimeMillis()
    }

    var nextduplakattelozoido = 0L
    fun next() {

        var nextduplakattjelenido = System.currentTimeMillis()
        if ((nextduplakattjelenido - nextduplakattelozoido) > 250) {

            if (billentyuzetallapot) {
                sendMessage("jobbra")


            } else {
                if (params.y < 25 * dp) {
                    scrollGesture(+1F, metrics.heightPixels / 3.toFloat())


                } else if (params.y < 70 * dp) {

                    params.y = dp * 20

                } else {
                    var initialY = params.y
                    params.y = initialY - dp * 50
                    updatecursor(params)
                }
            }
        } else {
            if (billentyuzetallapot) {

                sendMessage("fel")
            } else {

                if (params.y < 25 * dp) {
                    scrollGesture(+1F, metrics.heightPixels / 2.toFloat())
                    Timer("SettingUp1", false).schedule(150) {
                        scrollGesture(+1F, metrics.heightPixels / 2.toFloat())
                        Timer("SettingUp2", false).schedule(150) {
                            scrollGesture(+1F, metrics.heightPixels / 2.toFloat())
                            Timer("SettingUp3", false).schedule(150) {
                                scrollGesture(+1F, metrics.heightPixels / 2.toFloat())
                                Timer("SettingUp3", false).schedule(150) {
                                    scrollGesture(+1F, metrics.heightPixels / 2.toFloat())
                                    Timer("SettingUp3", false).schedule(150) {
                                        scrollGesture(+1F, metrics.heightPixels / 2.toFloat())

                                    }
                                }
                            }
                        }
                    }


                } else if (params.y < 70 * dp) {
                    params.y = dp * 20
                } else if (params.y > 3 * metrics.heightPixels / 4) {
                    params.y = metrics.heightPixels / 2
                } else {
                    params.y = dp * 20
                }
                updatecursor(params)
            }
        }
        nextduplakattelozoido = System.currentTimeMillis()
    }
/*
    var kozepsoduplakattelozoido3 = 0L
    var kozepsoduplakattelozoido2 = 0L
    var kozepsoduplakattelozoido1 = 0L
    fun playpause() {
        kozepsoduplakattelozoido3 = kozepsoduplakattelozoido2
        kozepsoduplakattelozoido2 = kozepsoduplakattelozoido1
        kozepsoduplakattelozoido1 = System.currentTimeMillis()
        if (kozepsoduplakattelozoido1 - kozepsoduplakattelozoido2 < 550) {
            performGlobalAction(GLOBAL_ACTION_BACK)
            //duplaklikk
        } else {
            Timer("SettingUp", false).schedule(550) {

                if ((kozepsoduplakattelozoido1 - kozepsoduplakattelozoido2) < 550) {
                    //semmi
                }else{
                    if (billentyuzetallapot) {
                        sendMessage("click")
                    } else {
                        var point = Point(params.x + 10, params.y + 60)
                        pressLocation(point)
                    }
                }



            }
        }
    }
*/
    var kozepsoduplakattelozoido1 = 0L
    fun playpause() {

        if (System.currentTimeMillis() - kozepsoduplakattelozoido1 < 750) {
            performGlobalAction(GLOBAL_ACTION_BACK)
            //duplaklikk
        } else {
            if (billentyuzetallapot) {
                sendMessage("click")
            } else {
                var point = Point(params.x + 10, params.y + 60)
                pressLocation(point)
            }



        }
        kozepsoduplakattelozoido1 = System.currentTimeMillis()
    }
    @TargetApi(24)
    private fun pressLocation(position: Point) {
        val builder = GestureDescription.Builder()
        val p = Path()
        // p.moveTo(600F , 850F )
        // p.lineTo(620F, 850F)

        p.moveTo(position.x.toFloat(), position.y.toFloat())
        p.lineTo((position.x + 5).toFloat(), (position.y + 5).toFloat())
        builder.addStroke(GestureDescription.StrokeDescription(p, 0L, 100L))
        val gesture = builder.build()
        val isDispatched = dispatchGesture(gesture, object : GestureResultCallback() {
            override fun onCompleted(gestureDescription: GestureDescription) {
                super.onCompleted(gestureDescription)
            }

            override fun onCancelled(gestureDescription: GestureDescription) {
                super.onCancelled(gestureDescription)
            }
        }, null)
        // Toast.makeText(this@ChatHeadAccessibilityService1, "Was it dispatched? " + isDispatched, Toast.LENGTH_SHORT).show()
    }

    @TargetApi(24)
    private fun scrollGesture(minusplus: Float, nagysag: Float) {
        val builder = GestureDescription.Builder()
        val p = Path()
        // p.moveTo(600F , 850F )
        // p.lineTo(620F, 850F)

        p.moveTo(metrics.widthPixels.toFloat() / 2, (metrics.heightPixels.toFloat() / 2)-minusplus*metrics.heightPixels.toFloat()/6)
        p.lineTo(metrics.widthPixels.toFloat() / 2, minusplus * nagysag + metrics.heightPixels.toFloat() / 2)
        builder.addStroke(GestureDescription.StrokeDescription(p, 0L, 150L))
        val gesture = builder.build()
        val isDispatched = dispatchGesture(gesture, object : GestureResultCallback() {
            override fun onCompleted(gestureDescription: GestureDescription) {
                super.onCompleted(gestureDescription)
            }

            override fun onCancelled(gestureDescription: GestureDescription) {
                super.onCancelled(gestureDescription)
            }
        }, null)
        // Toast.makeText(this@ChatHeadAccessibilityService1, "Was it dispatched? " + isDispatched, Toast.LENGTH_SHORT).show()
    }

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
                            // Toast.makeText(applicationContext, "play", Toast.LENGTH_SHORT).show()

                            playpause()
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
                        }
                        KeyEvent.KEYCODE_MEDIA_PAUSE -> {
                            // Toast.makeText(applicationContext, "pause", Toast.LENGTH_SHORT).show()
                            playpause()
                        }
                        KeyEvent.KEYCODE_MEDIA_NEXT -> {
                            // Toast.makeText(applicationContext, "next", Toast.LENGTH_SHORT).show()
                            next()
                        }

                        KeyEvent.KEYCODE_MEDIA_PREVIOUS -> {
                            // Toast.makeText(applicationContext, "prev", Toast.LENGTH_SHORT).show()
                            previous()
                        }


                    }

                    return true
                }
            }
            return false
        }

    }

    private fun sendMessage(utasitas: String) {
        val intent = Intent("accessibilitytol")
        // You can also include some extra data.
        intent.putExtra("utasitas", utasitas)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    private val mMessageReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            // Get extra data included in the Intent

            billentyuzetallapot = intent.getBooleanExtra("allapot", false)
        }
    }

    override fun onCreate() {
        super.onCreate()

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                IntentFilter("billentyuzettol"))


        val receiver = ComponentName(packageName, RemoteReceiver::class.java.name)
        mediaSession = MediaSessionCompat(this, "ChatHeadAccessibilityService1", receiver, null)
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
        //Inflate the chat head layout we created

        mChatHeadView = LayoutInflater.from(this).inflate(R.layout.layout_chat_head, null)
        //Specify the chat head position
        params.gravity = Gravity.TOP or Gravity.LEFT       //Initially view will be added to top-left corner
        params.x = 600
        params.y = 850

        //Add the view to the window

        // mKeyBoardView= LayoutInflater.from(this).inflate(R.layout.layout_keyboard, null)
        // Toast.makeText(this, "Permission granted: \$PERMISSION_REQUEST_READ_PHONE_STATE", Toast.LENGTH_SHORT).show()
        //paramskeyboard.gravity= Gravity.CENTER
        // paramskeyboard.horizontalWeight=1F
        // paramskeyboard.horizontalMargin=0F

        // paramskeyboard.x = 50
        //  paramskeyboard.y = 10
        mWindowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        mWindowManager!!.addView(mChatHeadView, params)
        //mWindowManager!!.addView(mKeyBoardView, paramskeyboard)
        attachedchatheadview = true
        var defaultdisplay = mWindowManager!!.defaultDisplay.getSize(size)

//defaultdisplay.getSize(size)
        /*
        //Set the close button.
        ImageView closeButton = (ImageView) mChatHeadView.findViewById(R.id.kurzor);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //close the service and remove the chat head from the window
                stopSelf();
            }
        });
*/
        //Drag and move chat head using user's touch action.


        val mMediaPlayer: MediaPlayer
        mMediaPlayer = MediaPlayer.create(applicationContext, R.raw.silent_sound)
        mMediaPlayer.setOnCompletionListener { mMediaPlayer.release() }
        mMediaPlayer.start()
        val chatHeadImage = mChatHeadView!!.findViewById<View>(R.id.chat_head_profile_iv) as ImageView



        chatHeadImage.setOnTouchListener(object : View.OnTouchListener {
            private var lastAction: Int = 0
            private var initialX: Int = 0
            private var initialY: Int = 0
            private var initialTouchX: Float = 0.toFloat()
            private var initialTouchY: Float = 0.toFloat()

            override fun onTouch(v: View, event: MotionEvent): Boolean {
                billentyuzetallapot = false
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {

                        //remember the initial position.
                        initialX = params.x
                        initialY = params.y

                        //get the touch location
                        initialTouchX = event.rawX
                        initialTouchY = event.rawY

                        lastAction = event.action
                        return true
                    }
                    MotionEvent.ACTION_UP -> {
                        //As we implemented on touch listener with ACTION_MOVE,
                        //we have to check if the previous action was ACTION_DOWN
                        //to identify if the user clicked the view or not.
                        if (lastAction == MotionEvent.ACTION_DOWN) {
                            //Open the chat conversation click.
                            val intent = Intent(this@ChatHeadAccessibilityService1, MainActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)

                            //close the service and remove the chat heads
                            stopSelf()
                        }
                        lastAction = event.action
                        return true
                    }
                    MotionEvent.ACTION_MOVE -> {
                        //Calculate the X and Y coordinates of the view.
                        params.x = initialX + (event.rawX - initialTouchX).toInt()
                        params.y = initialY + (event.rawY - initialTouchY).toInt()

                        //Update the layout with new X & Y coordinate
                        mWindowManager!!.updateViewLayout(mChatHeadView, params)
                        lastAction = event.action
                        return true
                    }
                }
                return false
            }
        })
        mainHandler = Handler(Looper.getMainLooper())
        mainHandler.post(updateTextTask)
    }

    override fun onDestroy() {
        super.onDestroy()

        mainHandler.removeCallbacks(updateTextTask)
        if (mChatHeadView != null) mWindowManager!!.removeView(mChatHeadView)
    }
}
