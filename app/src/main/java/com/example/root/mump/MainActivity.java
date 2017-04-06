package com.example.root.mump;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity implements OnMusicStateChangedListener {
    boolean isPlaying;
    boolean isStreaming;
    public OnMusicSelectedListener omsl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                MediaController.getInstance().c = MainActivity.this;
                MediaController.getInstance().loadSongs();
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Typeface simplyRoundedBold = Typeface.createFromAsset(getAssets(),"fonts/Simply Rounded Bold.ttf");
        ((TextView)findViewById(R.id.Logo)).setTypeface(simplyRoundedBold);
        ((RelativeLayout)findViewById(R.id.splashLayout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ((RelativeLayout)findViewById(R.id.splashLayout)).animate().alpha(0.0f).setDuration(1000).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            ((RelativeLayout)findViewById(R.id.splashLayout)).setVisibility(GONE);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });



        final TabLayout tl = (TabLayout) findViewById(R.id.activitySwitcherLayout);

        final ViewPager vp = (ViewPager) findViewById(R.id.activitySwitcher);
        final FragmentViewPagerAdapter adapter = new FragmentViewPagerAdapter(getSupportFragmentManager(), this);


        vp.setAdapter(adapter);
        vp.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tl));
        tl.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vp.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 1) { isStreaming = true; startStreamService();}
                else { isStreaming = false; stopStreamService(); }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });


        MusicPlayerService.osl = this;
        final ImageButton play = (ImageButton) findViewById(R.id.playButton);
        final ImageButton previous = (ImageButton) findViewById(R.id.previousButton);
        final ImageButton next = (ImageButton) findViewById(R.id.nextButton);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPlaying = !isPlaying;
                if(!isPlaying) {
                     play.setImageResource(R.drawable.ic_play_circle_filled_black_24dp);
                }
                else
                {
                    play.setImageResource(R.drawable.ic_pause_circle_filled_black_24dp);
                }
                Intent objIntent = new Intent(MainActivity.this, MusicPlayerService.class);
                objIntent.setAction(MusicPlayerService.ACTION_PLAY);
                startService(objIntent);
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent objIntent = new Intent(MainActivity.this, MusicPlayerService.class);
                    objIntent.setAction(MusicPlayerService.ACTION_PREVIOUS);
                    startService(objIntent);
                    play.setImageResource(R.drawable.ic_pause_circle_filled_black_24dp);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent objIntent = new Intent(MainActivity.this, MusicPlayerService.class);
                    objIntent.setAction(MusicPlayerService.ACTION_NEXT);
                    startService(objIntent);
                    play.setImageResource(R.drawable.ic_pause_circle_filled_black_24dp);
            }
        });
    }

    @Override
    public void OnMusicStateChanged(Song s, int state) {
        TextView songTitle = (TextView) findViewById(R.id.mainSongTitle);
        TextView songAuthor = (TextView) findViewById(R.id.mainSongArtist);
        songAuthor.setText(s.author);
        songTitle.setText(s.title);
        if (state == 1)
            ((ImageButton) findViewById(R.id.playButton)).setImageResource(R.drawable.ic_pause_circle_filled_black_24dp);
        else
            ((ImageButton) findViewById(R.id.playButton)).setImageResource(R.drawable.ic_play_circle_filled_black_24dp);
        for(Song si : MediaController.getInstance().songs)
            si.selected = false;
        s.selected = true;
        omsl.onMusicSelected();
    }

    public void startStreamService() {
        Intent objIntent = new Intent(MainActivity.this, MusicPlayerService.class);
        objIntent.setAction(MusicPlayerService.ACTION_STREAM);
        startService(objIntent);

    }

    public void stopStreamService() {
        Intent objIntent = new Intent(MainActivity.this, MusicPlayerService.class);
        objIntent.setAction(MusicPlayerService.ACTION_UNSTREAM);
        startService(objIntent);

    }
}
