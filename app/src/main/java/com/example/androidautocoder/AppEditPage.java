package com.example.androidautocoder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidautocoder.Databases.SessionManager;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class AppEditPage extends AppCompatActivity {
    private View decorView;

    Button addBlog2, save;
    TextInputLayout blog2, CompanyName, AppDesc, CompanyDesc, Website, AboutUs, Fb, LinkedIn, Twitter, Email, BlogURL, adminUser, adminPass;
    TextView appNameHere;

    //    database
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_edit_page);


        retriveEverythingFromDB();

        appNameHere = (TextView) findViewById(R.id.appNameHere);
        AppDesc = findViewById(R.id.userApp_Desc);
        CompanyName = findViewById(R.id.user_CompanyName);
        CompanyDesc = findViewById(R.id.user_Companydesc);
        AboutUs = findViewById(R.id.about_us);
        Website = findViewById(R.id._website);
        Fb = findViewById(R.id.fbURL);
        LinkedIn = findViewById(R.id.linkedinURL);
        Email = findViewById(R.id.emailid);
        BlogURL = findViewById(R.id.blogURL1);
        adminUser = findViewById(R.id.setUsername);
        adminPass = findViewById(R.id.setPassword);
        Twitter = findViewById(R.id.twitter);
        blog2 = findViewById(R.id.blogURL2);


        save = (Button) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent next = new Intent(getApplicationContext(),AppEditPage2.class);
                next.putExtra("app_NamE", appNameHere.getText().toString());
                next.putExtra("app_Desc", AppDesc.getEditText().getText().toString());
                next.putExtra("Company_Name", CompanyName.getEditText().getText().toString());
                next.putExtra("Company_Desc", CompanyDesc.getEditText().getText().toString());
                next.putExtra("About_Us", AboutUs.getEditText().getText().toString());
                next.putExtra("_Website", Website.getEditText().getText().toString());
                next.putExtra("FB", Fb.getEditText().getText().toString());
                next.putExtra("Linked_In", LinkedIn.getEditText().getText().toString());
                next.putExtra("E_mail", Email.getEditText().getText().toString());
                next.putExtra("Blog_Url1", BlogURL.getEditText().getText().toString());
                next.putExtra("Blog_Url2", blog2.getEditText().getText().toString());
                next.putExtra("admin_User", adminUser.getEditText().getText().toString());
                next.putExtra("admin_Pass", adminPass.getEditText().getText().toString());
                next.putExtra("_Twitter", Twitter.getEditText().getText().toString());
                startActivity(next);
            }
        });


        addBlog2 = (Button) findViewById(R.id.blogAddBtn);
        addBlog2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (blog2.getVisibility() == View.GONE) {
                    blog2.setVisibility(v.VISIBLE);
                    addBlog2.setText("Cancel -");
                } else {
                    blog2.setVisibility(v.GONE);
                    addBlog2.setText("Add +");
                }
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


        SessionManager sessionManager = new SessionManager(this);
        HashMap<String, String> userDetails = sessionManager.getUserDetailsFromSession();
        String _Username = userDetails.get(SessionManager.KEY_USERNAME);
    }

    private void retriveEverythingFromDB() {


        SessionManager sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> userDetails = sessionManager.getUserDetailsFromSession();

        String _Username = userDetails.get(SessionManager.KEY_USERNAME);

        //Firebase reference
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user_app_data").child(_Username).child("appFeaturesInfo");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                String appName = snapshot.child("appName").getValue().toString();
                String companyName = snapshot.child("companyName").getValue().toString();
                String appDesc = snapshot.child("appDesc").getValue().toString();
                String companyDesc = snapshot.child("appName").getValue().toString();
                String adminPanel = snapshot.child("adminPanel").getValue().toString();
                String aboutUs = snapshot.child("appName").getValue().toString();
                String notification = snapshot.child("notification").getValue().toString();
                String share = snapshot.child("share").getValue().toString();
                String review = snapshot.child("review").getValue().toString();
                String uploadVideo = snapshot.child("uploadVideo").getValue().toString();
                String website = snapshot.child("website").getValue().toString();
                String uploadPhoto = snapshot.child("uploadPhoto").getValue().toString();
                String videoConferance = snapshot.child("videoConferance").getValue().toString();
                String login_registerPg = snapshot.child("login_registerPg").getValue().toString();
                String appUpdates = snapshot.child("appUpdates").getValue().toString();
                String formBuilder = snapshot.child("formBuilder").getValue().toString();
                String eCommerce = snapshot.child("eCommerce").getValue().toString();
                String donate = snapshot.child("donate").getValue().toString();
                String map = snapshot.child("map").getValue().toString();
                String blog = snapshot.child("blog").getValue().toString();
                String fb = snapshot.child("fb").getValue().toString();
                String linkedin = snapshot.child("linkedin").getValue().toString();
                String email = snapshot.child("email").getValue().toString();
                String twitter = snapshot.child("twitter").getValue().toString();
                appNameHere.setText(appName);
                AppDesc.getEditText().setText(appDesc);
                CompanyName.getEditText().setText(companyName);
                CompanyDesc.getEditText().setText(companyDesc);

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

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