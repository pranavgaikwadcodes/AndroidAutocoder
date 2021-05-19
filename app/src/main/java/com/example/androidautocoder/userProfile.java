package com.example.androidautocoder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.androidautocoder.Databases.SessionManager;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;

public class userProfile extends AppCompatActivity {

    private View decorView;
    NavigationView navigationView;
    DrawerLayout drawerLayout;

    TextInputLayout Username, Email, Contact, Password;
    TextView TextUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        //show data function
        showUserData();


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

    }

    //show user data
    public void showUserData() {


        //Hooks
        Username = findViewById(R.id.userUsername);
        Email = findViewById(R.id.userEmail);
        Contact = findViewById(R.id.userContact);
        Password = findViewById(R.id.userPassword);
        TextUsername = findViewById(R.id.textUsername);


        SessionManager sessionManager = new SessionManager(this);
        HashMap<String, String> userDetails = sessionManager.getUserDetailsFromSession();

        String _Email = userDetails.get(SessionManager.KEY_EMAIL);
        String _Phone = userDetails.get(SessionManager.KEY_PHONE);
        String _Username = userDetails.get(SessionManager.KEY_USERNAME);
        String _Password = userDetails.get(SessionManager.KEY_PASSWORD);

        Email.getEditText().setText(_Email);
        Username.getEditText().setText(_Username);
        Contact.getEditText().setText(_Phone);
        Password.getEditText().setText(_Password);
        TextUsername.setText(_Username);

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