package com.example.android.trackmysleepquality;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.media.session.MediaSession;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import static com.example.android.trackmysleepquality.App.CHANNEL_ID;



public class TTSService extends Service {
    private MediaPlayer mMediaPlayer;

  //  private MediaSession
/*
  BroadcastReceiver mybroadcast = new BroadcastReceiver() {
      //When Event is published, onReceive method is called
      @Override
      public void onReceive(Context context, Intent intent) {
          // TODO Auto-generated method stub
          Log.i("[BroadcastReceiver]", "MyReceiver");

          if (intent.getAction().equals(Intent.ACTION_MEDIA_BUTTON)) {
              Log.i("mrknrb", "Mediamrk");
          }
          else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
              Log.i("mrknrb", "Screen OFF");
          }

      }
  };
*/



    @Override
    public void onCreate() {
        super.onCreate();


/*
        registerReceiver(mybroadcast, new IntentFilter(Intent.ACTION_MEDIA_BUTTON));
        registerReceiver(mybroadcast, new IntentFilter(Intent.ACTION_SCREEN_OFF));
*/

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String input = intent.getStringExtra("EXTRA_MESSAGE");

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
//todo icont változtasd meg
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Text to Speech")
                .setSmallIcon(R.drawable.ic_sleep_5)
                .setContentText(input)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);

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
}