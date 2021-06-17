package com.example.androidautocoder;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class AppEditPage2 extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_edit_page2);

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
}