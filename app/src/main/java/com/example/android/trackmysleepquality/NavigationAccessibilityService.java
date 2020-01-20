package com.example.android.trackmysleepquality;

import android.accessibilityservice.AccessibilityService;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

public class NavigationAccessibilityService extends AccessibilityService  {
    private WindowManager.LayoutParams mWindowsParams;
    private View mView;
    private boolean wasInFocus = true;

    private int height;

    @Override
    public void  onServiceConnected(){
        Toast.makeText(getApplicationContext(), "start", Toast.LENGTH_SHORT).show();
System.out.println("mrkstart");
    }


    private WindowManager mWindowManager;
    private View mChatHeadView;
    @Override
    public void onCreate() {
        super.onCreate();



        mChatHeadView = LayoutInflater.from(this).inflate(R.layout.layout_chat_head, null);


        //Add the view to the window.
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        //Specify the chat head position
//Initially view will be added to top-left corner
        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 0;
        params.y = 100;

        //Add the view to the window
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mWindowManager.addView(mChatHeadView, params);


//….

        /**hgjghj*//*
        Toast.makeText(getBaseContext(),"onCreate", Toast.LENGTH_LONG).show();
        mView = new HUDView(this);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                0,
//              WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//                      | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.RIGHT | Gravity.TOP;
        params.setTitle("Load Average");
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        wm.addView(mView, params);
*/
/*
        mView = new HUDView(this);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.RIGHT | Gravity.TOP;
        params.setTitle("Load Average");
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        wm.addView(mView, params);
*/

/*
        androidHead = new ImageView(this);
        androidHead.setImageResource(R.drawable.button);

        wm = (WindowManager) getSystemService(WINDOW_SERVICE);

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams();

        //params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        //params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.type = WindowManager.LayoutParams.TYPE_PRIORITY_PHONE;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS |
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        params.format = PixelFormat.TRANSLUCENT;
        params.gravity = Gravity.TOP | Gravity.LEFT;

        wm.addView(androidHead, params);
*/
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
    @Override
    public void onDestroy() {

        super.onDestroy();
       //removeView();
    }

    private void removeView() {
        /*
        if (androidHead != null) {
            wm.removeView(androidHead);
        }  */
    }

}