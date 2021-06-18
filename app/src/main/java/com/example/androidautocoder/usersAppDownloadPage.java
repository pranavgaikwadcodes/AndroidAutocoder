package com.example.androidautocoder;

import android.app.DownloadManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class usersAppDownloadPage extends AppCompatActivity {

    private View decorView;

    private Button DownloadApp;
    private String DownloadUrl ="https://firebasestorage.googleapis.com/v0/b/androidcoder-486b3.appspot.com/o/demoApp%2FDemoApk-0.1.apk?alt=media&token=24968ace-9583-4bea-b21f-524ff5aa9f36";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_app_download_page);

        DownloadApp=findViewById(R.id.DownloadApp);
        DownloadApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Uri uri = Uri.parse(DownloadUrl);
//                startActivity(new Intent(Intent.ACTION_VIEW,uri));

                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(DownloadUrl));
                String title = URLUtil.guessFileName(DownloadUrl,null,null);
                request.setTitle(title);
                request.setDescription("Downloading...");
                String cookie= CookieManager.getInstance().getCookie(DownloadUrl);
                request.addRequestHeader("cookie",cookie);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,title);

                DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                downloadManager.enqueue(request);

                Toast.makeText(usersAppDownloadPage.this, "Downloading Demo.apk", Toast.LENGTH_SHORT).show();

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