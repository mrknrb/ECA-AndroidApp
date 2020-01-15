package com.example.android.trackmysleepquality;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.KeyEvent;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import static com.example.android.trackmysleepquality.App.CHANNEL_ID;


public class TTSService extends Service {
    public static final String TAG = "MPS";
    private MediaSessionCompat mediaSession;
    private final MediaSessionCompat.Callback mMediaSessionCallback
            = new MediaSessionCompat.Callback() {

        @Override
        public boolean onMediaButtonEvent(Intent mediaButtonEvent) {
            final String intentAction = mediaButtonEvent.getAction();
            // Toast.makeText(getApplicationContext(), mediaButtonEvent.getAction()., Toast.LENGTH_SHORT).show();

            if (Intent.ACTION_MEDIA_BUTTON.equals(intentAction)) {
                final KeyEvent event = mediaButtonEvent.getParcelableExtra(
                        Intent.EXTRA_KEY_EVENT);
                if (event == null) {
                    return super.onMediaButtonEvent(mediaButtonEvent);
                }
                final int keycode = event.getKeyCode();
                final int action = event.getAction();
                if (event.getRepeatCount() == 0 && action == KeyEvent.ACTION_DOWN) {

                    switch (keycode) {
                        // Do what you want in here
                        case KeyEvent.KEYCODE_MEDIA_PLAY:
                            Toast.makeText(getApplicationContext(), "play", Toast.LENGTH_SHORT).show();
                            break;
                        case KeyEvent.KEYCODE_MEDIA_PAUSE:
                            Toast.makeText(getApplicationContext(), "pause", Toast.LENGTH_SHORT).show();
                            break;
                        case KeyEvent.KEYCODE_MEDIA_NEXT:
                            Toast.makeText(getApplicationContext(), "next", Toast.LENGTH_SHORT).show();
                            break;
                        case KeyEvent.KEYCODE_MEDIA_PREVIOUS:
                            Toast.makeText(getApplicationContext(), "previous", Toast.LENGTH_SHORT).show();
                            break;
                    }
                    startService(new Intent(getApplicationContext(), TTSService.class));
                    return true;
                }
            }
            return false;
        }
    };

    @Override
    public void onCreate() {

        super.onCreate();
        final MediaPlayer mMediaPlayer;
        ComponentName receiver = new ComponentName(getPackageName(), RemoteReceiver.class.getName());
        mediaSession = new MediaSessionCompat(this, "PlayerService", receiver, null);
        mediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mMediaPlayer = MediaPlayer.create(this, R.raw.silent_sound);
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mMediaPlayer.release();
            }
        });
        mMediaPlayer.start();
        mediaSession.setPlaybackState(new PlaybackStateCompat.Builder()
                .setState(PlaybackStateCompat.STATE_PAUSED, 0, 0)
                .setActions(PlaybackStateCompat.ACTION_PLAY_PAUSE)
                .build());
        mediaSession.setCallback(mMediaSessionCallback);
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.requestAudioFocus(new AudioManager.OnAudioFocusChangeListener() {
            @Override
            public void onAudioFocusChange(int focusChange) {
            }
        }, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        mediaSession.setActive(true);



    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        String input = intent.getStringExtra("inputExtra");

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Example Service")
                .setContentText(input)
                //.setSmallIcon(R.drawable.ic_android)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);

        //do heavy work on a background thread
        //stopSelf();

        return START_NOT_STICKY;





        /*
        if (mediaSession.getController().getPlaybackState().getState() == PlaybackStateCompat.STATE_PLAYING) {
            mediaSession.setPlaybackState(new PlaybackStateCompat.Builder()
                    .setState(PlaybackStateCompat.STATE_PAUSED, 0, 0.0f)
                    .setActions(PlaybackStateCompat.ACTION_PLAY_PAUSE).build());
        } else {
            mediaSession.setPlaybackState(new PlaybackStateCompat.Builder()
                    .setState(PlaybackStateCompat.STATE_PLAYING, 0, 1.0f)
                    .setActions(PlaybackStateCompat.ACTION_PLAY_PAUSE).build());
        }


        String input = intent.getStringExtra("EXTRA_MESSAGE");
        Intent notificationIntent = new Intent(this, TTSService.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        mediaSession.setActive(true);
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ecalogo);
        Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setContentTitle("Text to Speech")
                .setSmallIcon(R.drawable.ic_launcher_sleep_tracker_foreground)
                .setContentText(input)
                .setColor(Color.RED)
                .setLargeIcon(largeIcon)
                .addAction(R.drawable.ic_sleep_0, "Dislike", null)
                .addAction(R.drawable.ic_sleep_0, "Previous", null)
                .addAction(R.drawable.ic_sleep_0, "Pause", null)
                .addAction(R.drawable.ic_sleep_0, "Next", null)
                .addAction(R.drawable.ic_sleep_1, "Like", null)
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(1, 2, 3)
                )
                .setSubText("Sub Text")
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);

        //do heavy work on a background thread
        //stopSelf();
        return START_NOT_STICKY;
*/

    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaSession.release();
    }

}