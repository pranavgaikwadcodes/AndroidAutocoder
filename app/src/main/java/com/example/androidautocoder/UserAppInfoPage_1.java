package com.example.androidautocoder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserAppInfoPage_1 extends AppCompatActivity {
    private View decorView;
    Button button;
    DrawerLayout drawerLayout;

    //    database
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    //variables
    TextInputLayout appName, companyName, appDesc, companyDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_app_info_page_1);

        //get all elements
        appName = (TextInputLayout) findViewById(R.id.userAppName);
        companyName = (TextInputLayout) findViewById(R.id.userCompanyName);
        appDesc = (TextInputLayout) findViewById(R.id.userAppDesc);
        companyDesc = (TextInputLayout) findViewById(R.id.userCompanydesc);


        button = (Button) findViewById(R.id.next);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (appName.getEditText().getText().toString().equals("") && companyName.getEditText().getText().toString().equals("") && appDesc.getEditText().getText().toString().equals("") && companyDesc.getEditText().getText().toString().equals("")){


                    Toast.makeText(UserAppInfoPage_1.this, "Enter All Fields To Continue !", Toast.LENGTH_SHORT).show();

                }else {

                    Intent next = new Intent(getApplicationContext(), UserAppFeatures_Page.class);

                    next.putExtra("appName",appName.getEditText().getText().toString());
                    next.putExtra("companyName",companyName.getEditText().getText().toString());
                    next.putExtra("appDesc",appDesc.getEditText().getText().toString());
                    next.putExtra("companyDesc",companyDesc.getEditText().getText().toString());

                    startActivity(next);

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

        drawerLayout = findViewById(R.id.userappinfolayout);
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


}