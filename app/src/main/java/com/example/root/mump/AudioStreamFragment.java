package com.example.root.mump;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gigamole.library.PulseView;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;


/**
 * Created by root on 05/04/17.
 */

public class AudioStreamFragment extends Fragment {
    Activity m_activity;

    public AudioStreamFragment(Activity a) {
        // Required empty public constructor
        m_activity = a;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_audio_stream,container,false);

        PulseView p = (PulseView)v.findViewById(R.id.pv);
        p.startPulse();

        GPSTracker gps = new GPSTracker(m_activity);

        // check if GPS enabled
        if (gps.canGetLocation()) {
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            String address = BetterGeocoder.getNameFromCoordinates(m_activity, latitude, longitude);
            ((TextView)v.findViewById(R.id.audioStreamAddress)).setText(address);
        }

        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
        PulseView p = (PulseView)getView().findViewById(R.id.pv);
        p.finishPulse();
    }

    @Override
    public void onResume() {
        super.onResume();
        PulseView p = (PulseView)getView().findViewById(R.id.pv);
        p.startPulse();
    }

    @Override
    public void onStop() {
        super.onStop();
        PulseView p = (PulseView)getView().findViewById(R.id.pv);
        p.finishPulse();
    }
}
