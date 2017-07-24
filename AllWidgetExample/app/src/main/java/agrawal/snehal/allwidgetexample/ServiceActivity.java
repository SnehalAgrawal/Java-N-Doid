package agrawal.snehal.allwidgetexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import agrawal.snehal.allwidgetexample.service.MyService;

public class ServiceActivity extends AppCompatActivity implements View.OnClickListener {
    Button buttonStart, buttonStop;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        buttonStart = (Button) findViewById(R.id.buttonStart);
        buttonStop = (Button) findViewById(R.id.buttonStop);
        buttonStart.setOnClickListener(this);
        buttonStop.setOnClickListener(this);
    }

    public void onClick(View src) {
        switch (src.getId()) {
            case R.id.buttonStart:
                startService(new Intent(this, MyService.class));
                break;
            case R.id.buttonStop:
                stopService(new Intent(this, MyService.class));
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopService(new Intent(this, MyService.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        stopService(new Intent(this, MyService.class));
    }
}
