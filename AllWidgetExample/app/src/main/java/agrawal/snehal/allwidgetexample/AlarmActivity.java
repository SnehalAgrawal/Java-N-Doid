package agrawal.snehal.allwidgetexample;

import android.support.v7.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AlarmActivity extends AppCompatActivity {
    Button bstart, bstop;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        bstart = (Button) findViewById(R.id.button1);
        bstop = (Button) findViewById(R.id.bstop);

        bstart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startAlert();
            }
        });
        bstop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                stopalarm();
            }
        });

    }

    public void startAlert() {
        EditText text = (EditText) findViewById(R.id.time);
        int i = Integer.parseInt(text.getText().toString());
        pendingIntent = PendingIntent.getBroadcast(this, 234324243, new Intent(this, MyBroadcastReceiver.class), PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (i * 1000), pendingIntent);
        Toast.makeText(this, "Alarm set in " + i + " seconds", Toast.LENGTH_LONG).show();
    }

    public void stopalarm() {
        alarmManager.cancel(pendingIntent);
        pendingIntent.cancel();
        Toast.makeText(this, "Alarm stop", Toast.LENGTH_LONG).show();
    }

}