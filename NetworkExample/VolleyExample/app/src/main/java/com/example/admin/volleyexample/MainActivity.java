package com.example.admin.volleyexample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnString = (Button) findViewById(R.id.btnStringRequest);
        Button btnJson = (Button) findViewById(R.id.btnJsonRequest);
        Button btnImage = (Button) findViewById(R.id.btnImageRequest);
        btnString.setOnClickListener(this);
        btnJson.setOnClickListener(this);
        btnImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStringRequest:
                startActivity(new Intent(MainActivity.this, StringRequestActivity.class));
                break;
            case R.id.btnJsonRequest:
                startActivity(new Intent(MainActivity.this, JsonRequestActivity.class));
                break;
            case R.id.btnImageRequest:
                startActivity(new Intent(MainActivity.this, ImageRequestActivity.class));
                break;
            default:
                break;
        }
    }

}
