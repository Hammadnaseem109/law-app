package com.livewall.lawwalletfinalyearproject.LaywerActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.livewall.lawwalletfinalyearproject.R;
import com.livewall.lawwalletfinalyearproject.UserActivity.ReadPdfActivity;
import com.livewall.lawwalletfinalyearproject.UserActivity.ViewUploadedPdfActivity;
import com.livewall.lawwalletfinalyearproject.databinding.ActivityReadLawyerBinding;
import com.livewall.lawwalletfinalyearproject.databinding.ActivityReadPdfBinding;

import java.net.URLEncoder;

public class ReadLawyerActivity extends AppCompatActivity {
   private ActivityReadLawyerBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityReadLawyerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        backimage();
        loadPdfFromUrl();
    }
    private void backimage(){
        binding.backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReadLawyerActivity.this,ViewLawyerPdfActivty.class));
            }
        });
    }
    private void loadPdfFromUrl() {
        ProgressDialog dialog=new ProgressDialog(ReadLawyerActivity.this);
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
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);

        webView.setInitialScale(1);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);

        webView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        webView.loadUrl("https://docs.google.com/gview?embedded=true&url=" + url);
    }

}