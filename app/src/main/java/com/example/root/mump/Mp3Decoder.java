package com.example.root.mump;

import android.media.MediaMetadataRetriever;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by root on 05/04/17.
 */

public class Mp3Decoder {
    private MediaMetadataRetriever m;

    private static Mp3Decoder instance = new Mp3Decoder();
    private Mp3Decoder() { m = new MediaMetadataRetriever(); }
    public static Mp3Decoder getInstance() { return instance; }

    public HashMap<String,String> getInformations(String f) {
        m.setDataSource(f);

        HashMap<String, String> datas = new HashMap<String, String>();

        datas.put("title",m.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE));
        datas.put("author",m.extractMetadata(MediaMetadataRetriever.METADATA_KEY_AUTHOR));
        datas.put("duration",m.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));

        long millis = Integer.parseInt(datas.get("duration"));
        datas.put("duration", (new SimpleDateFormat("mm:ss")).format(new Date(millis)));


        if(datas.get("title") == null) {
            String t = f.substring(f.lastIndexOf("/")+1);
            t = t.substring(0,t.indexOf("."));
            datas.put("title", t);
        }
        if(datas.get("author") == null) datas.put("author", "Auteur inconnu");

        return datas;
    }
}
