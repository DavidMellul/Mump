package com.example.root.mump;

import java.util.HashMap;

/**
 * Created by root on 05/04/17.
 */

public class SongBuilder {
    private Song s;
    public SongBuilder() {
        s = new Song();
    }

    public SongBuilder title(String t) { s.title = t; return this; }
    public SongBuilder author(String a) { s.author = a; return this; }
    public SongBuilder duration(String d) { s.duration = d; return this; }
    public SongBuilder ID(long i) { s.ID = i; return this; }

    public Song build() { return  s; }
    public Song buildWithHashmap(HashMap<String, String> d, long i) {
        s = new SongBuilder()
                .title(d.get("title"))
                .author(d.get("author"))
                .duration(d.get("duration"))
                .ID(i)
                .build();
        return s;
    }
}
