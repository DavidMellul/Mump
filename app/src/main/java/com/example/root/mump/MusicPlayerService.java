package com.example.root.mump;

import android.app.Service;
import android.content.ContentUris;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;


import java.io.IOException;


public class MusicPlayerService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {
    //media player
    private MediaPlayer player;
    public static int currentIndex;
    public static int currentStreamIndex;
    public static OnMusicStateChangedListener osl;

    static final String ACTION_PLAY = "com.example.action.PLAY";
    static final String ACTION_NEXT = "com.example.action.NEXT";
    static final String ACTION_PREVIOUS = "com.example.action.PREVIOUS";
    static final String ACTION_STREAM = "com.example.action.STREAM";
    static final String ACTION_NEWPLAY = "com.example.action.ACTION_NEWPLAY";
    static final String ACTION_UNSTREAM = "com.example.action.ACTION_UNSTREAM";

    boolean isStreaming = false;


    public void onCreate() {
        super.onCreate();
        player = new MediaPlayer();
        player.setOnPreparedListener(this);
        player.setOnCompletionListener(this);
        playSong();
        player.pause();
        NotificationHandler.getInstance().setContext(this);
        NotificationHandler.getInstance().sendNotification(MediaController.getInstance().songs.get(currentIndex));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        switch (intent.getAction()) {
            case ACTION_PLAY:
                if (player.isPlaying()) {
                    player.pause();

                    if (!isStreaming)
                        osl.OnMusicStateChanged(MediaController.getInstance().songs.get(currentIndex), 0);
                    else
                        osl.OnMusicStateChanged(MediaController.getInstance().streams.get(currentIndex), 0);

                } else {
                    player.start();

                    if (!isStreaming)
                        osl.OnMusicStateChanged(MediaController.getInstance().songs.get(currentIndex), 1);
                    else
                        osl.OnMusicStateChanged(MediaController.getInstance().streams.get(currentIndex), 1);
                }
                break;
            case ACTION_PREVIOUS:
                if (isStreaming) {
                    if (currentStreamIndex > 0) currentStreamIndex--;
                }
                MediaController.getInstance().songs.get(currentIndex).selected = false;
                if (currentIndex > 0) {
                    currentIndex--;
                } else {
                    currentIndex = MediaController.getInstance().songs.size() - 1;
                }
                playSong();
                break;

            case ACTION_NEXT:
                if (isStreaming) {
                    if (currentStreamIndex < MediaController.getInstance().streams.size() - 1)
                        currentStreamIndex++;
                }
                MediaController.getInstance().songs.get(currentIndex).selected = false;
                if (currentIndex < MediaController.getInstance().songs.size() - 1) {
                    currentIndex++;
                } else {
                    currentIndex = 0;
                }
                playSong();
                break;
            case ACTION_NEWPLAY:
                playSong();
                break;
            case ACTION_STREAM:
                isStreaming = true;
                break;
            case ACTION_UNSTREAM:
                isStreaming = false;
                break;
            default:
                break;
        }

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public void playSong() {
        Uri trackUri;
        if (!isStreaming)
            trackUri = ContentUris.withAppendedId(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, MediaController.getInstance().songs.get(currentIndex).ID);
        else {
            if ((currentStreamIndex + 1) % 6 == 0)
                trackUri = Uri.parse("http://dmware.fr/mump/1.mp3");
            else
                trackUri = Uri.parse("http://dmware.fr/mump/" + ((currentStreamIndex + 1) % 6) + ".mp3");
        }

        MediaController.getInstance().songs.get(currentIndex).selected = true;

        try {
            player.stop();
            player.reset();
            player.setDataSource(getApplicationContext(), trackUri);
            player.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        player.start();
        Song s;
        if (!isStreaming) s = MediaController.getInstance().songs.get(currentIndex);
        else s = MediaController.getInstance().streams.get(currentStreamIndex);

        osl.OnMusicStateChanged(s, 1);
        NotificationHandler.getInstance().sendNotification(s);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (!isStreaming)
            if (currentIndex < MediaController.getInstance().songs.size() - 1) {
                currentIndex++;
                playSong();
            }
        else
            if(currentStreamIndex < MediaController.getInstance().streams.size() -1) {
                currentStreamIndex++;
                playSong();
            }
    }
}