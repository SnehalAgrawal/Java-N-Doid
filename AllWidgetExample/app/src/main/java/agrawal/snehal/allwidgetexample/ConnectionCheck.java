package agrawal.snehal.allwidgetexample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import agrawal.snehal.allwidgetexample.connectin_check.ConnectivityReceiver;
import agrawal.snehal.allwidgetexample.connectin_check.AllWidgetApplication;

public class ConnectionCheck extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connectioncheck);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button btnCheck = (Button) findViewById(R.id.btn_check);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        checkConnection();
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkConnection();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkConnection();
            }
        });
    }

    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }

    private void showSnack(boolean isConnected) {
        String message;
        int color;
        if (isConnected) {
            message = "Good! Connected to Internet";
            color = Color.WHITE;
        } else {
            message = "Sorry! Not connected to internet";
            color = Color.RED;
        }
        Snackbar snackbar = Snackbar.make(findViewById(R.id.fab), message, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        AllWidgetApplication.getInstance().setConnectivityListener(ConnectionCheck.this);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }
}

