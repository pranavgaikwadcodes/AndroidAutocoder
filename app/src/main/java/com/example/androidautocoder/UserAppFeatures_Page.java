package com.example.androidautocoder;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class UserAppFeatures_Page extends AppCompatActivity {
    Button button;
    GridLayout mainGrid;
    private View decorView;
    public String adminPanelstatus = "no";
    public String aboutUsstatus = "no";
    public String notificationstatus = "no";
    public String sharestatus = "no";
    public String reviewstatus = "no";
    public String uploadVideostatus = "no";
    public String websitestatus = "no";
    public String uploadPhotostatus = "no";
    public String videoConferancestatus = "no";
    public String login_registerPg_status = "no";
    public String appUpdatesstatus = "no";
    public String formBuilderstatus = "no";
    public String eCommercestatus = "no";
    public String donatestatus = "no";
    public String mapstatus = "no";
    public String blogstatus = "no";
    public String fbstatus = "no";
    public String linkedinstatus = "no";
    public String emailstatus = "no";
    public String twitterstatus = "no";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_app_features_page);

        mainGrid = (GridLayout) findViewById(R.id.mainGrid);

        //set event
        setToggleEvent(mainGrid);

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
        button = (Button) findViewById(R.id.btnNew);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goAhed = new Intent(UserAppFeatures_Page.this,fillAppFeaturesInfo.class);
                isSetColorThis();
                goAhed.putExtra("adminPanel",adminPanelstatus);
                goAhed.putExtra("aboutUs",aboutUsstatus);
                goAhed.putExtra("notification",notificationstatus);
                goAhed.putExtra("share",sharestatus);
                goAhed.putExtra("review",reviewstatus);
                goAhed.putExtra("uploadVideo",uploadVideostatus);
                goAhed.putExtra("website",websitestatus);
                goAhed.putExtra("uploadPhoto",uploadPhotostatus);
                goAhed.putExtra("videoConferance",videoConferancestatus);
                goAhed.putExtra("login_registerPg",login_registerPg_status);
                goAhed.putExtra("appUpdates",appUpdatesstatus);
                goAhed.putExtra("formBuilder",formBuilderstatus);
                goAhed.putExtra("eCommerce",eCommercestatus);
                goAhed.putExtra("donate",donatestatus);
                goAhed.putExtra("map",mapstatus);
                goAhed.putExtra("blog",blogstatus);
                goAhed.putExtra("fb",fbstatus);
                goAhed.putExtra("linkedin",linkedinstatus);
                goAhed.putExtra("email",emailstatus);
                goAhed.putExtra("twitter",twitterstatus);




                CardView adminPanel = (CardView) findViewById(R.id.adminPanel);
                CardView aboutUs = (CardView) findViewById(R.id.aboutUs);
                CardView notification = (CardView) findViewById(R.id.notification);
                CardView share = (CardView) findViewById(R.id.share);
                CardView review = (CardView) findViewById(R.id.review);
                CardView uploadVideo = (CardView) findViewById(R.id.uploadVideo);
                CardView website = (CardView) findViewById(R.id.website);
                CardView uploadPhoto = (CardView) findViewById(R.id.uploadPhoto);
                CardView videoConferance = (CardView) findViewById(R.id.videoConferance);
                CardView login_registerPg = (CardView) findViewById(R.id.login_registerPg);
                CardView appUpdates = (CardView) findViewById(R.id.appUpdates);
                CardView formBuilder = (CardView) findViewById(R.id.formBuilder);
                CardView eCommerce = (CardView) findViewById(R.id.eCommerce);
                CardView donate = (CardView) findViewById(R.id.donate);
                CardView map = (CardView) findViewById(R.id.map);
                CardView blog = (CardView) findViewById(R.id.blog);
                CardView fb = (CardView) findViewById(R.id.fb);
                CardView linkedin = (CardView) findViewById(R.id.linkedin);
                CardView email = (CardView) findViewById(R.id.email);
                CardView twitter = (CardView) findViewById(R.id.twitter);
                if (adminPanel.getCardBackgroundColor().getDefaultColor() == -1 &&
                        aboutUs.getCardBackgroundColor().getDefaultColor() == -1 &&
                        notification.getCardBackgroundColor().getDefaultColor() == -1 &&
                        share.getCardBackgroundColor().getDefaultColor() == -1 &&
                        review.getCardBackgroundColor().getDefaultColor() == -1 &&
                        uploadVideo.getCardBackgroundColor().getDefaultColor() == -1 &&
                        website.getCardBackgroundColor().getDefaultColor() == -1 &&
                        uploadPhoto.getCardBackgroundColor().getDefaultColor() == -1 &&
                        videoConferance.getCardBackgroundColor().getDefaultColor() == -1 &&
                        login_registerPg.getCardBackgroundColor().getDefaultColor() == -1 &&
                        appUpdates.getCardBackgroundColor().getDefaultColor() == -1 &&
                        formBuilder.getCardBackgroundColor().getDefaultColor() == -1 &&
                        eCommerce.getCardBackgroundColor().getDefaultColor() == -1 &&
                        donate.getCardBackgroundColor().getDefaultColor() == -1 &&
                        map.getCardBackgroundColor().getDefaultColor() == -1 &&
                        blog.getCardBackgroundColor().getDefaultColor() == -1 &&
                        fb.getCardBackgroundColor().getDefaultColor() == -1 &&
                        linkedin.getCardBackgroundColor().getDefaultColor() == -1 &&
                        email.getCardBackgroundColor().getDefaultColor() == -1 &&
                        twitter.getCardBackgroundColor().getDefaultColor() == -1 )
                {

                    Toast.makeText(UserAppFeatures_Page.this, "Select any one to continue", Toast.LENGTH_SHORT).show();

                }else {

                startActivity(goAhed);
                finish();
                }
            }
        });

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


    private void setToggleEvent(GridLayout mainGrid) {
        // loop all child items of main grid
        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (cardView.getCardBackgroundColor().getDefaultColor() == -1) {
                        //change background color
                        cardView.setCardBackgroundColor(Color.parseColor("#FF6C44"));
                        Toast.makeText(UserAppFeatures_Page.this, "index : " + finalI, Toast.LENGTH_SHORT).show();
                    } else {
                        //change background color
                        cardView.setCardBackgroundColor(Color.parseColor("#ffffff"));
                    }
                }
            });
        }
    }

    public void isSetColorThis() {
        CardView adminPanel = (CardView) findViewById(R.id.adminPanel);
        CardView aboutUs = (CardView) findViewById(R.id.aboutUs);
        CardView notification = (CardView) findViewById(R.id.notification);
        CardView share = (CardView) findViewById(R.id.share);
        CardView review = (CardView) findViewById(R.id.review);
        CardView uploadVideo = (CardView) findViewById(R.id.uploadVideo);
        CardView website = (CardView) findViewById(R.id.website);
        CardView uploadPhoto = (CardView) findViewById(R.id.uploadPhoto);
        CardView videoConferance = (CardView) findViewById(R.id.videoConferance);
        CardView login_registerPg = (CardView) findViewById(R.id.login_registerPg);
        CardView appUpdates = (CardView) findViewById(R.id.appUpdates);
        CardView formBuilder = (CardView) findViewById(R.id.formBuilder);
        CardView eCommerce = (CardView) findViewById(R.id.eCommerce);
        CardView donate = (CardView) findViewById(R.id.donate);
        CardView map = (CardView) findViewById(R.id.map);
        CardView blog = (CardView) findViewById(R.id.blog);
        CardView fb = (CardView) findViewById(R.id.fb);
        CardView linkedin = (CardView) findViewById(R.id.linkedin);
        CardView email = (CardView) findViewById(R.id.email);
        CardView twitter = (CardView) findViewById(R.id.twitter);




        if (adminPanel.getCardBackgroundColor().getDefaultColor() != -1) {
            adminPanelstatus="yes";
        }
        if (aboutUs.getCardBackgroundColor().getDefaultColor() != -1) {
            aboutUsstatus="yes";
        }
        if (notification.getCardBackgroundColor().getDefaultColor() != -1) {
            notificationstatus="yes";
        }
        if (share.getCardBackgroundColor().getDefaultColor() != -1) {
            sharestatus="yes";
        }
        if (review.getCardBackgroundColor().getDefaultColor() != -1) {
            reviewstatus="yes";
        }
        if (uploadVideo.getCardBackgroundColor().getDefaultColor() != -1) {
            uploadVideostatus="yes";
        }
        if (website.getCardBackgroundColor().getDefaultColor() != -1) {
            websitestatus="yes";
        }
        if (uploadPhoto.getCardBackgroundColor().getDefaultColor() != -1) {
            uploadPhotostatus="yes";
        }
        if (videoConferance.getCardBackgroundColor().getDefaultColor() != -1) {
            videoConferancestatus="yes";
        }
        if (login_registerPg.getCardBackgroundColor().getDefaultColor() != -1) {
            login_registerPg_status="yes";
        }
        if (appUpdates.getCardBackgroundColor().getDefaultColor() != -1) {
            appUpdatesstatus="yes";
        }
        if (formBuilder.getCardBackgroundColor().getDefaultColor() != -1) {
            formBuilderstatus="yes";
        }
        if (eCommerce.getCardBackgroundColor().getDefaultColor() != -1) {
            eCommercestatus="yes";
        }
        if (donate.getCardBackgroundColor().getDefaultColor() != -1) {
            donatestatus="yes";
        }
        if (map.getCardBackgroundColor().getDefaultColor() != -1) {
            mapstatus="yes";
        }
        if (blog.getCardBackgroundColor().getDefaultColor() != -1) {
            blogstatus="yes";
        }
        if (fb.getCardBackgroundColor().getDefaultColor() != -1) {
            fbstatus="yes";
        }
        if (linkedin.getCardBackgroundColor().getDefaultColor() != -1) {
            linkedinstatus="yes";
        }
        if (email.getCardBackgroundColor().getDefaultColor() != -1) {
            emailstatus="yes";
        }
        if (twitter.getCardBackgroundColor().getDefaultColor() != -1) {
            twitterstatus="yes";
        }
    }

}
