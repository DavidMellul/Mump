package com.example.root.mump;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.constraint.solver.ArrayLinkedVariables;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;


public class Utils {
    private static long m_ids[];

    public static ArrayList<String> getAllMusicFiles(Context c) {
            final Cursor mCursor = c.getContentResolver().query(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    new String[] { MediaStore.Audio.Media.DISPLAY_NAME, MediaStore.Audio.Media.DATA , MediaStore.Audio.Media._ID}, null, null,
                    "LOWER(" + MediaStore.Audio.Media.TITLE + ") ASC");

            int count = mCursor.getCount();

            String[] songs = new String[count];
            String[] mAudioPath = new String[count];
            long[] ids = new long[count];
            int i = 0;
            if (mCursor.moveToFirst()) {
                do {
                    songs[i] = mCursor.getString(mCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
                    mAudioPath[i] = mCursor.getString(mCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                    ids[i] = mCursor.getLong(mCursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                    i++;
                } while (mCursor.moveToNext());
            }

            mCursor.close();

            ArrayList<String> result = new ArrayList<String>();
            for(int j = 0; j < mAudioPath.length; j++) {
                result.add(mAudioPath[j]);
            }

            m_ids = ids;
        return result;
    }

    public static ArrayList<Song> getAllSongs(Context c) {
        ArrayList<Song> result = new ArrayList<Song>();
        ArrayList<String> sl = getAllMusicFiles(c);
        for (int i = 0; i < sl.size(); i++) {
            String s = sl.get(i);
            result.add(new SongBuilder().buildWithHashmap(Mp3Decoder.getInstance().getInformations(s),m_ids[i]));
        }

        return result;
    }

    public static void requestPermissions(Activity c) {
        int permissionCheck = ContextCompat.checkSelfPermission(c, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (ContextCompat.checkSelfPermission(c,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(c,
                    Manifest.permission.READ_CONTACTS)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(c,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }
}
