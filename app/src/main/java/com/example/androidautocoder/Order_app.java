package com.example.androidautocoder;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.androidautocoder.Databases.SessionManager;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class Order_app extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private View decorView;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    private Button Logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_app);

        Logout = (Button) findViewById(R.id.nav_logoutbtn);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUserFromApp();
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
        navigationView.setCheckedItem(R.id.click_orderApp);

//navbar code end
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

    public void menu_btn(View view) {
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    private void checkIfApkCreated() {


        SessionManager sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> userDetails = sessionManager.getUserDetailsFromSession();

        String _Username = userDetails.get(SessionManager.KEY_USERNAME);

        //Firebase reference
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user_app_data").child(_Username).child("appFeaturesInfo");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (!snapshot.exists()){
                    Toast.makeText(getApplicationContext(), "You haven't created any app yet !", Toast.LENGTH_SHORT).show();
                }else {

                    Intent myapp = new Intent(getApplicationContext(), MyCreatedApps.class);
                    startActivity(myapp);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.click_home:
                Intent intent = new Intent(this, Home.class);
                startActivity(intent);
                break;
            case R.id.my_apps:
                checkIfApkCreated();
                break;
            case R.id.click_orderApp:
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

}