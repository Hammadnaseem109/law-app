package com.livewall.lawwalletfinalyearproject.UserActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.livewall.lawwalletfinalyearproject.R;

public class LawDiraryDetailsFragment extends Fragment {
    ProgressDialog progressDialog;
    private WebView webView;
    public LawDiraryDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_law_dirary_details, container, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = requireActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getContext(), R.color.borron));
        }

// Create and show the progress dialog
        progressDialog = ProgressDialog.show(getContext(), "", "Loading...");
        webview(view);
        return  view;
    }
    private void webview(View view){
        webView =view. findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // Enable JavaScript
        webView.setWebViewClient(new WebViewClient() {
            // Override the onPageFinished method to detect when the page finishes loading
            @Override
            public void onPageFinished(WebView view, String url) {
                // Dismiss the progress dialog
                progressDialog.dismiss();
            }
        });
        // Load a website
        webView.loadUrl("https://legalopt.law.blog/");

    }
    private OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
            Exit();
        }
    };

    private void Exit() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("LegalOpt");
        builder.setMessage("Are you sure you want to exit the LegalOpt?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getActivity().finishAffinity();
            }
        });
        builder.setNegativeButton("No", null);
        builder.show();
    }




    // Register the callback in the onCreate() method
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requireActivity().getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback);
    }

    // Unregister the callback in the onDestroy() method
    @Override
    public void onDestroy() {
        super.onDestroy();
        onBackPressedCallback.remove();
    }
}