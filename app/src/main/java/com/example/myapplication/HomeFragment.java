package com.example.myapplication;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements AdapterView.OnItemClickListener {
//    Database database;
//    ArrayList<Video> arrayVideo;
    HomeAdapter homeAdapter;
    View view;
    ListView listView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        String videoPath = "android.resource://" + requireContext().getPackageName() + "/" + R.raw.vd;
        view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homeAdapter = new HomeAdapter(getContext(),R.layout.row_home, MainActivity.listHomeVideo);

        listView = (ListView) view.findViewById(R.id.listVidHome);
        listView.setAdapter(homeAdapter);
        GetDataVideo();
    }

    public void GetDataVideo(){
        MainActivity.listHomeVideo.clear();
        Cursor dataVideo = MainActivity.database.GetData("SELECT * FROM Video");
        while (dataVideo.moveToNext()){
            int id = dataVideo.getInt(0);
            String ten = dataVideo.getString(1);
            String url = dataVideo.getString(2);
            String thumb = dataVideo.getString(3);
            int idthumb = getResources().getIdentifier(thumb, null, getActivity().getPackageName());
            int timeline = dataVideo.getInt(4);
            int tongtg = dataVideo.getInt(5);
            int history = dataVideo.getInt(6);
            MainActivity.listHomeVideo.add(new Video(id, ten, url, idthumb, timeline, tongtg, history  ));
        }
        homeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}


