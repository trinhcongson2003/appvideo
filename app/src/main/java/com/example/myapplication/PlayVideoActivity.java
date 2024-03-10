package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.media.AsyncPlayer;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
    private int maxSec=0;
    private boolean videoPlay;
    private int currSec=0;
    private boolean fullSceen=false;
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
                if(videoPlay){
                   currSec++;
                   SetAnimation();
                }
                handler.postDelayed(runnable,1000);
            }
        };
        arrayList = new ArrayList<>();
        adapter = new HomeAdapter(getApplicationContext(),R.layout.row_home, arrayList);
        main.listView.setAdapter(adapter);
        GetDataVideo();

        main.nameVideo.setText(videodata.getTenVD());
        main.video.setVideoPath(MainActivity.videoPath+R.raw.vd1);
        main.video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                maxSec=main.video.getDuration()/1000;
                main.seekBarVideo.setMax(maxSec);
                main.tongThoiGian.setText(TimeLine(maxSec));
                mp.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT);
                main.video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        SetAnimation();
                        videoPlay=false;
                        main.thoiGianHienTai.setText(TimeLine(maxSec));
                        main.layoutChucNang.setVisibility(View.VISIBLE);
                        main.pausePlay.setBackgroundResource(R.drawable.ic_refresh);
                    }
                });
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        main.video.start();
                        videoPlay=true;
                        handler.post(runnable);
                        main.layoutChucNang.setVisibility(View.GONE);
                    }
                },1000);
            }
        });
        Handler layout=new Handler(Looper.getMainLooper());
        Runnable runnable1=new Runnable() {
            @Override
            public void run() {
                main.layoutChucNang.setVisibility(View.VISIBLE);
                Runnable runnable2=new Runnable() {
                    @Override
                    public void run() {
                        main.layoutChucNang.setVisibility(View.GONE);
                    }
                };
                layout.postDelayed(runnable2,2000);

            }
        };
        GestureDetector gestureDetector = new GestureDetector(getApplicationContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                float xEvent = e.getX();
                float xPoin = main.viewVideo.getWidth();
                //click vao ben phai
                if(xEvent>=xPoin/2){
                    Next10s();
                    Toast.makeText(PlayVideoActivity.this, "Phai", Toast.LENGTH_SHORT).show();
                }
                else {
                    Prev10s();
                    Toast.makeText(PlayVideoActivity.this, "Trai", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
        main.viewVideo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                layout.removeCallbacksAndMessages(null);
                layout.postDelayed(runnable1,300);
                gestureDetector.onTouchEvent(event);
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
                main.thoiGianHienTai.setText(TimeLine(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                layout.removeCallbacksAndMessages(null);
                main.layoutChucNang.setVisibility(View.VISIBLE);
                handler.removeCallbacks(runnable);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                SeekTo(seekBar.getProgress());
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        main.layoutChucNang.setVisibility(View.GONE);
                    }
                },1000);
                handler.post(runnable);
            }
        });
        main.thuPhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullSceen= fullSceen==false ?true:false;
                SetFullSceen();
            }
        });
    }
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Kiểm tra hướng xoay của màn hình
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            fullSceen=true;
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            // Xử lý khi màn hình xoay dọc
            fullSceen=false;
        }
        SetFullSceen();
    }


    private void SetFullSceen(){
        if(fullSceen){
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
            ViewGroup.LayoutParams params = main.layoutXemVideo.getLayoutParams();
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            main.layoutXemVideo.setLayoutParams(params);
        }
        else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

            int dpValue = 250; // Số dp bạn muốn
            float density = getResources().getDisplayMetrics().density;
            int pixelValue = (int) (dpValue * density);
            ViewGroup.LayoutParams params = main.layoutXemVideo.getLayoutParams();
            params.height = pixelValue;
            main.layoutXemVideo.setLayoutParams(params);
        }
    }
    private void SetAnimation(){
        SetAnimationPause_Play();
        SetSeekBar(currSec);
    }
    private void SetSeekBar(int s){
        main.seekBarVideo.setProgress(s);
    }
    private void Prev10s(){
        currSec-=10;
        if(currSec<0){
            currSec=0;
            main.video.seekTo(0);
        }
        else {
            main.video.seekTo(currSec*1000);
        }
        SetSeekBar(currSec);
    }
    private void Next10s(){
        currSec+=10;
        if(currSec>maxSec){
            currSec=maxSec;
            main.video.seekTo(currSec*1000);
        }
        else {
            main.video.seekTo(currSec*1000);
        }
        SetSeekBar(currSec);
    }
    private void SeekTo(int timeLine){
        main.video.seekTo(timeLine*1000);
        currSec=timeLine;
    }
    private void Pause_Play(){
        if(main.video.isPlaying()){
            main.video.pause();
            videoPlay=false;
        }
        else {
            if(currSec==maxSec){
                main.video.seekTo(0);
                currSec=0;
                main.layoutChucNang.setVisibility(View.GONE);
            }
            SetAnimation();
            main.video.start();
            videoPlay=true;
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
        int minutes = s/ 60;
        int seconds = s % 60;
        // Sử dụng định dạng chuỗi để hiển thị "phút:giây"
        return String.format("%02d:%02d", minutes, seconds);
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