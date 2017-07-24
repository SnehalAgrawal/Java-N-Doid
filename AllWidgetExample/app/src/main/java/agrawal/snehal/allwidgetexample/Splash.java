package agrawal.snehal.allwidgetexample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Splash extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(Splash.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
        t.start();
    }

    protected void onPause() {
        super.onPause();
        finish();
    }
}
