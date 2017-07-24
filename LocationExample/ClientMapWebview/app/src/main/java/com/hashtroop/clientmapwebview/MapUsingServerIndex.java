package com.hashtroop.clientmapwebview;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class MapUsingServerIndex extends Activity {
    WebView mapView;
    Button bclearcache;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_using_server_index);
        mapView = (WebView) findViewById(R.id.mapView);
        bclearcache = (Button) findViewById(R.id.bclearcache);
        bclearcache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapView.clearCache(true);
            }
        });
        mapView.getSettings().setJavaScriptEnabled(true);
        mapView.loadUrl(MainActivity.SERVER_SIDE_INDEX);
        mapView.setWebViewClient(new WebViewClient() {
            @SuppressWarnings("deprecation")
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url != null && url.startsWith("http://")) {
                    view.getContext().startActivity(
                            new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    return true;
                } else {
                    return false;
                }
            }

            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                final Uri uri = request.getUrl();
                if (uri != null && uri.toString().startsWith("http://")) {
                    view.getContext().startActivity(
                            new Intent(Intent.ACTION_VIEW, Uri.parse(String.valueOf(uri))));
                    return true;
                } else {
                    return false;
                }
            }
        });
    }
}

