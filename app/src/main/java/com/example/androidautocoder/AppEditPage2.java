package com.example.androidautocoder;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class AppEditPage2 extends AppCompatActivity {

    private View decorView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_edit_page2);


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
//        end hide bar



        Bundle extras = getIntent().getExtras();
//        get content from last ka last page
        String appName = extras.getString("app_NamE");
        String appDesc = extras.getString("app_Desc");
        String CompanyName = extras.getString("Company_Name");
        String CompanyDesc = extras.getString("Company_Desc");
        String AboutUs = extras.getString("About_Us");
        String Website = extras.getString("_Website");
        String Fb = extras.getString("FB");
        String LinkedIn = extras.getString("Linked_In");
        String Email = extras.getString("E_mail");
        String BlogUrl1 = extras.getString("Blog_Url1");
        String BlogUrl2 = extras.getString("Blog_Url2");
        String adminUser = extras.getString("admin_User");
        String adminPass = extras.getString("admin_Pass");
        String Twitter = extras.getString("_Twitter");

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