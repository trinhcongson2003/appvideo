package com.example.myapplication;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class YouFragment extends Fragment {

    private VideoView videoView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_you, container, false);

        // Tìm kiếm VideoView trong giao diện
        videoView = rootView.findViewById(R.id.videoView);

        // Đặt đường dẫn của video mặc định
        String videoPath = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.vd1;

        // Chuyển đổi đường dẫn thành Uri
        Uri uri = Uri.parse(videoPath);

        // Tải video vào VideoView
        videoView.setVideoURI(uri);

        // Bắt đầu phát video
        videoView.start();

        return rootView;
    }
}

