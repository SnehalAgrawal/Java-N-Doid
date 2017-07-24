package com.example.admin.volleyexample;

import com.example.admin.volleyexample.app.AppController;
import com.example.admin.volleyexample.utils.Const;

import java.io.UnsupportedEncodingException;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.Cache;
import com.android.volley.Cache.Entry;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class ImageRequestActivity extends AppCompatActivity {

    private static final String TAG = ImageRequestActivity.class.getSimpleName();
    private NetworkImageView imgNetWorkView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        Button btnImageReq = (Button) findViewById(R.id.btnImageReq);
        imgNetWorkView = (NetworkImageView) findViewById(R.id.imgNetwork);
        imageView = (ImageView) findViewById(R.id.imgView);
        btnImageReq.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                makeImageRequest();
            }
        });
    }

    private void makeImageRequest() {
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        imgNetWorkView.setImageUrl(Const.URL_IMAGE, imageLoader);
        imageLoader.get(Const.URL_IMAGE, ImageLoader.getImageListener(imageView, R.drawable.ico_loading, R.drawable.ico_error));
        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Entry entry = cache.get(Const.URL_IMAGE);
        if (entry != null) {
            try {
                String data = new String(entry.data, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
        }

    }
}
