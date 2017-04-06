package com.example.root.mump;

import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by root on 05/04/17.
 */

public class SongListAdapter extends ArrayAdapter<Song> implements OnMusicSelectedListener {
    private Context c;
    private View selected;
    public int positionSelected;
    public int previousPostion;

    public SongListAdapter(@NonNull Context context) {
        super(context,R.layout.support_simple_spinner_dropdown_item);
        c = context;
    }

    @Nullable
    @Override
    public Song getItem(int position) {
        return MediaController.getInstance().songs.get(position);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater l = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final Song s = MediaController.getInstance().songs.get(position);

        SongHolder holder = null;
        if (convertView == null)
        {
            convertView = l.inflate(R.layout.layout_song, null);
            holder = new SongHolder();
            holder.songTItle = (TextView)convertView.findViewById(R.id.songTitle);
            holder.songArtist = (TextView)convertView.findViewById(R.id.songArtist);
            holder.songDuration = (TextView)convertView.findViewById(R.id.songDuration);
            holder.songIcon = (ImageView) convertView.findViewById(R.id.songIcon);


            final View finalConvertView = convertView;


            convertView.setTag(holder);
        }
        else {
            holder = (SongHolder)convertView.getTag();
        }

        holder.songTItle.setText(MediaController.getInstance().songs.get(position).title);
        holder.songArtist.setText(MediaController.getInstance().songs.get(position).author);
        holder.songDuration.setText(MediaController.getInstance().songs.get(position).duration);

     /*   if(selected != convertView)
            holder.songIcon.setImageResource(R.drawable.ic_insert_chart_black_24dp);
        else
            holder.songIcon.setImageResource(R.drawable.ic_chart_selected);
*/

        convertView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg1) {

                if(selected!=null){
                    Song previousItem = MediaController.getInstance().songs.get(previousPostion);
                    previousItem.selected = false;
                }

                s.selected = true;
                selected = arg1;
                previousPostion = position;
                notifyDataSetChanged();

                MusicPlayerService.currentIndex = previousPostion;
                Intent objIntent = new Intent(c, MusicPlayerService.class);
                objIntent.setAction(MusicPlayerService.ACTION_NEWPLAY);
                c.startService(objIntent);
            }

        });



        if(!s.selected){
            ((ImageView)convertView.findViewById(R.id.songIcon)).setImageResource(R.drawable.ic_insert_chart_black_24dp);
        }else{
            ((ImageView)convertView.findViewById(R.id.songIcon)).setImageResource(R.drawable.ic_chart_selected);
        }

        return convertView;
    }

    @Override
    public int getCount() {
        return MediaController.getInstance().songs.size();
    }

    @Override
    public void onMusicSelected() {
        notifyDataSetChanged();
    }

    class SongHolder {
        TextView songTItle;
        TextView songArtist;
        TextView songDuration;
        ImageView songIcon;
    }
}
