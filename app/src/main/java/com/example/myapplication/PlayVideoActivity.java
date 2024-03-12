package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.media.AsyncPlayer;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.myapplication.databinding.ActivityPlayVideoBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class PlayVideoActivity extends AppCompatActivity {
    private ActivityPlayVideoBinding main;
    private SharedPreferences sharedPreferences;
    private ArrayList<Video> arrayList;
    private ArrayList<Video> listHistory;
    private HomeAdapter adapter;
    private HistoryAdapter historyAdapter;
    public static Video videodata;
    private Handler handler;
    private Runnable runnable;
    private int maxSec=0;
    private boolean videoPlay;
    private int currSec=0;
    private boolean fullSceen=false;
    public static boolean home_history=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        main=ActivityPlayVideoBinding.inflate(getLayoutInflater());
        setContentView(main.getRoot());

        sharedPreferences=getApplicationContext().getSharedPreferences("history",MODE_PRIVATE);

        if(home_history){
            arrayList = new ArrayList<>();
            adapter = new HomeAdapter(getApplicationContext(),R.layout.row_home, arrayList);
            main.listView.setAdapter(adapter);
            MainActivity.listVideoPlay.add(videodata);
            if(MainActivity.listVideoPlay.indexOf(videodata)<=0){
                main.videoPrev.setEnabled(false);
                main.videoPrev.setBackgroundResource(R.drawable.ic_prev_off);
            }
            GetDataVideo();
        }else {
            String dataString=sharedPreferences.getString("listHistory","");
            Gson gson=new Gson();
            main.title.setText("Lịch Sử");
            Type type=new TypeToken<ArrayList<Video>>(){}.getType();
            ArrayList<Video> videos=new ArrayList<>();
            videos=gson.fromJson(dataString,type);
            listHistory=new ArrayList<>();
            historyAdapter=new HistoryAdapter(getApplicationContext(),R.layout.row_history,listHistory);
            main.listView.setAdapter(historyAdapter);
            listHistory.addAll(videos);
            historyAdapter.notifyDataSetChanged();

        }

        Handler handler=new Handler(Looper.getMainLooper());
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

        main.nameVideo.setText(videodata.getTenVD());
        main.video.setVideoPath(videodata.getVDURL());
        main.video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                int duration=main.video.getDuration();
                maxSec=duration/1000+1;
                if(home_history==false) {
                    currSec=videodata.getTimeline();
                    SetAnimation();
                    main.video.seekTo(videodata.getTimeline() * 1000);
                }
                main.seekBarVideo.setMax(maxSec);
                main.tongThoiGian.setText(TimeLine(maxSec));

                main.video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        SetAnimation();
                        videoPlay=false;
                        currSec=0;
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
                layout.removeCallbacksAndMessages(runnable1);

                //click vao ben phai
                if(xEvent>=xPoin/2){
                    Next10s();
                    //animation
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            main.animationNext.setVisibility(View.GONE);
                            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    main.animationNext.setVisibility(View.VISIBLE);
                                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            main.animationNext.setVisibility(View.GONE);
                                        }
                                    },200);
                                }
                            },200);
                        }
                    },200);
                }
                else {
                    Prev10s();
                    //animation
                    main.animationPrev.setVisibility(View.VISIBLE);
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            main.animationPrev.setVisibility(View.GONE);
                            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    main.animationPrev.setVisibility(View.VISIBLE);
                                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            main.animationPrev.setVisibility(View.GONE);
                                        }
                                    },200);
                                }
                            },200);
                        }
                    },200);

                }
                return true;
            }
        });
        main.viewVideo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                float startY=0;
