package com.example.myapplication;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class HomeAdapter extends BaseAdapter {
    private HomeFragment context;
    private int layout;
    ArrayList<Video> VideoList;
    public HomeAdapter(HomeFragment context, int layout, ArrayList<Video> videoList) {
        this.context = context;
        this.layout = layout;
        this.VideoList = videoList;
    }

    @Override
    public int getCount() {
        return VideoList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
