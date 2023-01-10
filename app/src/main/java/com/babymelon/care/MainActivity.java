package com.babymelon.care;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private WebView web;
    String webUrl = "https://babymelon.in/";
    private ProgressBar progressBar;
//    private ProgressDialog pd;
    private SwipeRefreshLayout swipeRefreshLayout;
    RelativeLayout relativeLayout;
    Button NointernetBtn;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        web = (WebView) findViewById(R.id.myweb);
        web.loadUrl(webUrl);

        WebSettings mywebsettings = web.getSettings();
        mywebsettings.setJavaScriptEnabled(true);

        web.setWebViewClient(new WebViewClient(){
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                internetcheck();
                super.onReceivedError(view, request, error);
            }
        });

        web.getSettings().setAllowFileAccess(true);
        web.getSettings().setLoadsImagesAutomatically(true);
        web.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        web.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        web.getSettings().setAppCacheEnabled(true);
        web.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mywebsettings.setDomStorageEnabled(true);
        mywebsettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        mywebsettings.setUseWideViewPort(true);
        mywebsettings.setSavePassword(true);
        mywebsettings.setSaveFormData(true);
        mywebsettings.setEnableSmoothTransition(true);

//        pd = new ProgressDialog(MainActivity.this);
//        pd.setMessage("Loading Please Wait....");
//        web.setWebViewClient(new mbfllixer(pd));
//        pd.setCanceledOnTouchOutside(false);



        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        web.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {

                progressBar.setProgress(progress);
                if (progress < 100 && progressBar.getVisibility() == ProgressBar.GONE) {
                    progressBar.setVisibility(ProgressBar.VISIBLE);
                }

                if (progress == 100) {
                    progressBar.setVisibility(ProgressBar.GONE);
                }
            }
        });

        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                internetcheck();
                swipeRefreshLayout.setRefreshing(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        web.reload();
                    }
                },2000);
            }
        });

        swipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_blue_dark),
                getResources().getColor(android.R.color.holo_orange_dark),
                getResources().getColor(android.R.color.holo_green_dark),
                getResources().getColor(android.R.color.holo_red_dark)
        );


        NointernetBtn = (Button) findViewById(R.id.btnRetry);
        relativeLayout = (RelativeLayout) findViewById(R.id.nointernet);
        internetcheck();
        NointernetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                internetcheck();
            }
        });
    }

    private class WebViewClientDemo extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView wv, String url) {

            if (url.startsWith("tel:") || url.startsWith("whatsapp:")) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
                return true;
            }
            if (url.startsWith("https://t.me")) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
                return true;
            }
            if (url.startsWith("https://meet.google.com")) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
                return true;
            }
            if (url.startsWith("https://")) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
                return true;
            }

            if (url.startsWith("https://www.instagram.com/")) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
                return true;
            }

            if (url.startsWith("intent:") || url.startsWith("m.youtube.com")) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
                return true;
            }

            if (url.startsWith("https://play.google.com")) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
                return true;
            }

            progressBar.setVisibility(View.GONE);
            return false;
        }


        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            swipeRefreshLayout.setRefreshing(false);
        }
    }

//    public class mbfllixer extends WebViewClient {
//
//        ProgressDialog pd;
//        public mbfllixer(ProgressDialog pd){
//
//            this.pd=pd;
//            pd.show();
//        }
//
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//            view.loadUrl(webUrl);
//            return super.shouldOverrideUrlLoading(view, webUrl);
//        }
//
//        @Override
//        public void onPageFinished(WebView view, String url) {
//            super.onPageFinished(view, url);
//            if (pd.isShowing()){
//
//                pd.dismiss();
//            }
//        }
//
//    }
//public void onBackPressed() {
//    if (web.isFocused() && web.canGoBack()) {
//        web.goBack();
//    } else {
//    AlertDialog.Builder builder = new AlertDialog.Builder(this);
//    builder.setMessage("Are you sure you want to Exit?")
//            .setCancelable(false)
//            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    finishAffinity();
//                }
//            })
//            .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    dialogInterface.cancel();
//                }
//            });
//
//    AlertDialog alertDialog=builder.create();
//    alertDialog.show();
//        }
//    }

    private static long back_pressed;
    @Override
    public void onBackPressed(){
        if (web.isFocused() && web.canGoBack()) {
        web.goBack();
    } else if (back_pressed + 2000 > System.currentTimeMillis()){
//            super.onBackPressed();
            finishAffinity();
//            System.exit(0);
        }
        else{
            Toast.makeText(getBaseContext(), "Press once again to exit",
                    Toast.LENGTH_SHORT).show();
            back_pressed = System.currentTimeMillis();
        }
    }

    public void internetcheck(){
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobiledata = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if(mobiledata.isConnected()){
            web.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setVisibility(View.VISIBLE);
            relativeLayout.setVisibility(View.GONE);
            web.reload();
        }

        else if(wifi.isConnected()){
            web.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setVisibility(View.VISIBLE);
            relativeLayout.setVisibility(View.GONE);
            web.reload();
        }
        else{

            web.setVisibility(View.GONE);
            swipeRefreshLayout.setVisibility(View.GONE);
            relativeLayout.setVisibility(View.VISIBLE);
        }
    }
}