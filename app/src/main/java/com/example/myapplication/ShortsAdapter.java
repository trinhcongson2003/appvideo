package com.example.myapplication;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ShortsAdapter extends RecyclerView.Adapter<ShortsAdapter.ViewHolder> {

    private ArrayList<DataHander> dthandel;
    private Fragment fragment;
    private List<Integer> randomPositions;
    private Random random = new Random();

    public ShortsAdapter(ArrayList<DataHander> dthandel, Fragment fragment) {
        this.dthandel = dthandel;
        this.fragment = fragment;
        this.randomPositions = generateRandomPositions(dthandel.size());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_shorts, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int randomPosition = randomPositions.get(position);
        DataHander dataHander = dthandel.get(randomPosition);
        holder.tiltle1.setText(dataHander.tiltle);
        holder.tiltle2.setText(dataHander.tiltle);
        holder.vdv.setVideoURI(Uri.parse(dthandel.get(randomPosition).url));
        holder.vdv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                holder.progressBar.setVisibility(View.GONE);
                mediaPlayer.start();
                float vidRatio = mediaPlayer.getVideoWidth() / (float) mediaPlayer.getVideoWidth();
                float screenRatio = holder.vdv.getWidth() / (float) holder.vdv.getHeight();
                float scale = vidRatio / screenRatio;
                if (scale >= 1) {
                    holder.vdv.setScaleX(scale);
                } else {
                    holder.vdv.setScaleY(1f / scale);
                }

                holder.seekBar.setMax(mediaPlayer.getDuration() / 1000);
            }
        });

        holder.vdv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });

        holder.vdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.vdv.isPlaying()) {
                    holder.vdv.pause();
                } else {
                    holder.vdv.start();
                }
            }
        });

        holder.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    holder.timeLine = progress;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                holder.vdv.seekTo(holder.timeLine * 1000);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dthandel.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        VideoView vdv;
        ProgressBar progressBar;
        TextView tiltle1, tiltle2;
        ImageView imgv1, imgv2, imgv3, imgv4;
        SeekBar seekBar;
        int timeLine;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            vdv = itemView.findViewById(R.id.videoView);
            progressBar = itemView.findViewById(R.id.videoProgressBar);
            tiltle1 = itemView.findViewById(R.id.textVideoTitle);
            tiltle2 = itemView.findViewById(R.id.textVideoDescription);
            imgv1 = itemView.findViewById(R.id.imgv1);
            imgv2 = itemView.findViewById(R.id.favorites);
            imgv3 = itemView.findViewById(R.id.imgv2);
            imgv4 = itemView.findViewById(R.id.imgv3);
            seekBar = itemView.findViewById(R.id.seekBarVideo);
        }
    }

    private List<Integer> generateRandomPositions(int size) {
        List<Integer> positions = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            positions.add(i);
        }
        Collections.shuffle(positions); // Trộn danh sách vị trí
        return positions;
    }
}


