package com.example.androidautocoder;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class fillAppFeaturesInfo extends AppCompatActivity {
TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_app_features_info);

        Bundle extras = getIntent().getExtras();
        String adminPanel = extras.getString("adminPanel");
        String aboutUs = extras.getString("aboutUs");
        String notification = extras.getString("notification");
        String share = extras.getString("share");
        String review = extras.getString("review");
        String uploadVideo = extras.getString("uploadVideo");
        String website = extras.getString("website");
        String uploadPhoto = extras.getString("uploadPhoto");
        String videoConferance = extras.getString("videoConferance");
        String login_registerPg = extras.getString("login_registerPg");
        String appUpdates = extras.getString("appUpdates");
        String formBuilder = extras.getString("formBuilder");
        String eCommerce = extras.getString("eCommerce");
        String donate = extras.getString("donate");
        String map = extras.getString("map");
        String blog = extras.getString("blog");
        String fb = extras.getString("fb");
        String linkedin = extras.getString("linkedin");
        String email = extras.getString("email");
        String twitter = extras.getString("twitter");


        textView = (TextView) findViewById(R.id.tv);
        textView.setText(adminPanel+twitter);
    }
}