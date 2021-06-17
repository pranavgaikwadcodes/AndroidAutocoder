package com.example.androidautocoder;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidautocoder.Databases.SessionManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class AppEditPage extends AppCompatActivity {
    private View decorView;

    Button addBlog2, save, uploadImageBtn, confirmUploadIMG;
    TextInputLayout blog2, CompanyName, AppDesc, CompanyDesc, Website, AboutUs, Fb, LinkedIn, Twitter, Email, BlogURL, adminUser, adminPass, userAddress, userVideo, userDonateLink;
    TextView appNameHere, adminpanelsettingstxt, showUrlForIMG,showImgUploadTxt;

    ImageView viewUploadedImg;
    private Uri imageUri;

    ProgressBar ImageUploadProgressBar;

    //    database
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_edit_page);

        addBlog2 = (Button) findViewById(R.id.blogAddBtn);
        adminpanelsettingstxt = (TextView) findViewById(R.id.aps);
        viewUploadedImg = findViewById(R.id.viewUploadedImg);
        confirmUploadIMG = findViewById(R.id.confirmUpload);
        ImageUploadProgressBar = findViewById(R.id.ImageUploadProgressBar);
        showUrlForIMG = findViewById(R.id.showUrlForIMG);
        showImgUploadTxt=findViewById(R.id.showImgUploadTxt);

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
        Twitter = findViewById(R.id.twitterURL);
        blog2 = findViewById(R.id.blogURL2);
        userAddress = findViewById(R.id.userAddress);
        userVideo = findViewById(R.id.videoFromUser);
        userDonateLink = findViewById(R.id.userDonateLink);
        uploadImageBtn = findViewById(R.id.uploadImg);


//        hide all edittext
        Website.setVisibility(View.GONE);
        AboutUs.setVisibility(View.GONE);
        Fb.setVisibility(View.GONE);
        LinkedIn.setVisibility(View.GONE);
        Twitter.setVisibility(View.GONE);
        Email.setVisibility(View.GONE);
        BlogURL.setVisibility(View.GONE);
        adminUser.setVisibility(View.GONE);
        adminPass.setVisibility(View.GONE);
        adminpanelsettingstxt.setVisibility(View.GONE);
        userAddress.setVisibility(View.GONE);
        userVideo.setVisibility(View.GONE);
        uploadImageBtn.setVisibility(View.GONE);
        userDonateLink.setVisibility(View.GONE);

        blog2.setVisibility(View.GONE);
        addBlog2.setVisibility(View.GONE);

        confirmUploadIMG.setVisibility(View.GONE);
        ImageUploadProgressBar.setVisibility(View.INVISIBLE);
        showUrlForIMG.setVisibility(View.GONE);
        showImgUploadTxt.setVisibility(View.GONE);

        save = (Button) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent next = new Intent(getApplicationContext(), AppEditPage2.class);
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
                next.putExtra("userAddress", userAddress.getEditText().getText().toString());
                next.putExtra("userVideo", userVideo.getEditText().getText().toString());
                next.putExtra("userDonateLink", userDonateLink.getEditText().getText().toString());
                next.putExtra("showUrlForIMG", showUrlForIMG.getText().toString());
                startActivity(next);
            }
        });


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

        uploadImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 2);
            }
        });

        confirmUploadIMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageUri != null) {
                    uploadeImgToFirebase(imageUri);
                } else {
                    Toast.makeText(AppEditPage.this, "Please select Image !", Toast.LENGTH_SHORT).show();
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


        retriveEverythingFromDB();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            viewUploadedImg.setImageURI(imageUri);
            confirmUploadIMG.setVisibility(View.VISIBLE);
        }
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
                String companyDesc = snapshot.child("companyDesc").getValue().toString();
                String adminPanel = snapshot.child("adminPanel").getValue().toString();
                String aboutUs = snapshot.child("aboutUs").getValue().toString();
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

                if (website.equals("yes")) {
                    Website.setVisibility(View.VISIBLE);
                }
                if (aboutUs.equals("yes")) {
                    AboutUs.setVisibility(View.VISIBLE);
                }
                if (adminPanel.equals("yes")) {
                    adminpanelsettingstxt.setVisibility(View.VISIBLE);
                    adminUser.setVisibility(View.VISIBLE);
                    adminPass.setVisibility(View.VISIBLE);
                }
                if (blog.equals("yes")) {
                    BlogURL.setVisibility(View.VISIBLE);
                    addBlog2.setVisibility(View.VISIBLE);

                }
                if (fb.equals("yes")) {
                    Fb.setVisibility(View.VISIBLE);
                }
                if (linkedin.equals("yes")) {
                    LinkedIn.setVisibility(View.VISIBLE);
                }
                if (email.equals("yes")) {
                    Email.setVisibility(View.VISIBLE);
                }
                if (twitter.equals("yes")) {
                    Twitter.setVisibility(View.VISIBLE);
                }
                if (map.equals("yes")) {
                    userAddress.setVisibility(View.VISIBLE);
                }
                if (uploadVideo.equals("yes")) {
                    userVideo.setVisibility(View.VISIBLE);
                }
                if (uploadPhoto.equals("yes")) {
                    uploadImageBtn.setVisibility(View.VISIBLE);
                }
                if (donate.equals("yes")) {
                    userDonateLink.setVisibility(View.VISIBLE);
                }

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


    private void uploadeImgToFirebase(Uri uri) {

        StorageReference fileRef = storageReference.child(System.currentTimeMillis() + "." + getFileExtention(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        showUrlForIMG.setText(uri.toString());
                        showImgUploadTxt.setText("IMAGE UPLOADED SUCCESSFULLY !");
                        showImgUploadTxt.setVisibility(View.VISIBLE);
                        ImageUploadProgressBar.setVisibility(View.INVISIBLE);
                    }
                });

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull @NotNull UploadTask.TaskSnapshot snapshot) {
                ImageUploadProgressBar.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                ImageUploadProgressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(AppEditPage.this, "Uploading Failed due to some reason!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private String getFileExtention(Uri mUri) {

        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));

    }


}