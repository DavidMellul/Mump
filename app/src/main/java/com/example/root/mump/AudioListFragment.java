package com.example.root.mump;

import android.app.Activity;
import android.app.Fragment;
import android.app.LauncherActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * Created by root on 05/04/17.
 */

public class AudioListFragment extends android.support.v4.app.Fragment {
    private ListView audioList;
    private SongListAdapter audioListAdapter;
    private MainActivity m_activity;

    public AudioListFragment(MainActivity a) {
        // Required empty public constructor
        m_activity = a;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_audio_list,container,false);

        audioList = (ListView)v.findViewById(R.id.audioListView);
        audioListAdapter = new SongListAdapter(m_activity);
        audioList.setAdapter(audioListAdapter);
        m_activity.omsl = audioListAdapter;

        // Inflate the layout for this fragment
        return v;
    }
}
