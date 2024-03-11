package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;

public class YouFragment extends Fragment implements AdapterView.OnItemClickListener {
    HistoryAdapter historyAdapter;
    private SharedPreferences sharedPreferences;


    View view;
    ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences=getContext().getSharedPreferences("history", Context.MODE_PRIVATE);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_you, container, false);
       try {
           historyAdapter = new HistoryAdapter(getContext(),R.layout.row_history, MainActivity.listHistoryVideo);
           listView = (ListView) view.findViewById(R.id.listHistory);
           listView.setAdapter(historyAdapter);
           GetDataVideo();
       }catch (Exception e){

       }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void GetDataVideo(){
        String dataString=sharedPreferences.getString("listHistory","");
        Gson gson=new Gson();
        Type type=new TypeToken<ArrayList<Video>>(){}.getType();
        MainActivity.listHistoryVideo=gson.fromJson(dataString,type);
        historyAdapter.notifyDataSetChanged();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}

