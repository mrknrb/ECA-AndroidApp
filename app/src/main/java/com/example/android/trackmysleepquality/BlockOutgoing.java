package com.example.android.trackmysleepquality;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class BlockOutgoing extends BroadcastReceiver
{
    String number;
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Log.d("12280", "asdasNumber is-->> " + number);
        number = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
        setResultData(null);
        Toast.makeText(context, "Outgoing Call Blocked" , Toast.LENGTH_LONG).show();

    }
}
