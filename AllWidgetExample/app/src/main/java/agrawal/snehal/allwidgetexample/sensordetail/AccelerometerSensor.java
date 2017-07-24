package agrawal.snehal.allwidgetexample.sensordetail;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import agrawal.snehal.allwidgetexample.R;

public class AccelerometerSensor extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private boolean color = false;
    private View view;
    private long lastUpdate;
    List list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_accelerometer);
        view = findViewById(R.id.textView);
        view.setBackgroundColor(Color.GREEN);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lastUpdate = System.currentTimeMillis();
        list = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if (list.size() > 0) {
            sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Toast.makeText(getBaseContext(), "Error: No Accelerometer.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float[] values = event.values;
            float x = values[0];
            float y = values[1];
            float z = values[2];
            ((TextView) view).setText("x: " + values[0] + "\ny: " + values[1] + "\nz: " + values[2]);
            float accelationSquareRoot = (x * x + y * y + z * z) / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
            long actualTime = event.timestamp;
            if (accelationSquareRoot >= 2) {
                if (actualTime - lastUpdate < 200) return;
                lastUpdate = actualTime;
                Toast.makeText(this, "Device was shuffed", Toast.LENGTH_SHORT).show();
                if (color) view.setBackgroundColor(Color.GREEN);
                else view.setBackgroundColor(Color.RED);
                color = !color;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (list.size() > 0) sensorManager.unregisterListener(this);
    }
}