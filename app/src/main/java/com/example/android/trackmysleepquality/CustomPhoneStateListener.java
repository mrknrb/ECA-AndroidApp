package com.example.android.trackmysleepquality;

import java.lang.reflect.Method;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;


public class CustomPhoneStateListener extends PhoneStateListener {

    //private static final String TAG = "PhoneStateChanged";
    Context context;
    public CustomPhoneStateListener(Context context) {
        super();
        this.context = context;
    }

    @Override
    public void onCallStateChanged(int state, String outGoingNumber) {
        super.onCallStateChanged(state, outGoingNumber);

        switch (state) {
            case TelephonyManager.CALL_STATE_IDLE:
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                endCallIfBlocked(outGoingNumber);
                break;
            case TelephonyManager.CALL_STATE_RINGING:
                break;
            default:
                break;
        }

    }

    private void endCallIfBlocked(String outGoingNumber) {
        try {
            // Java reflection to gain access to TelephonyManager's
            // ITelephony getter
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            Class<?> c = Class.forName(tm.getClass().getName());
            Method m = c.getDeclaredMethod("getITelephony");
            m.setAccessible(true);
            /*
            com.android.internal.telephony.ITelephony telephonyService = (ITelephony) m.invoke(tm);

            if (new BlockNumberHelper(context).isBlocked(outGoingNumber))
            {
                telephonyService = (ITelephony) m.invoke(tm);
                telephonyService.silenceRinger();
                telephonyService.endCall();
            }
*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
