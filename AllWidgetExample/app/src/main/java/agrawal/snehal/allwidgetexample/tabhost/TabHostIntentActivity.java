package agrawal.snehal.allwidgetexample.tabhost;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TabHost;

import agrawal.snehal.allwidgetexample.GridViewActivity;
import agrawal.snehal.allwidgetexample.ImageButtonImageExample;
import agrawal.snehal.allwidgetexample.R;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.Toast;

public class TabHostIntentActivity extends TabActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabhost_with_intent);

        TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
        TabHost.TabSpec spec;
        Intent intent;

        spec = tabHost.newTabSpec("home");
        spec.setIndicator("HOME");
        intent = new Intent(this, GridViewActivity.class);
        spec.setContent(intent);
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("Contact");
        spec.setIndicator("CONTACT");
        intent = new Intent(this, ImageButtonImageExample.class);
        spec.setContent(intent);
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("About");
        spec.setIndicator("ABOUT");
        intent = new Intent(this, GridViewActivity.class);
        spec.setContent(intent);
        tabHost.addTab(spec);
        tabHost.setCurrentTab(1);
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                Toast.makeText(getApplicationContext(), tabId, Toast.LENGTH_SHORT).show();
            }
        });
    }
}