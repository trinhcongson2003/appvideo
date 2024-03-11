package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class HistoryAdapter extends BaseAdapter{
    private Context context;
    private int layout;
    ArrayList<Video> VideoList;
    public HistoryAdapter(Context context, int layout, ArrayList<Video> videoList) {
        this.context = context;
        this.layout = layout;
        this.VideoList = videoList;
    }
    @Override
    public int getCount() {
        try{
            int x= VideoList.size();
            return x;
        }catch (Exception e){
            return  0;
        }
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
        RelativeLayout layout;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.txtTongTG = view.findViewById(R.id.vidTongTGH);
            holder.txtTenVid = view.findViewById(R.id.vidNameH);
            holder.imgVid = view.findViewById(R.id.vidImgH);
            holder.layout=view.findViewById(R.id.layoutHistory);
            view.setTag(holder);
        } else {
            holder = (HistoryAdapter.ViewHolder) view.getTag();
        }
        Video video = VideoList.get(i);
        int d = video.getTongTG();
        String duration = String.format("%d:%d", TimeUnit.MILLISECONDS.toMinutes(d), TimeUnit.MILLISECONDS.toSeconds(d) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(d)));
        holder.txtTongTG.setText(duration);
        holder.txtTenVid.setText(video.getTenVD());
        holder.imgVid.setImageResource(video.getThumbnail());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,PlayVideoActivity.class);
                PlayVideoActivity.videodata=video;
                PlayVideoActivity.home_history=false;
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );//Ket thuc Acvitivy cu va mo Activity moi
                context.startActivity(intent);
            }
        });
        return view;
    }
}
