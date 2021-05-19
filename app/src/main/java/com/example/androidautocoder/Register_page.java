package com.example.androidautocoder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidautocoder.Databases.SessionManager;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Register_page extends AppCompatActivity {

    private View decorView;

    TextView l;
    Button back;

    //    database
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    //    variables
    TextInputLayout regEmail, regUsername, regPhone, regPassword, regConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

//        get all elements
        regEmail = findViewById(R.id.new_email);
        regUsername = findViewById(R.id.new_username);
        regPhone = findViewById(R.id.new_ph);
        regPassword = findViewById(R.id.new_password);
        regConfirmPassword = findViewById(R.id.new_cpassword);

        Button register = findViewById(R.id.register_user);

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


        l = (TextView) findViewById(R.id.loginbtn_redirect);
        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register_page.this, Login_page.class);
                startActivity(intent);
                finish();
            }
        });

        back = (Button) findViewById(R.id.reg_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register_page.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    //validation

    private Boolean valEmail() {
        String valEmail = regEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (valEmail.isEmpty()) {
            regEmail.setError("Field cannot be empty");
            regEmail.requestFocus();
            return false;
        } else if (!valEmail.matches(emailPattern)) {
            regEmail.setError("Invalid email address");
            regEmail.requestFocus();
            return false;
        } else {
            regEmail.setError(null);
            regEmail.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean valUsername() {
        String valUsername = regUsername.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{0,20}\\z";

        if (valUsername.isEmpty()) {
            regUsername.setError("Field cannot be empty");
            regUsername.requestFocus();
            return false;
        } else if (valUsername.length() > 15) {
            regUsername.setError("Username too long");
            regUsername.requestFocus();
            return false;
        } else if (!valUsername.matches(noWhiteSpace)) {
            regUsername.setError("White Spaces are not allowed");
            regUsername.requestFocus();
            return false;
        } else {
            regUsername.setError(null);
            regUsername.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean valPhone() {
        String valPhone = regPhone.getEditText().getText().toString();
        if (valPhone.isEmpty()) {
            regPhone.setError("Fields cannot be empty");
            regPhone.requestFocus();
            return false;
        } else if (!valPhone.matches("[0-9]{10}")) {
            regPhone.setError("Invalid phone number");
            regPhone.requestFocus();
            return false;
        } else if (!valPhone.matches("^[6-9]\\d{9}$")) {
            regPhone.setError("Invalid phone number");
            regPhone.requestFocus();
            return false;
        } else {
            regPhone.setError(null);
            regPhone.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean valPassword() {
        String valPassword = regPassword.getEditText().getText().toString();
        String passwordVal = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if (valPassword.isEmpty()) {
            regPassword.setError("Field cannot be empty");
            regPassword.requestFocus();
            return false;
        } else if (!valPassword.matches(passwordVal)) {
            regPassword.setError("Use Uppercase,Lowercase,SpecialCharacters");
            regPassword.requestFocus();
            return false;
        } else if (valPassword.length() <= 5) {
            regPassword.setError("Password too short");
            regPassword.requestFocus();
            return false;
        } else if (valPassword.length() >= 15) {
            regPassword.setError("Password too long");
            regPassword.requestFocus();
            return false;
        } else {
            regPassword.setError(null);
            regPassword.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean valConfirmpassword() {
        String valConfirmPassword = regConfirmPassword.getEditText().getText().toString();
        String checkPasswordMatch = regPassword.getEditText().getText().toString();
        if (valConfirmPassword.isEmpty()) {
            regConfirmPassword.setError("Fields cannot be empty");
            regConfirmPassword.requestFocus();
            return false;
        } else if (!valConfirmPassword.equals(checkPasswordMatch)) {
            regConfirmPassword.setError("Password does not match");
            regConfirmPassword.requestFocus();
            return false;
        } else {
            regConfirmPassword.setError(null);
            return true;
        }

    }

    //already exist
    private Boolean existUsername() {
        String userEnteredUsername = regUsername.getEditText().getText().toString().trim();

        //Firebase reference
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUsername = reference.orderByChild("username").equalTo(userEnteredUsername);

        checkUsername.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    regUsername.setError("Username already taken");
                    return;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return true;
    }

    private Boolean existEmail() {
        String userEnteredEmail = regEmail.getEditText().getText().toString().trim();

        //Firebase reference
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkEmail = reference.orderByChild("email").equalTo(userEnteredEmail);

        checkEmail.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    regEmail.setError("Email already registered");
                    return;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return true;
    }

    private Boolean existPhoneNumber() {
        String userEnteredPhoneNumber = regPhone.getEditText().getText().toString().trim();

        //Firebase reference
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkPhoneNumber = reference.orderByChild("phone").equalTo(userEnteredPhoneNumber);
        checkPhoneNumber.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    regPhone.setError("Phone Number already registered");
                    return;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return true;
    }

    //register
    public void registerUser(View view) {

        if (!isConnected(this)) {
            showCustomDialog();
        } else {
            rootNode = FirebaseDatabase.getInstance();
            reference = rootNode.getReference("users");

            if (!valConfirmpassword() | !valPassword() | !valPhone() | !valUsername() | !valEmail()) {
                return;
            }

            String email = regEmail.getEditText().getText().toString();
            String username = regUsername.getEditText().getText().toString();
            String ph = regPhone.getEditText().getText().toString();
            String pass = regPassword.getEditText().getText().toString();
            String con_pass = regConfirmPassword.getEditText().getText().toString();


            UserHelperClass helperClass = new UserHelperClass(email, username, ph, pass, con_pass);
            reference.child(username).setValue(helperClass);
            Toast reg = Toast.makeText(getApplicationContext(), "Registered Successfully !", Toast.LENGTH_LONG);
            reg.show();


            //Session
            SessionManager sessionManager = new SessionManager(Register_page.this);
            sessionManager.createLoginSession(username,email,ph,pass);



            Intent registered = new Intent(Register_page.this, Splash_Screen.class);

            registered.putExtra("username", username);
            registered.putExtra("email", email);
            registered.putExtra("phone", ph);
            registered.putExtra("password", pass);


            startActivity(registered);
            finish();


        }
    }


    //check internet connection
    private boolean isConnected(Register_page register_page) {

        ConnectivityManager connectivityManager = (ConnectivityManager) register_page.getSystemService(Context.CONNECTIVITY_SERVICE);

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

    //internet dialog
    private void showCustomDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(Register_page.this);
        builder.setMessage("Please connect to the internet to proceed !")
                .setCancelable(true)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                }).show();

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