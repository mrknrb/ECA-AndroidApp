package com.example.android.trackmysleepquality;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.session.MediaSession;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.media.MediaBrowserServiceCompat;
import androidx.media.session.MediaButtonReceiver;

import java.io.IOException;
import java.util.List;

import static com.example.android.trackmysleepquality.App.CHANNEL_ID;


public class TTSService extends MediaBrowserServiceCompat implements MediaPlayer.OnCompletionListener, AudioManager.OnAudioFocusChangeListener {


    private MediaPlayer mMediaPlayer;
    private MediaSessionCompat mMediaSessionCompat;

    private BroadcastReceiver mNoisyReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
                mMediaPlayer.pause();
            }
        }
    };


    private void initMediaSession() {
        ComponentName mediaButtonReceiver = new ComponentName(getApplicationContext(), MediaButtonReceiver.class);
        mMediaSessionCompat = new MediaSessionCompat(getApplicationContext(), "Tag", mediaButtonReceiver, null);

        mMediaSessionCompat.setCallback(mMediaSessionCallback);
        mMediaSessionCompat.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS | MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        Intent mediaButtonIntent = new Intent(Intent.ACTION_MEDIA_BUTTON);
        mediaButtonIntent.setClass(this, MediaButtonReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, mediaButtonIntent, 0);
        mMediaSessionCompat.setMediaButtonReceiver(pendingIntent);
        setSessionToken(mMediaSessionCompat.getSessionToken());

    }

    private void setMediaPlaybackState(int state) {
        PlaybackStateCompat.Builder playbackstateBuilder = new PlaybackStateCompat.Builder();
        playbackstateBuilder
                .setActions(PlaybackStateCompat.ACTION_PLAY_PAUSE |
                        PlaybackStateCompat.ACTION_PLAY |
                        PlaybackStateCompat.ACTION_PAUSE |
                        PlaybackStateCompat.ACTION_STOP |
                        PlaybackStateCompat.ACTION_SEEK_TO |
                        PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                        PlaybackStateCompat.ACTION_SKIP_TO_NEXT |
                        PlaybackStateCompat.ACTION_SKIP_TO_NEXT
                )
                .setState(
                        PlaybackStateCompat.STATE_PLAYING, 0, 1.0f

                );

        /* ez volt az eredeti, ami előtte van, az módosítás
        if( state == PlaybackStateCompat.STATE_PLAYING ) {
            playbackstateBuilder.setActions(PlaybackStateCompat.ACTION_PLAY_PAUSE | PlaybackStateCompat.ACTION_PAUSE);
        } else {
            playbackstateBuilder.setActions(PlaybackStateCompat.ACTION_PLAY_PAUSE | PlaybackStateCompat.ACTION_PLAY);
        }
        playbackstateBuilder.setState(state, PlaybackStateCompat.PLAYBACK_POSITION_UNKNOWN, 0);*/
        mMediaSessionCompat.setPlaybackState(playbackstateBuilder.build());

        Toast.makeText(getApplicationContext(), "state playing", Toast.LENGTH_SHORT).show();
        System.out.println("mrknrb" + "state playing");

    }

    private boolean successfullyRetrievedAudioFocus() {
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        int result = audioManager.requestAudioFocus(this,
                AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

        return result == AudioManager.AUDIOFOCUS_GAIN;
    }

    private MediaSessionCompat.Callback mMediaSessionCallback = new MediaSessionCompat.Callback() {

        @Override
        public void onPlay() {
            super.onPlay();
            Toast.makeText(getApplicationContext(), "stop tracking", Toast.LENGTH_SHORT).show();
            System.out.println("mrknrb" + "mukodik a gomb");
            if (!successfullyRetrievedAudioFocus()) {
                return;
            }

            if (!successfullyRetrievedAudioFocus()) {
                return;
            }

            mMediaSessionCompat.setActive(true);
            setMediaPlaybackState(PlaybackStateCompat.STATE_PLAYING);

            //showPlayingNotification();
            mMediaPlayer.start();

        }

        @Override
        public void onPause() {
            super.onPause();
            Toast.makeText(getApplicationContext(), "stop tracking", Toast.LENGTH_SHORT).show();
            System.out.println("mrknrb" + "mukodik a gomb");
                    /*
            if( mMediaPlayer.isPlaying() ) {
                mMediaPlayer.pause();
                setMediaPlaybackState(PlaybackStateCompat.STATE_PAUSED);
               // showPausedNotification();
            }

                     */
        }

        @Override
        public void onPlayFromMediaId(String mediaId, Bundle extras) {
            super.onPlayFromMediaId(mediaId, extras);
            System.out.println("mrknrb" + "mukodik a gomb" + mediaId);
/*
            try {
                AssetFileDescriptor afd = getResources().openRawResourceFd(Integer.valueOf(mediaId));
                if( afd == null ) {
                    return;
                }

                try {
                    mMediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());

                } catch( IllegalStateException e ) {
                    mMediaPlayer.release();
                    initMediaPlayer();
                    mMediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                }

                afd.close();
                initMediaSessionMetadata();

            } catch (IOException e) {
                return;
            }

            try {
                mMediaPlayer.prepare();
            } catch (IOException e) {}
*/
            //Work with extras here if you want
        }

        @Override
        public void onCommand(String command, Bundle extras, ResultReceiver cb) {
            super.onCommand(command, extras, cb);
            Toast.makeText(getApplicationContext(), "stop tracking", Toast.LENGTH_SHORT).show();
            System.out.println("mrknrb" + "mukodik a gomb");
            /*
            if( COMMAND_EXAMPLE.equalsIgnoreCase(command) ) {
                //Custom command here
            }
            */
        }


        @Override
        public void onSeekTo(long pos) {
            super.onSeekTo(pos);
        }

    };

    @Override
    public void onCreate() {
        super.onCreate();
        initMediaSession();
        mMediaSessionCompat.setActive(true);
        // Toast.makeText(getApplicationContext(),"stop tracking", Toast.LENGTH_SHORT).show();
        System.out.println("mrknrb" + mMediaSessionCompat.isActive());

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        mMediaSessionCompat.setActive(true);
        String input = intent.getStringExtra("EXTRA_MESSAGE");
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ecalogo);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
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
                        //.setMediaSession(mMediaSessionCompat.getSessionToken())   ha pirosra akarod alakítani
                )
                .setSubText("Sub Text")
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);

        mMediaSessionCompat.setActive(true);
        //do heavy work on a background thread
        //stopSelf();
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Nullable
    @Override
    public BrowserRoot onGetRoot(@NonNull String clientPackageName, int clientUid, @Nullable Bundle rootHints) {
        return null;
    }

    @Override
    public void onLoadChildren(@NonNull String parentId, @NonNull Result<List<MediaBrowserCompat.MediaItem>> result) {

    }

    @Override
    public void onAudioFocusChange(int i) {

    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {

    }

}