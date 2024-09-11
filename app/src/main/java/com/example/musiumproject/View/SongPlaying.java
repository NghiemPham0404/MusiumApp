package com.example.musiumproject.View;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.musiumproject.R;
import com.example.musiumproject.models.Track;
import com.example.musiumproject.util.Credentials;
import com.example.musiumproject.util.TrackPlayingType;
import com.example.musiumproject.viewmodels.TrackViewModel;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SongPlaying extends AppCompatActivity implements View.OnClickListener {
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");

    //TODO : Components
    TextView playFromTxt, titleTxt, artistsTxt, currentPositionTxt, maxDurationTxt;
    SeekBar seekBar;
    ImageButton mixBtn, backwardBtn, forwardBtn, backBtn;
    ImageView trackCover;
    View playBtn,playBtnSymbol;

    MediaPlayer musicPlayer;
    Handler handler;
    Runnable runnable;

    TrackViewModel trackViewModel;
    List<Track> tracks;
    Track track;
    int track_id, index;
    TrackPlayingType trackPlayingType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_playing);

        track_id = getIntent().getIntExtra("id",  -1);
        index = getIntent().getIntExtra("index", -1);

        Log.i("request track id : ", track_id+"");

        trackViewModel =new ViewModelProvider(this).get(TrackViewModel.class);
        initComponents();
        initFeature();
        observeAnyChange();
    }

    private void observeAnyChange() {
        if(trackViewModel!=null){
            trackViewModel.getLiveTrack().observe(this, track -> {
                if(track != null){
                    this.track = track;
                    initMusicPlayer();
                    fillUpCoponents();
                }
            });
            trackViewModel.getLiveListTracks().observe(this, tracks ->{
                this.tracks = tracks;
            });
        }
    }

    public void initComponents(){
        playFromTxt = findViewById(R.id.playFromTxt);
        titleTxt = findViewById(R.id.trackTitleTxt);
        artistsTxt = findViewById(R.id.artistStrTxt);
        currentPositionTxt = findViewById(R.id.positionTxt);
        maxDurationTxt = findViewById(R.id.durationTxt);

        trackCover = findViewById(R.id.trackCover);

        seekBar = findViewById(R.id.seekBar);
        mixBtn = findViewById(R.id.mixBtn);
        backwardBtn = findViewById(R.id.backwardBtn);
        playBtn = findViewById(R.id.playBtn);
        playBtnSymbol = findViewById(R.id.playBtnSysbol);
        forwardBtn = findViewById(R.id.forwardBtn);
        backBtn = findViewById(R.id.backBtn);
    }

    public void initFeature(){
        trackViewModel.getTrackById(track_id);

        playBtn.setOnClickListener(this);
        forwardBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        backwardBtn.setOnClickListener(this);
    }

    public void fillUpCoponents(){
        playFromTxt.setText(getIntent().getStringExtra("origin"));
        titleTxt.setText(track.getTitle());
        artistsTxt.setText(track.getArtistStr());
        Glide.with(this).load(Uri.parse(Credentials.STORAGE_URL + "/track_img" + track.getTrackImg())).into(trackCover);
    }

    public void initMusicPlayer(){
        musicPlayer =  new MediaPlayer();
        try {
            musicPlayer.setDataSource(Credentials.STORAGE_URL + Credentials.TRACK + "/" + track.getPlayLink());
            musicPlayer.prepare();
            musicPlayer.start();
            musicPlayer.setOnPreparedListener(mp -> playBtnSymbol.setBackgroundDrawable(getResources().getDrawable(R.drawable.pause)));
            musicPlayer.setOnCompletionListener(mp -> {
                playNextTrack();
            });
            seekBar.setMax(musicPlayer.getDuration());
            initSeekBarAction();
        }catch (IOException e){
            Log.e("music player error : ", e.toString());
        }
        initializeProgressUpdater();
    }

    public void initializeProgressUpdater(){
        maxDurationTxt.setText(formatDuration(musicPlayer.getDuration()));
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                updateCurrentDuration();
                handler.postDelayed(runnable, 500);
            }
        };
        handler.post(runnable);
    }

    public void initSeekBarAction(){
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    musicPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void updateCurrentDuration() {
        currentPositionTxt.setText(formatDuration(musicPlayer.getCurrentPosition()));
        seekBar.setProgress(musicPlayer.getCurrentPosition());
    }

    public String formatDuration(int duration){
          return simpleDateFormat.format(new Date(duration));
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.playBtn){
            if(musicPlayer.isPlaying()){
                musicPlayer.pause();
                playBtnSymbol.setBackgroundDrawable(getResources().getDrawable(R.drawable.triangle));
            }else{
                musicPlayer.start();
                playBtnSymbol.setBackgroundDrawable(getResources().getDrawable(R.drawable.pause));
            }
        }else if(v.getId() == R.id.forwardBtn){
            playNextTrack();
        }else if(v.getId() == R.id.backwardBtn){
            playPreviousTrack();
        }else if(v.getId() == R.id.backBtn){
            finish();
        }
    }

    private void playNextTrack() {
        if(!tracks.isEmpty()){
            if(index < tracks.size() -1 ){
                trackViewModel.getTrackById(tracks.get(++index).getId());
            }
        }
    }

    private void playPreviousTrack(){
        if(index > 0 && !tracks.isEmpty()){
            trackViewModel.getTrackById(tracks.get(--index).getId());
        }
    }
}