package com.example.android.trackmysleepquality

import android.telecom.Call
import android.telecom.InCallService
import android.widget.Toast

class CallService : InCallService() {

    override fun onCallAdded(call: Call) {

        call!!.disconnect()
       //OngoingCall.call = call
        //CallActivity.start(this, call)

        Toast.makeText(this, "kurva jóóóóó!!!!!!!", Toast.LENGTH_SHORT).show()
    }

    override fun onCallRemoved(call: Call) {
      //  OngoingCall.call = null
    }
}