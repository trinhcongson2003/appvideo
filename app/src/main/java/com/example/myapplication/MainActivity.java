package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drlayout;
    BottomNavigationView btngview;
    FragmentManager frgmanager;
    Toolbar toolbar;
    public static String videoPath = "android.resource://com.example.myapplication/";
    public static ArrayList<Video> listVideoPlay, listHomeVideo, listHistoryVideo;
    public static Database database;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = new Database(getApplicationContext(), "VideoPlayer.sql", null, 1);
        listVideoPlay=new ArrayList<Video>();
        listHomeVideo = new ArrayList<Video>();
        listHistoryVideo = new ArrayList<Video>();

        //Database
//        database = new Database(getActivity(), "VideoPlayer.sql", null, 1);
        //Tạo bảng video
//        database.QueryData("CREATE TABLE IF NOT EXISTS Video(IdVD INTEGER PRIMARY KEY AUTOINCREMENT, TenVD VARCHAR(500), VDURL VARCHAR(300), Thumbnail VARCHAR(300), Timeline INTEGER, TongTG INTEGER, IdHistory INTEGER)");
//        database.QueryData("DROP  TABLE IF EXISTS Video");
        database.QueryData("CREATE TABLE IF NOT EXISTS Video(IdVD INTEGER PRIMARY KEY AUTOINCREMENT, TenVD VARCHAR(500), VDURL VARCHAR(300), Thumbnail VARCHAR(300), Timeline INTEGER, TongTG INTEGER, IdHistory INTEGER)");
        //Chèn CSDL vào bảng Video
        database.QueryData("INSERT INTO Video VALUES(null,'Rank 1 Kata : STOMRING KR Challenger - Engsub','R.raw.vd1','@drawable/katadeath','0','612000','0')");
        database.QueryData("INSERT INTO Video VALUES(null,'BeiFeng Talon is Pretty Good - Engsub','R.raw.vd1','@drawable/beifeng2','0','642000','0')");
        database.QueryData("INSERT INTO Video VALUES(null,'YeDaoShen : INSANE 1HP SURVIVE','R.raw.vd1','@drawable/talonenduring','0','556000','0')");
        database.QueryData("INSERT INTO Video VALUES(null,'BeiFeng : Did You Know About This Qiyana ONE SHOT Combos ?','R.raw.vd1','@drawable/beifeng1','0','516000','0')");
        database.QueryData("INSERT INTO Video VALUES(null,'When Rank 1 Katarina meet Yasuo - Engsub','R.raw.vd1','@drawable/rank1kata','0','593000','0')");
        //

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drlayout = findViewById(R.id.layoutdr);
        btngview = findViewById(R.id.btnav);
        btngview.setBackground(null);
        //Sơn béo cậu bé thời gian
        btngview.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int ItemID = item.getItemId();
                if(ItemID == R.id.nuthome){
                    openFragment(new HomeFragment());
                    return true;
                }else if(ItemID == R.id.nutslideshow){
                    openFragment(new ShortsFragment());
                    return true;
                }else if(ItemID == R.id.nutsubrices){
                    openFragment(new SubscriptionFragment());
                    return true;
                }else if(ItemID == R.id.nuthuman){
                    openFragment(new YouFragment());
                    return true;
                }
                return false;
            }
        });
        frgmanager = getSupportFragmentManager();
        openFragment(new HomeFragment());

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
    @Override
    public void onBackPressed() {
        if(drlayout.isDrawerOpen(GravityCompat.START)){
            drlayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }
    private void openFragment(Fragment fragment){
        FragmentTransaction trs = frgmanager.beginTransaction();
        trs.replace(R.id.frm,fragment);
        trs.commit();
    }
}