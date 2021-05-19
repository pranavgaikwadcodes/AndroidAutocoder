package com.example.androidautocoder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.androidautocoder.Databases.SessionManager;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login_page extends AppCompatActivity {

    private View decorView;
    //
    TextView registerbtn;
    Button login, back;

    //    variables
    TextInputLayout LogUsername, LogPassword;

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "userLoginSession";
    private static final String _KEY_USERNAME = "username";
    private static final String _KEY_EMAIL = "email";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        String name = sharedPreferences.getString(_KEY_USERNAME,null);
        if (name != null){
            Intent intent11 = new Intent(Login_page.this,Home.class);
            startActivity(intent11);
        }


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

        back = (Button) findViewById(R.id.login_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login_page.this, Register_page.class);
                startActivity(intent);
                finish();
            }
        });

        registerbtn = (TextView) findViewById(R.id.registerbtn_loginpg);
        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login_page.this, Register_page.class);
                startActivity(intent);
                finish();
            }
        });


        LogUsername = findViewById(R.id.username);
        LogPassword = findViewById(R.id.password);

    }


    private Boolean valUsername() {
        String valUsername = LogUsername.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if (valUsername.isEmpty()) {
            LogUsername.setError("Field cannot be empty");
            return false;
        } else if (!valUsername.matches(noWhiteSpace)) {
            LogUsername.setError("White Spaces are not allowed");
            return false;
        } else {
            LogUsername.setError(null);
            LogUsername.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean valPassword() {
        String valPassword = LogPassword.getEditText().getText().toString();

        if (valPassword.isEmpty()) {
            LogPassword.setError("Field cannot be empty");
            return false;
        } else {
            LogPassword.setError(null);
            LogPassword.setErrorEnabled(false);
            return true;
        }
    }

    public void loginUser(View view) {
        if (!isConnected(this)) {
            showCustomDialog();
        } else {

            //Validate
            if (!valUsername() | !valPassword()) {
                return;
            } else {
                isUser();
                return;
            }
        }
    }

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Login_page.this);
        builder.setMessage("Please connect to the internet to proceed !")
                .setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                }).show();
    }

    private boolean isConnected(Login_page login_page) {
        ConnectivityManager connectivityManager = (ConnectivityManager) login_page.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netConnected = connectivityManager.getActiveNetworkInfo();

        if (null != netConnected) {

            if (netConnected.getType() == connectivityManager.TYPE_WIFI) {
                return true;
            } else if (netConnected.getType() == connectivityManager.TYPE_MOBILE) {
                return true;
            }
        }

        return false;
    }

    private void isUser() {
        String userEnteredUsername = LogUsername.getEditText().getText().toString().trim();
        String userEnteredPassword = LogPassword.getEditText().getText().toString().trim();

        //Firebase reference
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUsername = reference.orderByChild("username").equalTo(userEnteredUsername);

        checkUsername.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    LogUsername.setError(null);
                    LogUsername.setErrorEnabled(false);

                    String passwordFromDB = snapshot.child(userEnteredUsername).child("password").getValue(String.class);

                    if (passwordFromDB.equals(userEnteredPassword)) {

                        LogPassword.setError(null);
                        LogPassword.setErrorEnabled(false);

                        String usernameFromDB = snapshot.child(userEnteredUsername).child("username").getValue(String.class);
                        String phoneFromDB = snapshot.child(userEnteredUsername).child("phone").getValue(String.class);
                        String emailFromDB = snapshot.child(userEnteredUsername).child("email").getValue(String.class);


                        //Session
                        SessionManager sessionManager = new SessionManager(Login_page.this);
                        sessionManager.createLoginSession(usernameFromDB,emailFromDB,phoneFromDB,passwordFromDB);

                        Intent loginUserAfterCheck = new Intent(getApplicationContext(), Splash_Screen.class);

                        loginUserAfterCheck.putExtra("username", usernameFromDB);
                        loginUserAfterCheck.putExtra("email", emailFromDB);
                        loginUserAfterCheck.putExtra("phone", phoneFromDB);
                        loginUserAfterCheck.putExtra("password", passwordFromDB);
                        startActivity(loginUserAfterCheck);

                    } else {
                        LogPassword.setError("Incorrect Password");
                        LogPassword.requestFocus();
                    }
                } else {
                    LogUsername.setError("No such user exist");
                    LogUsername.requestFocus();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
}