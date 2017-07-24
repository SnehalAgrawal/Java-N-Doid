package com.example.admin.restapi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ViewImage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        Picasso.with(this).load("http://192.168.10.101/task_manager/v1/imageupload/1")
                .placeholder(R.drawable.ic_image_black_24dp).error(R.drawable.ic_broken_image_black_24dp)
                .resize(400, 400).into(imageView);
    }
}
