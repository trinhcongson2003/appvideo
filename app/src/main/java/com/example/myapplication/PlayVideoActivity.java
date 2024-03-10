package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.media.AsyncPlayer;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.myapplication.databinding.ActivityPlayVideoBinding;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class PlayVideoActivity extends AppCompatActivity {
    ActivityPlayVideoBinding main;
    private ArrayList<Video> arrayList;
    private HomeAdapter adapter;
    public static Video videodata;
    private Handler handler;
    private Runnable runnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        main=ActivityPlayVideoBinding.inflate(getLayoutInflater());
        setContentView(main.getRoot());

        MainActivity.listVideoPlay.add(videodata);
        if(MainActivity.listVideoPlay.size()<=1){
            main.videoPrev.setEnabled(false);
            main.videoPrev.setBackgroundResource(R.drawable.ic_prev_off);
        }

        handler=new Handler(Looper.getMainLooper());
        runnable=new Runnable() {
            @Override
            public void run() {
                if(main.layoutChucNang.getVisibility()==View.VISIBLE && main.video.isPlaying()){
                   SetAnimation();
                }
                handler.postDelayed(runnable,200);
            }
        };
        arrayList = new ArrayList<>();
        adapter = new HomeAdapter(getApplicationContext(),R.layout.row_home, arrayList);
        main.listView.setAdapter(adapter);
        GetDataVideo();

        main.nameVideo.setText(videodata.getTenVD());
        main.video.setVideoPath(videodata.getVDURL());
        main.video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                main.tongThoiGian.setText(TimeLine(main.video.getDuration()));
                mp.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT);
                SetRunnerTime();
                main.video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        SetAnimation();
                        main.thoiGianHienTai.setText(TimeLine(main.video.getDuration()));
                        main.layoutChucNang.setVisibility(View.VISIBLE);
                        main.pausePlay.setBackgroundResource(R.drawable.ic_refresh);
                    }
                });
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        main.video.start();
                        main.layoutChucNang.setVisibility(View.GONE);
                        SetRunnerTime();
                    }
                },1000);
            }
        });
        Handler layout=new Handler(Looper.getMainLooper());
        Runnable runnable1=new Runnable() {
            @Override
            public void run() {
                main.layoutChucNang.setVisibility(View.VISIBLE);
                SetRunnerTime();
                Runnable runnable2=new Runnable() {
                    @Override
                    public void run() {
                        main.layoutChucNang.setVisibility(View.GONE);
                        SetRunnerTime();
                    }
                };
                layout.postDelayed(runnable2,2000);

            }
        };
        GestureDetector gestureDetector = new GestureDetector(getApplicationContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                float xEvent = e.getX();
                float xPoin = main.video.getWidth();
                //click vao ben phai
                if(xEvent>=xPoin/2){
                    Next10s();
                }
                else {
                    Prev10s();
                }
                return true;
            }
        });
        main.video.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                layout.removeCallbacksAndMessages(null);
                gestureDetector.onTouchEvent(event);
                layout.postDelayed(runnable1,500);
                return true;
                }
        });
        if(main.videoPrev.isEnabled()){
            main.videoPrev.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getApplicationContext(),PlayVideoActivity.class);
                    PlayVideoActivity.videodata=MainActivity.listVideoPlay.get(MainActivity.listVideoPlay.size()-2);//phat video truoc
                    MainActivity.listVideoPlay.remove(MainActivity.listVideoPlay.size()-1);
                    MainActivity.listVideoPlay.remove(MainActivity.listVideoPlay.size()-1);
                    startActivity(intent);
                    finish();
                }
            });
        }
        main.videoNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),PlayVideoActivity.class);
                PlayVideoActivity.videodata=arrayList.get(0);//phat video dau tien trong list
                startActivity(intent);
                finish();
            }
        });
        main.pausePlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pause_Play();
                SetAnimationPause_Play();
            }
        });
        main.seekBarVideo.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                main.thoiGianHienTai.setText(TimeLine((main.video.getDuration()/1000)*progress));
                handler.removeCallbacksAndMessages(null);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                layout.removeCallbacksAndMessages(null);
                main.layoutChucNang.setVisibility(View.VISIBLE);
                SetRunnerTime();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                SeekTo(seekBar.getProgress());
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        main.layoutChucNang.setVisibility(View.GONE);
                        SetRunnerTime();
                    }
                },1000);
                handler.post(runnable);
            }
        });
    }
    private void  SetRunnerTime(){
        if(main.layoutChucNang.getVisibility()==View.VISIBLE){
            handler.post(runnable);
        }
        else {
            handler.removeCallbacks(runnable);
        }
    }
    private void SetAnimation(){
        main.thoiGianHienTai.setText(TimeLine(main.video.getCurrentPosition()));
        SetAnimationPause_Play();
        SetSeekBar(main.video.getCurrentPosition());
    }
    private void Prev10s(){
        int curr=main.video.getCurrentPosition()-10000;//tg hien tai tru di 10s
        if(curr<0){
            main.video.seekTo(1);
        }
        else {
            main.video.seekTo(curr);
        }
        SetSeekBar(curr);
    }
    private void SetSeekBar(int s){
        main.seekBarVideo.setProgress((s*1000)/main.video.getDuration());
    }
    private void Next10s(){
        int curr=main.video.getCurrentPosition()+10000;//tg hien tai cong 10s
        if(curr>main.video.getDuration()){
            main.video.seekTo(main.video.getDuration()-1);
        }
        else {
            main.video.seekTo(curr);
        }
        SetSeekBar(curr);
    }
    private void SeekTo(int timeLine){

        main.video.seekTo(main.video.getDuration()/1000*timeLine);
    }
    private void Pause_Play(){
        if(main.video.isPlaying()){
            main.video.pause();
        }
        else {
            main.video.start();
        }
    }
    private void SetAnimationPause_Play(){
        if(main.video.isPlaying()){
            main.pausePlay.setBackgroundResource(R.drawable.ic_pause);
        }
        else {
            main.pausePlay.setBackgroundResource(R.drawable.ic_play);
        }
    }
    private String TimeLine(int s){
        /*convert millis to appropriate time*/
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(s),
                TimeUnit.MILLISECONDS.toSeconds(s) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(s)));
    }
    public void GetDataVideo(){
        try {
            arrayList.clear();
            Cursor dataVideo = MainActivity.database.GetData("SELECT * FROM Video");
            while (dataVideo.moveToNext()){
                int id = dataVideo.getInt(0);
                String ten = dataVideo.getString(1);
                String url = dataVideo.getString(2);
                String thumb = dataVideo.getString(3);
                int idthumb = getResources().getIdentifier(thumb, null, getBaseContext().getPackageName());
                int timeline = dataVideo.getInt(4);
                int tongtg = dataVideo.getInt(5);
                int history = dataVideo.getInt(6);
                arrayList.add(new Video(id, ten, url, idthumb, timeline, tongtg, history  ));
                adapter.notifyDataSetChanged();
            }
        }catch (Exception e){

        }
    }
}