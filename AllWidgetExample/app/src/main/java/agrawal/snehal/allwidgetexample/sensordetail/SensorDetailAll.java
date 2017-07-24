package agrawal.snehal.allwidgetexample.sensordetail;


import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import agrawal.snehal.allwidgetexample.R;

public class SensorDetailAll extends AppCompatActivity {
    TextView tvsensorname = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_detail_all);
        tvsensorname = (TextView) findViewById(R.id.tvsensorname);
        tvsensorname.setVisibility(View.GONE);
        SensorManager mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> mList = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        for (int i = 1; i < mList.size(); i++) {
            tvsensorname.setVisibility(View.VISIBLE);
            tvsensorname.append("\n\n" + mList.get(i).getName() + "\n" + mList.get(i).getVendor() + "\n" + mList.get(i).getVersion());
        }
    }
}