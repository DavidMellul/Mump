package com.example.root.mump;

import android.content.Context;
import android.media.MediaPlayer;

import java.util.ArrayList;

/**
 * Created by root on 05/04/17.
 */

public class MediaController
{
    public ArrayList<Song> songs;
    public Context c;
    public ArrayList<Song> streams;

    private static MediaController instance = new MediaController();
    private MediaController() {
        songs = new ArrayList<>();

        streams = new ArrayList<>();
        streams.add(new SongBuilder().title("In Da Club").author("Fifty Cent").build());
        streams.add(new SongBuilder().title("Smack That").author("Akon & Eminem").build());
        streams.add(new SongBuilder().title("Lonely").author("Akon").build());
        streams.add(new SongBuilder().title("Miss independant").author("Ne-Yo").build());
        streams.add(new SongBuilder().title("Beautiful Girls").author("Sean Kingston").build());
        streams.add(new SongBuilder().title("In Da Club").author("Fifty Cent").build());
        streams.add(new SongBuilder().title("Smack That").author("Akon & Eminem").build());
        streams.add(new SongBuilder().title("Lonely").author("Akon").build());
        streams.add(new SongBuilder().title("Miss independant").author("Ne-Yo").build());
        streams.add(new SongBuilder().title("Beautiful Girls").author("Sean Kingston").build());
        streams.add(new SongBuilder().title("In Da Club").author("Fifty Cent").build());
        streams.add(new SongBuilder().title("Smack That").author("Akon & Eminem").build());
        streams.add(new SongBuilder().title("Lonely").author("Akon").build());
        streams.add(new SongBuilder().title("Miss independant").author("Ne-Yo").build());
        streams.add(new SongBuilder().title("Beautiful Girls").author("Sean Kingston").build());

    }
    public static MediaController getInstance() { return instance; }
    public void loadSongs() { songs = Utils.getAllSongs(c); }

}
