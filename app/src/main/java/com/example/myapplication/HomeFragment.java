package com.example.myapplication;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {
    VideoView vd, vd1;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        vd = view.findViewById(R.id.video);
        String videoPath = "android.resource://" + requireContext().getPackageName() + "/" + R.raw.vd;
        Uri uri = Uri.parse(videoPath);
        vd.setVideoURI(uri);
        MediaController mediaController = new MediaController(requireContext());
        vd.setMediaController(mediaController);

        return view;
    }
}


