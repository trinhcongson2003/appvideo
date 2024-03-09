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

        DataHander data1 = new DataHander("title 1", "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4");
        DataHander data2 = new DataHander("this is title 2", "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyrides.mp4");
        DataHander data3 = new DataHander("this is title 3", "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4");
        DataHander data4 = new DataHander("this is title 4", "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4");
        DataHander data5 = new DataHander("this is title 5", "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WhatCarCanYouGetForAGrand.mp4");
        DataHander data6 = new DataHander("this is title 6", "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WeAreGoingOnBullrun.mp4");

        dataHanders.add(data1);
        dataHanders.add(data2);
        dataHanders.add(data3);
        dataHanders.add(data4);
        dataHanders.add(data5);
        dataHanders.add(data6);

        viewPager2 = view.findViewById(R.id.vpager);
        ShortsAdapter pagerAdapter = new ShortsAdapter(dataHanders, this);
        viewPager2.setAdapter(pagerAdapter);
        return view;
    }

}



