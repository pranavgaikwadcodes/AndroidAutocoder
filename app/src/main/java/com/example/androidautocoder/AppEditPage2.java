package com.example.androidautocoder;

import android.app.ProgressDialog;
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

public class AppEditPage2 extends AppCompatActivity {

    private View decorView;

    TextView adminUserPass;
    TextView appname, appdesc, companyname, companydesc, website, aboutus, fburl, linkedin, twitter, email, blog1, blog2, donationlink, address, videourl, imageurl;


    Button generateApp;

    private ProgressDialog progressDialog;
    private int  progress = 0;

    //    database
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_edit_page2);

        generateApp = findViewById(R.id.generateAPP);

        appname = findViewById(R.id.appName);
        appdesc = findViewById(R.id.appDesc);
        companyname = findViewById(R.id.companyName);
        companydesc = findViewById(R.id.companyDesc);
        website = findViewById(R.id.website);
        aboutus = findViewById(R.id.aboutus);
        fburl = findViewById(R.id.fburl);
        linkedin = findViewById(R.id.linkedin);
        twitter = findViewById(R.id.twitter);
        email = findViewById(R.id.email);
        blog1 = findViewById(R.id.blog1);
        blog2 = findViewById(R.id.blog2);
        donationlink = findViewById(R.id.donationlink);
        address = findViewById(R.id.address);
        videourl = findViewById(R.id.videourl);
        imageurl = findViewById(R.id.imageurl);

        adminUserPass = findViewById(R.id.adminUserPass);


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
        String Twitter = extras.getString("_Twitter");
        String userAddress = extras.getString("userAddress");
        String userDonateLink = extras.getString("userDonateLink");
        String userVideo = extras.getString("userVideo");
        String ImageURL = extras.getString("showUrlForIMG");
        String adminUser = extras.getString("admin_User");
        String adminPass = extras.getString("admin_Pass");

        appname.setText("App Name : " + appName);
        appdesc.setText("App Description : " + appDesc);
        companyname.setText("Company Name : " + CompanyName);
        companydesc.setText("Company Description : " + CompanyDesc);
        website.setText("Website : " + Website);
        aboutus.setText("About US : " + AboutUs);
        fburl.setText("Facebook URL : " + Fb);
        linkedin.setText("LinkedIn URL : " + LinkedIn);
        email.setText("Email : " + Email);
        blog1.setText("Blog URL 1 : " + BlogUrl1);
        blog2.setText("Blog URL 2 : " + BlogUrl2);
        twitter.setText("Twitter URL : " + Twitter);
        address.setText("Address : " + userAddress);
        donationlink.setText("Donation Link : " + userDonateLink);
        videourl.setText("Video URL : " + userVideo);
        imageurl.setText("Image Status : Uploaded Successfully");
        adminUserPass.setText("Username :  " + adminUser + "  , Password : " + adminPass);

        generateApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog = new ProgressDialog(AppEditPage2.this);
                progressDialog.setCancelable(false);
                progressDialog.setMax(100);
                progressDialog.setTitle("Generating App");
                progressDialog.setMessage("please wait for some time and don't switch between apps ");
                progressDialog.setProgressStyle(progressDialog.STYLE_HORIZONTAL);
                progressDialog.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (progress<100){
                            try {
                                Thread.sleep(200);
                                progress++;
                                progressDialog.setProgress(progress);

                                if (progress==100){
                                    progressDialog.dismiss();
                                    Intent gotodownload = new Intent(getApplicationContext(),usersAppDownloadPage.class);
                                    startActivity(gotodownload);
                                    finish();
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();



                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("user_app_data");

                //get username
                SessionManager sessionManager = new SessionManager(getApplicationContext());
                HashMap<String, String> userDetails = sessionManager.getUserDetailsFromSession();
                String _Username = userDetails.get(SessionManager.KEY_USERNAME);
//
//                HashMap setValForApp = new HashMap();
//                setValForApp.put("appName",appName);
//                setValForApp.put("appDesc",appDesc);
//                setValForApp.put("CompanyName",CompanyName);
//                setValForApp.put("CompanyDesc",CompanyDesc);
//                setValForApp.put("adminUser",adminUser);
//                setValForApp.put("adminPass",adminPass);
//                setValForApp.put("AboutUs",AboutUs);
//                setValForApp.put("Website",Website);
//                setValForApp.put("BlogUrl1",BlogUrl1);
//                setValForApp.put("BlogUrl2",BlogUrl2);
//                setValForApp.put("Fb",Fb);
//                setValForApp.put("LinkedIn",LinkedIn);
//                setValForApp.put("Email",Email);
//                setValForApp.put("Twitter",Twitter);
//                setValForApp.put("userAddress",userAddress);
//                setValForApp.put("userDonateLink",userDonateLink);
//                setValForApp.put("userVideo",userVideo);
//                setValForApp.put("ImageURL",ImageURL);
//                reference.child(_Username).child("userAppData").updateChildren(setValForApp);

                UserAppFinalHelperClass helperClass = new UserAppFinalHelperClass(appName, appDesc, CompanyName, CompanyDesc, adminUser,
                        adminPass, AboutUs, Website, BlogUrl1, BlogUrl2, Fb, LinkedIn, Email, Twitter, userAddress, userDonateLink, userVideo, ImageURL);
                reference.child(_Username).child("userAppData").setValue(helperClass);


                reference = rootNode.getReference("users").child(_Username);
                HashMap hashMap = new HashMap();
                hashMap.put("adminUsername",adminUser);
                hashMap.put("adminPassword",adminPass);

                reference.updateChildren(hashMap);



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