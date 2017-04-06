package com.example.root.mump;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by root on 05/04/17.
 */

public class FragmentViewPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {
    private AudioListFragment al;
    private AudioStreamFragment as ;

    public FragmentViewPagerAdapter(FragmentManager fm, MainActivity a) {
        super(fm);
        al = new AudioListFragment(a);
        as = new AudioStreamFragment(a);
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0)
            return al;
        else
            return as;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
