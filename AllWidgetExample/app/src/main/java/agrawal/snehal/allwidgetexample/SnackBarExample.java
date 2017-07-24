package agrawal.snehal.allwidgetexample;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import agrawal.snehal.allwidgetexample.tablayout.TabLayoutCustomIconTextTabs;
import agrawal.snehal.allwidgetexample.tablayout.TabLayoutIconTabs;
import agrawal.snehal.allwidgetexample.tablayout.TabLayoutIconTextTabs;
import agrawal.snehal.allwidgetexample.tablayout.TabLayoutScrollableTab;
import agrawal.snehal.allwidgetexample.tablayout.TabLayoutSimpleTab;

public class SnackBarExample extends AppCompatActivity {
    private CoordinatorLayout coordinatorLayout;

    // we have to ass dependancy compile 'com.android.support:design:26.+'
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snackbar);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Button btnSimpleSnackbar = (Button) findViewById(R.id.btnSimpleSnackbar);
        Button btnActionCallback = (Button) findViewById(R.id.btnActionCallback);
        Button btnCustomView = (Button) findViewById(R.id.btnCustomSnackbar);
        Button btabs = (Button) findViewById(R.id.btabs);
        btnSimpleSnackbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(coordinatorLayout, "Hello", Snackbar.LENGTH_LONG).show();
            }
        });

        btnActionCallback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(coordinatorLayout, "Message is deleted", Snackbar.LENGTH_LONG).setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "Message is restored!", Snackbar.LENGTH_SHORT);
                        snackbar1.show();
                    }
                }).show();
            }
        });

        btnCustomView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar snackbar = Snackbar.make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_LONG).setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });
                snackbar.setActionTextColor(Color.RED);
                View sbView = snackbar.getView();
                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.YELLOW);
                snackbar.show();
            }
        });
        btabs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] tabhostoption = {"Simple Tabs", "Scrollable Tabs", "Icon &amp; Text Tabs", "Only Icon Tabs", "Custom View Icon &amp; Text"};
                AlertDialog.Builder tabhost_builder = new AlertDialog.Builder(SnackBarExample.this);
                tabhost_builder.setItems(tabhostoption, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0:
                                startActivity(new Intent(SnackBarExample.this, TabLayoutSimpleTab.class));
                                break;
                            case 1:
                                startActivity(new Intent(SnackBarExample.this, TabLayoutScrollableTab.class));
                                break;
                            case 2:
                                startActivity(new Intent(SnackBarExample.this, TabLayoutIconTextTabs.class));
                                break;
                            case 3:
                                startActivity(new Intent(SnackBarExample.this, TabLayoutIconTabs.class));
                                break;
                            case 4:
                                startActivity(new Intent(SnackBarExample.this, TabLayoutCustomIconTextTabs.class));
                                break;
                        }
                    }
                }).show();
            }
        });
    }
}