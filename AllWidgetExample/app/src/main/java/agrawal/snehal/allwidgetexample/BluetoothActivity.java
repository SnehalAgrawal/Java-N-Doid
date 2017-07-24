package agrawal.snehal.allwidgetexample;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import java.util.Set;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class BluetoothActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_DISCOVERABLE_BT = 2;
    TextView textview1;
    private static final int REQUEST_ENABLE_BT = 1;
    BluetoothAdapter btAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        textview1 = (TextView) findViewById(R.id.textView1);
        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        textview1.append("\nAdapter: " + btAdapter);
        CheckBluetoothState();
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
    }

    @SuppressLint("ShowToast")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_BT) {
            if (btAdapter.isEnabled()) {
                textview1.append("\nBluetooth is enabled...");
                textview1.append("\nPaired Devices are:");
                Set<BluetoothDevice> devices = btAdapter.getBondedDevices();
                for (BluetoothDevice device : devices)
                    textview1.append("\n  Device: " + device.getName() + ", " + device);
            } else
                Toast.makeText(this, "Please on bluetooth", Toast.LENGTH_SHORT).show();
        } else if (requestCode == REQUEST_DISCOVERABLE_BT) {
            if (!btAdapter.isDiscovering())
                Toast.makeText(getApplicationContext(), "your device is discoverable", Toast.LENGTH_LONG);
            else
                Toast.makeText(getApplicationContext(), "your device is not discoverable", Toast.LENGTH_LONG);
        }
    }

    private void CheckBluetoothState() {
        if (btAdapter == null) {
            textview1.append("\nBluetooth NOT supported. Aborting.");
            return;
        } else {
            startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), REQUEST_ENABLE_BT);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                if (!btAdapter.isEnabled()) {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                }
                break;
            case R.id.button2:
                if (!btAdapter.isDiscovering()) {
                    Toast.makeText(getApplicationContext(), "MAKING YOUR DEVICE DISCOVERABLE", Toast.LENGTH_LONG);
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    startActivityForResult(enableBtIntent, REQUEST_DISCOVERABLE_BT);
                }
                break;
            case R.id.button3:
                btAdapter.disable();
                Toast.makeText(getApplicationContext(), "TURNING_OFF BLUETOOTH", Toast.LENGTH_LONG);
                break;
        }
    }
}