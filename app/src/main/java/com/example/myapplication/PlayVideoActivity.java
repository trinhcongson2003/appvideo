package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.example.myapplication.databinding.ActivityPlayVideoBinding;

import java.util.ArrayList;

public class PlayVideoActivity extends AppCompatActivity {
    ActivityPlayVideoBinding main;
    private ArrayList<Video> arrayList;
    private HomeAdapter adapter;
    public static Video videodata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        main=ActivityPlayVideoBinding.inflate(getLayoutInflater());
        setContentView(main.getRoot());

        arrayList = new ArrayList<>();
        adapter = new HomeAdapter(getApplicationContext(),R.layout.row_home, arrayList);
        main.listView.setAdapter(adapter);
        GetDataVideo();


        main.nameVideo.setText(videodata.getTenVD());

        main.video.setVideoPath(MainActivity.videoPath+R.raw.vd1);
        main.video.start();
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
                SetAnimationPause_Play();
                return true;
            }
        });
        main.video.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });
        main.videoPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Prev10s();

            }
        });
        main.videoNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Next10s();
            }
        });
        main.pausePlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pause_Play();
                SetAnimationPause_Play();
            }
        });
    }
    private void Prev10s(){
        int curr=main.video.getCurrentPosition()-10000;//tg hien tai tru di 10s
        if(curr<0){
            main.video.seekTo(1);
        }
        else {
            main.video.seekTo(curr);
        }
    }
    private void Next10s(){
        int curr=main.video.getCurrentPosition()+10000;//tg hien tai cong 10s
        if(curr>main.video.getDuration()){
            main.video.seekTo(main.video.getDuration());
        }
        else {
            main.video.seekTo(curr);
        }
    }
    private void SeekTo(int timeLine){
        main.video.seekTo(timeLine);
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
        int timeLine=s/1000;
        int giay=timeLine%60;
        int phut=timeLine/60;
        return String.format("%02d:%02d", phut, giay);
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