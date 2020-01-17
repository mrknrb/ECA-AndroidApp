package com.example.android.trackmysleepquality;

import android.media.AudioManager;
import android.util.Log;

import androidx.fragment.app.Fragment;

public class AudioFragment extends Fragment implements OnAudioVolumeChangedListener {

    private AudioVolumeObserver mAudioVolumeObserver;

    @Override
    public void onResume() {
        super.onResume();
        // initialize audio observer
        if (mAudioVolumeObserver == null) {
            mAudioVolumeObserver = new AudioVolumeObserver(getActivity());
        }
        /*
         * register audio observer to identify the volume changes
         * of audio streams for music playback.
         *
         * It is also possible to listen for changes in other audio stream types:
         * STREAM_RING: phone ring, STREAM_ALARM: alarms, STREAM_SYSTEM: system sounds, etc.
         */
        mAudioVolumeObserver.register(AudioManager.STREAM_MUSIC, this);
    }

    @Override
    public void onPause() {
        super.onPause();
        // release audio observer
        if (mAudioVolumeObserver != null) {
            mAudioVolumeObserver.unregister();
        }
    }

    @Override
    public void onAudioVolumeChanged(int currentVolume, int maxVolume) {
        Log.d("Audio", "Volume: " + currentVolume + "/" + maxVolume);
        Log.d("Audio", "Volume: " + (int) ((float) currentVolume / maxVolume) * 100 + "%");
    }
}
