package com.example.myapplication;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.widget.ViewPager2;
import java.util.ArrayList;
import java.util.Random;

public class ShortsFragment extends Fragment {

    private ArrayList<DataHander> dataHanders = new ArrayList<>();
    ViewPager2 viewPager2;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.shorts_main, container, false);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        DataHander data1 = new DataHander("Colors for Children to Learn with Truck TransporterColors for Children", "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4");
        DataHander data2 = new DataHander("Transportation Vocabulary and Vehicle Names", "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyrides.mp4");
        DataHander data3 = new DataHander("テスト　人狼のための子守唄", "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4");
        DataHander data4 = new DataHander("시간 반복 재생] 너의 이름은 OST | '미츠하 테마'", "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4");
        DataHander data5 = new DataHander("夜に聴きたいフリーBGM11曲", "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WhatCarCanYouGetForAGrand.mp4");
        DataHander data6 = new DataHander("CALL OF SILENCE x AKUMACALL OF SILENCE x AKUMA", "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WeAreGoingOnBullrun.mp4");
        DataHander data7 = new DataHander("I've been burning my heart out", "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4");
        DataHander data8 = new DataHander("You will know you're reborn tonight", "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4");
        DataHander data9 = new DataHander("Go ahead be proud", "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4");
        DataHander data10 = new DataHander("To homeTo home", "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4");

//        WebView webView = view.findViewById(R.id.webView);
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.setWebViewClient(new WebViewClient());
//        webView.loadUrl("https://www.youtube.com/embed/WxiG6JY77bM");


        dataHanders.add(data1);
        dataHanders.add(data2);
        dataHanders.add(data3);
        dataHanders.add(data4);
        dataHanders.add(data5);
        dataHanders.add(data6);
        dataHanders.add(data7);
        dataHanders.add(data8);
        dataHanders.add(data9);
        dataHanders.add(data10);

        viewPager2 = view.findViewById(R.id.vpager);
        ShortsAdapter pagerAdapter = new ShortsAdapter(dataHanders, this);
        viewPager2.setAdapter(pagerAdapter);

        return view;
    }

}



