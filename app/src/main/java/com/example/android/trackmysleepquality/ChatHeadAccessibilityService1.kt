package com.example.android.trackmysleepquality

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.annotation.TargetApi
import android.app.Notification
import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Path
import android.graphics.PixelFormat
import android.graphics.Point
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.IBinder
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.view.*
import android.view.accessibility.AccessibilityEvent
import android.widget.ImageView
import android.widget.Toast

import androidx.core.app.NotificationCompat

import com.example.android.trackmysleepquality.App.CHANNEL_ID

class ChatHeadAccessibilityService1 : AccessibilityService() {
    var size = Point()
    private var mWindowManager: WindowManager? = null
    private var mChatHeadView: View? = null
    private var mediaSession: MediaSessionCompat? = null
    lateinit var audioManager: AudioManager
    //Add the view to the window.
    val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT)


    override fun onAccessibilityEvent(accessibilityEvent: AccessibilityEvent) {

    }

    override fun onInterrupt() {

    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
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

        val mMediaPlayer: MediaPlayer
        mMediaPlayer = MediaPlayer.create(applicationContext, R.raw.silent_sound)
        mMediaPlayer.setOnCompletionListener { mMediaPlayer.release() }
        mMediaPlayer.start()



        return super.onStartCommand(intent, flags, startId)
    }

    fun previous(){
       var initialX = params.x
       var initialY = params.y
        params.y=initialY+100

        //Update the layout with new X & Y coordinate
        mWindowManager!!.updateViewLayout(mChatHeadView, params)

    }

    fun next(){
        var initialX = params.x
        var initialY = params.y
        params.y=initialY-100

 Toast.makeText(applicationContext,  size.x.toString()+  size.y.toString(), Toast.LENGTH_SHORT).show()




        //Update the layout with new X & Y coordinate
        mWindowManager!!.updateViewLayout(mChatHeadView, params)
    }
    fun playpause(){
        var point=Point(params.x+10,params.y+60)
        pressLocation(point)
    }
    @TargetApi(24)
    private fun pressLocation(position: Point) {
        val builder = GestureDescription.Builder()
        val p = Path()
    // p.moveTo(600F , 850F )
    // p.lineTo(620F, 850F)

      p.moveTo(position.x.toFloat() , position.y.toFloat() )
      p.lineTo((position.x + 5).toFloat(), (position.y + 5).toFloat())
        builder.addStroke(GestureDescription.StrokeDescription(p, 0L, 50L))
        val gesture = builder.build()
        val isDispatched = dispatchGesture(gesture, object:GestureResultCallback() {
           override fun onCompleted(gestureDescription:GestureDescription) {
                super.onCompleted(gestureDescription)
            }
           override fun onCancelled(gestureDescription:GestureDescription) {
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



    override fun onCreate() {
        super.onCreate()


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
        Toast.makeText(this, "Permission granted: \$PERMISSION_REQUEST_READ_PHONE_STATE", Toast.LENGTH_SHORT).show()

        //Specify the chat head position
        params.gravity = Gravity.TOP or Gravity.LEFT       //Initially view will be added to top-left corner
        params.x = 600
        params.y = 850

        //Add the view to the window
        mWindowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        mWindowManager!!.addView(mChatHeadView, params)

      var defaultdisplay= mWindowManager!!.defaultDisplay.getSize(size)

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
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mChatHeadView != null) mWindowManager!!.removeView(mChatHeadView)
    }
}
