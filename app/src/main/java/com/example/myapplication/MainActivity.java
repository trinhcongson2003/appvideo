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

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drlayout;
    BottomNavigationView btngview;
    FragmentManager frgmanager;
    Toolbar toolbar;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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