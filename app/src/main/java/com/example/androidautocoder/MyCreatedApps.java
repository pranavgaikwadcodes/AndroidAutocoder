package com.example.androidautocoder;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.androidautocoder.Databases.SessionManager;
import com.google.android.material.navigation.NavigationView;

public class MyCreatedApps extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private View decorView;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    Button Logout;
    CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_created_apps);

        cardView = (CardView) findViewById(R.id.cardview);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AppEditPage.class);
                startActivity(intent);
            }
        });


        //        hide bars
        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility == 0) {
                    decorView.setSystemUiVisibility(hideSystemBars());
                }
            }
        });

        Logout = (Button) findViewById(R.id.nav_logoutbtn);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUserFromApp();
            }
        });


//navbar code

        /*------------------Hooks---------------------*/
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navView);
        toolbar = findViewById(R.id.toolbar);

        /*------------------toolbar---------------------*/
        setSupportActionBar(toolbar);


        /*------------------navigation drawer menu---------------------*/
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        /*------------------------custom toolbar open button-------------------------*/
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu_btn);


        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.my_apps);


//navbar code end

    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void menu_btn(View view) {
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.click_home:
                Intent home = new Intent(this, Home.class);
                startActivity(home);
                break;
            case R.id.my_apps:
                break;
            case R.id.click_orderApp:
                Intent intent = new Intent(this, Order_app.class);
                startActivity(intent);
                break;
            case R.id.click_subscribe:
                Intent intent1 = new Intent(this, subscribe.class);
                startActivity(intent1);
                break;
            case R.id.click_profile:
                Intent intent3 = new Intent(this, userProfile.class);
                startActivity(intent3);
                break;

            case R.id.share:
                ApplicationInfo api = getApplicationContext().getApplicationInfo();
                String apkpath = api.sourceDir;
                Intent intent2 = new Intent(Intent.ACTION_SEND);
                intent2.setType("text/plain");
                String shareBody = "http://androidcoder.epizy.com/app/AndroidCoder.apk";
                String sharesub = "http://androidcoder.epizy.com/app/AndroidCoder.apk";
                intent2.putExtra(Intent.EXTRA_SUBJECT, shareBody);
                intent2.putExtra(Intent.EXTRA_TEXT, sharesub);
                startActivity(Intent.createChooser(intent2, "ShareVia"));
                break;

            case R.id.help:
                Uri uri = Uri.parse("http://androidcoder.epizy.com/#contact"); // missing 'http://' will cause crashed
                Intent link = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(link);
                break;

            case R.id.click_logout:
                logoutUserFromApp();
                break;

        }


        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    public void logoutUserFromApp() {
        //Session
        SessionManager sessionManager = new SessionManager(this);
        sessionManager.logoutUserFromSession();
        Intent takeUserToLogin = new Intent(this, Login_page.class);
        startActivity(takeUserToLogin);
        finish();
    }



    //        hide bars
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            decorView.setSystemUiVisibility(hideSystemBars());
        }
    }

    private int hideSystemBars() {
        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    }
    //    end hide bar


}