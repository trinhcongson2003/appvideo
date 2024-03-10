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
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class YouFragment extends Fragment implements AdapterView.OnItemClickListener {
    HistoryAdapter historyAdapter;

    View view;
    ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_you, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        historyAdapter = new HistoryAdapter(getContext(),R.layout.row_history, MainActivity.listHistoryVideo);

        listView = (ListView) view.findViewById(R.id.listHistory);
        listView.setAdapter(historyAdapter);
        GetDataVideo();
    }

    public void GetDataVideo(){
        MainActivity.listHistoryVideo.clear();
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
            MainActivity.listHistoryVideo.add(new Video(id, ten, url, idthumb, timeline, tongtg, history  ));
            historyAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}

