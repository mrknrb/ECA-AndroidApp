package com.example.android.trackmysleepquality;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.KeyEvent;
import android.widget.Toast;

public class TTSAccessibilityService extends AccessibilityService  {


    @Override
    public void  onServiceConnected(){
        Toast.makeText(getApplicationContext(), "start", Toast.LENGTH_SHORT).show();
System.out.println("mrkstart");
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Toast.makeText(getApplicationContext(), "start", Toast.LENGTH_SHORT).show();
        System.out.println("mrkstart");
    }
    @Override
    public boolean onKeyEvent(KeyEvent event) {
        Toast.makeText(getApplicationContext(), "valamikey", Toast.LENGTH_SHORT).show();

        int action = event.getAction();
        int keyCode = event.getKeyCode();
        if (action == KeyEvent.ACTION_UP) {
            if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
                Log.d("Hello", "KeyUp");
            } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
                Log.d("Hello", "KeyDown");
            }
            return true;
        } else {
           return true;
            //super.onKeyEvent(event);
        }

    }
    @Override
    public void onInterrupt() {
        Toast.makeText(getApplicationContext(), "start", Toast.LENGTH_SHORT).show();

    }
}