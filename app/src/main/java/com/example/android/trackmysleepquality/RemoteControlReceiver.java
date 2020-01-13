package com.example.android.trackmysleepquality;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class RemoteControlReceiver extends BroadcastReceiver {
    private static final String TAG = "MyBroadcastReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {


        if(Intent.ACTION_SCREEN_ON.equals(intent.getAction())){
Toast.makeText(context,"Screen on",Toast.LENGTH_LONG).show();



        }


        /*
        StringBuilder sb = new StringBuilder();
        sb.append("Action: " + intent.getAction() + "\n");
        sb.append("URI: " + intent.toUri(Intent.URI_INTENT_SCHEME).toString() + "\n");
        String log = sb.toString();
        Log.d(TAG, log);

        Toast.makeText(context, "gdrdgrg", Toast.LENGTH_LONG).show();
        Toast.makeText(context, log, Toast.LENGTH_LONG).show();

        */
    }
}