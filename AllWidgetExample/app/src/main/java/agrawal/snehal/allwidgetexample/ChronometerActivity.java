package agrawal.snehal.allwidgetexample;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

public class ChronometerActivity extends AppCompatActivity implements View.OnClickListener {

    Chronometer simpleChronometer;
    Button start, stop, restart, setFormat, clearFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chronometer);
        simpleChronometer = (Chronometer) findViewById(R.id.simpleChronometer);
        start = (Button) findViewById(R.id.startButton);
        stop = (Button) findViewById(R.id.stopButton);
        restart = (Button) findViewById(R.id.restartButton);
        setFormat = (Button) findViewById(R.id.setFormat);
        clearFormat = (Button) findViewById(R.id.clearFormat);
        start.setOnClickListener(this);
        stop.setOnClickListener(this);
        stop.setOnClickListener(this);
        restart.setOnClickListener(this);
        setFormat.setOnClickListener(this);
        clearFormat.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.startButton:
                simpleChronometer.start();
                break;
            case R.id.stopButton:
                simpleChronometer.stop();
                break;
            case R.id.restartButton:
                simpleChronometer.setBase(SystemClock.elapsedRealtime());
                break;
            case R.id.setFormat:
                simpleChronometer.setFormat("Time (%s)");
                break;
            case R.id.clearFormat:
                simpleChronometer.setFormat(null);
                break;
        }
    }
}