package com.livewall.lawwalletfinalyearproject.UserActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.livewall.lawwalletfinalyearproject.ModelClass.Constants;
import com.livewall.lawwalletfinalyearproject.R;
import com.livewall.lawwalletfinalyearproject.databinding.ActivityReadPdfBinding;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class ReadPdfActivity extends AppCompatActivity {
    private ActivityReadPdfBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityReadPdfBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//       loadBookDetails();
        backimage();
        loadPdfFromUrl();

    }
    private void backimage(){
        binding.backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReadPdfActivity.this,ViewUploadedPdfActivity.class));
            }
        });
    }
    private void loadPdfFromUrl() {
        ProgressDialog dialog=new ProgressDialog(ReadPdfActivity.this);
        dialog.setMessage("Please wait");
        WebView webView = findViewById(R.id.webView); // Replace with your WebView ID
       webView.setWebViewClient(new WebViewClient(){
           @Override
           public void onPageStarted(WebView view, String url, Bitmap favicon) {
               super.onPageStarted(view, url, favicon);
               dialog.show();
           }

           @Override
           public void onPageFinished(WebView view, String url) {
               super.onPageFinished(view, url);
               dialog.dismiss();
           }
       });
        String pdfurl=getIntent().getStringExtra("pdfurl");

       String url="";
       try {
         url= URLEncoder.encode(pdfurl,"UTF-8");

       }catch (Exception e)  {

       }

        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);

        webView.setInitialScale(1);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);

        webView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://docs.google.com/gview?embedded=true&url=" +url);
    }


    }

