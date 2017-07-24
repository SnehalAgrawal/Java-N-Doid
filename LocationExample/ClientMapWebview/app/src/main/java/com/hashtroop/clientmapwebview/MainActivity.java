package com.hashtroop.clientmapwebview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button blocalindex, bserverindex;
    static String GET_LOCATION_URL = "http://www.hashtroop.com/android_app/locationaccess/get_location.php";
    static String SERVER_SIDE_INDEX = "http://www.hashtroop.com/android_app/locationaccess/index.php";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        blocalindex = (Button) findViewById(R.id.blocalindex);
        bserverindex = (Button) findViewById(R.id.bserverindex);
        blocalindex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MapUsingLocalIndex.class));
            }
        });
        bserverindex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MapUsingServerIndex.class));
            }
        });
    }
}