//                if(event.getAction()==MotionEvent.ACTION_DOWN){
//                    startY=event.getY();
//                }
//                else if(event.getAction()==MotionEvent.ACTION_UP){
//                    if(startY < event.getY()-50){
//                        ThuNhoCuaSo();
//                    }
//                }
                layout.removeCallbacksAndMessages(null);
                layout.postDelayed(runnable1,300);
                gestureDetector.onTouchEvent(event);
                return true;
                }
        });
        main.nameVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThuNhoCuaSo();
            }
        });
        if(home_history){
            if(main.videoPrev.isEnabled()){
                main.videoPrev.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(getApplicationContext(),PlayVideoActivity.class);
                        int index=MainActivity.listVideoPlay.indexOf(videodata);
                        PlayVideoActivity.videodata=MainActivity.listVideoPlay.get(index-1);//phat video truoc
                        MainActivity.listVideoPlay.remove(index);
                        SaveVideoHistory();
                        startActivity(intent);
                        finish();
                    }
                });
            }
            main.videoNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getApplicationContext(),PlayVideoActivity.class);
                    int index=MainActivity.listVideoPlay.indexOf(videodata);
                    if(index>MainActivity.listVideoPlay.size()){
                        PlayVideoActivity.videodata=MainActivity.listVideoPlay.get(index+1);//phat video sau
                        MainActivity.listVideoPlay.remove(index);

                    }
                    else {
                        PlayVideoActivity.videodata=arrayList.get(0);//phat video sau
                    }
                    startActivity(intent);
                    finish();
                }
            });
        }
        else {
            main.videoNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        videodata=listHistory.get(listHistory.indexOf(videodata)+1);
                    }catch (Exception e){
                        videodata=listHistory.get(0);
                    }
                    main.video.setVideoPath(videodata.getVDURL());
                }
            });
            main.videoNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        videodata=listHistory.get(listHistory.indexOf(videodata)+1);
                    }catch (Exception e){
                        videodata=listHistory.get(listHistory.size()-1);
                    }
                    main.video.setVideoPath(videodata.getVDURL());
                }
            });
        }
        main.pausePlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pause_Play();
                SetAnimationPause_Play();
            }
        });
        main.videoXemTruoc.setVideoPath(MainActivity.videoPath+R.raw.adidas);
        main.seekBarVideo.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                main.thoiGianHienTai.setText(TimeLine(progress));
                main.videoXemTruoc.seekTo(progress*1000);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                layout.removeCallbacksAndMessages(null);
                main.videoXemTruoc.setVisibility(View.VISIBLE);
                main.layoutChucNang.setVisibility(View.VISIBLE);
                handler.removeCallbacks(runnable);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                main.videoXemTruoc.setVisibility(View.GONE);
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
        main.seekBarVideo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                layout.removeCallbacksAndMessages(null);
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    // Lấy tọa độ x, y của thumb trong SeekBar
                    float x = event.getX();
                    float tmpX=x-main.videoXemTruoc.getWidth()/2;
                    float pX= tmpX < 0? 0 :tmpX;

                    main.videoXemTruoc.setX(pX);
                }
                return false;
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
//--------------------------------------------------------Ham-------------------------------------------

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SaveVideoHistory();
    }
    private void ThuNhoCuaSo(){
//        WindowManager.LayoutParams params = getWindow().getAttributes();
//
//        // Đặt chiều rộng và chiều cao mong muốn (đơn vị là pixel)
//        params.width = 400; // Chiều rộng
//        params.height = 600; // Chiều cao
//
//        // Áp dụng thay đổi cho cửa sổ
//        getWindow().setAttributes(params);
    }
    private void SaveVideoHistory(){
        if(videodata!=null){
            if(MainActivity.listHistoryVideo==null)
                MainActivity.listHistoryVideo=new ArrayList<>();
            int max=10;Video vdHistory =videodata;
                if(MainActivity.listHistoryVideo.size()>=max){
                    MainActivity.listHistoryVideo.remove(0);

                }
                for (int i = 0; i < MainActivity.listHistoryVideo.size(); i++) {
                    if(MainActivity.listHistoryVideo.get(i).getIdVD()== vdHistory.getIdVD()){
                        MainActivity.listHistoryVideo.remove(i);
                        break;
                    }
        }
        vdHistory.setTimeline(currSec);
        MainActivity.listHistoryVideo.add(0,vdHistory);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            Gson gson=new Gson();
            String gsonString=gson.toJson(MainActivity.listHistoryVideo);
            editor.putString("listHistory",gsonString);
            editor.apply();
        }
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
        if(currSec>=maxSec){
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
                main.layoutChucNang.setVisibility(View.GONE);
            }
            main.video.start();
            videoPlay=true;
            SetAnimation();
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
                boolean check=true;
                while (dataVideo.moveToNext()) {
                    int id = dataVideo.getInt(0);
                    String ten = dataVideo.getString(1);
                    String url = dataVideo.getString(2);
                    String thumb = dataVideo.getString(3);
                    int idthumb = getResources().getIdentifier(thumb, null, getBaseContext().getPackageName());
                    int timeline = dataVideo.getInt(4);
                    int tongtg = dataVideo.getInt(5);
                    int history = dataVideo.getInt(6);
                    for (Video x :
                            MainActivity.listVideoPlay) {
                        if(x.getIdVD()==id){
                            check=false;
                        }
                    }
                    if(check){
                        arrayList.add(new Video(id, ten, url, idthumb, timeline, tongtg, history));
                    }
                    check=true;

                }
            adapter.notifyDataSetChanged();
        }catch (Exception e){

        }
    }
}