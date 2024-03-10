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
    FloatingActionButton fab;
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
        database.QueryData("INSERT INTO Video VALUES(null,'Season 2019: A New Journey | League of Legends','"+MainActivity.videoPath+R.raw.leagueoflegend+"','@drawable/leagueoflegend','0','60000','0')");
        database.QueryData("INSERT INTO Video VALUES(null,'Japanese Attack On Titan Live Action Subaru Advert / スバルフォレスターの広告「進撃の巨人」ENGLISH SUBBED','"+MainActivity.videoPath+R.raw.attackontitan+"','@drawable/attackontitan','0','30000','0')");
        database.QueryData("INSERT INTO Video VALUES(null,'Thăm ngàn - kệp ngần','"+MainActivity.videoPath+R.raw.thamngan+"','@drawable/thamngan','0','37000','0')");
        database.QueryData("INSERT INTO Video VALUES(null,'Version 2.0 Music Video — \"WHITE NIGHT\" | Honkai: Star Rail','"+MainActivity.videoPath+R.raw.honkaistarrail+"','@drawable/honkaistarrail','0','100000','0')");
        database.QueryData("INSERT INTO Video VALUES(null,'「LONG LONG MAN」 (Remake full ver.) ー Sakeru Gum さけるグミ CM Song','"+MainActivity.videoPath+R.raw.longlongman+"','@drawable/longlongman','0','61000','0')");
        database.QueryData("INSERT INTO Video VALUES(null,'One piece x Nissin Hungry days All Commercial 1 - 4','"+MainActivity.videoPath+R.raw.onepiece+"','@drawable/onepiece','0','119000','0')");
        database.QueryData("INSERT INTO Video VALUES(null,'Snickers Mr Bean TV advert - Subtitled','"+MainActivity.videoPath+R.raw.snicker+"','@drawable/snickers','0','70000','0')");
        database.QueryData("INSERT INTO Video VALUES(null,'TVアニメ『葬送のフリーレン』PV第2弾／毎週金曜よる11時放送','"+MainActivity.videoPath+R.raw.frieren+"','@drawable/frieren','0','100000','0')");
        database.QueryData("INSERT INTO Video VALUES(null,'TVアニメ『Re:ゼロから始める異世界生活』3rd season ティザーPV','"+MainActivity.videoPath+R.raw.rezero+"','@drawable/rezero','0','118000','0')");
        database.QueryData("INSERT INTO Video VALUES(null,'TVアニメ『呪術廻戦』第2期「懐玉・玉折」PV第2弾｜OPテーマ：「青のすみか」キタニタツヤ｜7月6日から毎週木曜夜11時56分～MBS/TBS系列全国28局にて放送開始!!','"+MainActivity.videoPath+R.raw.jujutsu+"','@drawable/jujutsu','0','91000','0')");
        database.QueryData("INSERT INTO Video VALUES(null,'Quảng cáo giày Adidas Climacool - David Beckham','"+MainActivity.videoPath+R.raw.adidas+"','@drawable/adidas','0','30000','0')");
        database.QueryData("INSERT INTO Video VALUES(null,'[Phim Kỹ Xảo] Khi Các Nhân Vật Free Fire Hành Động Ngầu Lòi | Garena Free Fire','"+MainActivity.videoPath+R.raw.freefire+"','@drawable/freefire','0','73000','0')");
        database.QueryData("INSERT INTO Video VALUES(null,'Quảng cáo Kẹo cao su Thái Lan','"+MainActivity.videoPath+R.raw.keocaosu+"','@drawable/keocaosu','0','67000','0')");
        //

        fab = findViewById(R.id.fab);
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

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"Thêm Video",Toast.LENGTH_SHORT).show();
            }
        });
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