package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class HomeAdapter extends BaseAdapter {
    private MainActivity mainActivity;
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
    private class ViewHolder{
        TextView txtTongTG, txtTenVid;
        ImageView imgVid;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) mainActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.txtTongTG = view.findViewById(R.id.vidTongTG);
            holder.txtTenVid = view.findViewById(R.id.vidName);
            holder.imgVid = view.findViewById(R.id.vidThumbnail);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        Video video = VideoList.get(i);
        int d = video.getTongTG();
        String duration = String.format("%d:%d", TimeUnit.MILLISECONDS.toMinutes(d), TimeUnit.MILLISECONDS.toSeconds(d) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(d)));
        holder.txtTongTG.setText(duration);
        holder.txtTenVid.setText(video.getTenVD());
        holder.imgVid.setImageResource(video.getThumbnail());
        return view;
    }
}
