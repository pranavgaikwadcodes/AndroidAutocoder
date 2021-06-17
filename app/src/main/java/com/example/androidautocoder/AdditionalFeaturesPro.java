package com.example.androidautocoder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidautocoder.Databases.SessionManager;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AdditionalFeaturesPro extends AppCompatActivity {

    private View decorView;
    TextView textView;
    Button skip;

    //    database
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional_features_pro);



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
        String appName = extras.getString("appName");
        String companyName = extras.getString("companyName");
        String appDesc = extras.getString("appDesc");
        String companyDesc = extras.getString("companyDesc");

//        get content from last page
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


//
//        SessionManager sessionManager = new SessionManager(getApplicationContext());
//        HashMap<String, String> appInfo = sessionManager.getAppInfoFromSession();
//        String appName = appInfo.get(SessionManager.KEY_APPNAME);



        skip = (Button) findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent skip = new Intent(AdditionalFeaturesPro.this, FinalPageOfFeature.class);
                insertAllDataToDB();
                startActivity(skip);
            }

            private void insertAllDataToDB() {


                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("user_app_data");

                //get username
                SessionManager sessionManager = new SessionManager(getApplicationContext());
                HashMap<String, String> userDetails = sessionManager.getUserDetailsFromSession();
                String _Username = userDetails.get(SessionManager.KEY_USERNAME);


                UserAppHelperClass helperClass = new UserAppHelperClass(appName, companyName, appDesc, companyDesc, adminPanel, aboutUs, notification,
                        share, review, uploadVideo, website, uploadPhoto, videoConferance, login_registerPg, appUpdates, formBuilder, eCommerce, donate, map,
                        blog, fb, linkedin, email, twitter);
                reference.child(_Username).child("appFeaturesInfo").setValue(helperClass);

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
}