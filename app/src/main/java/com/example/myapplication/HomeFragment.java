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
    Database database;
    ArrayList<Video> arrayVideo;
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
        arrayVideo = new ArrayList<>();
        homeAdapter = new HomeAdapter(getContext(),R.layout.row_home, arrayVideo);
        //Database
        database = new Database(getActivity(), "VideoPlayer.sql", null, 1);
        //Tạo bảng video
        database.QueryData("CREATE TABLE IF NOT EXISTS Video(IdVD INTEGER PRIMARY KEY AUTOINCREMENT, TenVD VARCHAR(500), VDURL VARCHAR(300), Thumbnail VARCHAR(300), Timeline INTEGER, TongTG INTEGER, IdHistory INTEGER)");
//        database.QueryData("DROP  TABLE IF EXISTS Video");
        //Chèn CSDL vào bảng Video
        database.QueryData("INSERT INTO Video VALUES(null,'When Rank 1 Katarina meet Yasuo - Engsub','R.raw.vd1','@drawable/rank1kata','0','593000','0')");
        database.QueryData("INSERT INTO Video VALUES(null,'BeiFeng : Did You Know About This Qiyana ONE SHOT Combos ?','R.raw.vd1','@drawable/beifeng1','0','516000','0')");
        //
        listView = (ListView) view.findViewById(R.id.listVidHome);
        listView.setAdapter(homeAdapter);
        GetDataVideo();
    }

    public void GetDataVideo(){
        arrayVideo.clear();
        Cursor dataVideo = database.GetData("SELECT * FROM Video");
        while (dataVideo.moveToNext()){
            int id = dataVideo.getInt(0);
            String ten = dataVideo.getString(1);
            String url = dataVideo.getString(2);
            String thumb = dataVideo.getString(3);
            int idthumb = getResources().getIdentifier(thumb, null, getActivity().getPackageName());
            int timeline = dataVideo.getInt(4);
            int tongtg = dataVideo.getInt(5);
            int history = dataVideo.getInt(6);
            arrayVideo.add(new Video(id, ten, url, idthumb, timeline, tongtg, history  ));
            homeAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(position==0) {
            Toast.makeText(getActivity(),"Katarina",Toast.LENGTH_SHORT).show();
        }
        if(position==1) {
            Toast.makeText(getActivity(),"Qiyana",Toast.LENGTH_SHORT).show();
        }
    }
}


