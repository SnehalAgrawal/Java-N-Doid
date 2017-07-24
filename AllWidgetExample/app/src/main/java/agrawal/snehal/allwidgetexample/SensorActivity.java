package agrawal.snehal.allwidgetexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import agrawal.snehal.allwidgetexample.sensordetail.AccelerometerSensor;
import agrawal.snehal.allwidgetexample.sensordetail.ProximitySensor;
import agrawal.snehal.allwidgetexample.sensordetail.SensorDetailAll;

public class SensorActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
    }

    public void sensorclicktogo(View view) {
        switch (view.getId()) {
            case R.id.bsensorall:
                startActivity(new Intent(SensorActivity.this, SensorDetailAll.class));
                break;
            case R.id.baccelerometersensor:
                startActivity(new Intent(SensorActivity.this, AccelerometerSensor.class));
                break;
            case R.id.bproximitysensor:
                startActivity(new Intent(SensorActivity.this, ProximitySensor.class));
                break;
        }
    }


}
