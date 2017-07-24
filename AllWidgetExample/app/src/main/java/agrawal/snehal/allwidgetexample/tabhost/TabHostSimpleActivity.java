package agrawal.snehal.allwidgetexample.tabhost;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TabHost;

import agrawal.snehal.allwidgetexample.R;


public class TabHostSimpleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabhost_simple);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TabHost host = (TabHost) findViewById(R.id.tabHost);
        host.setup();
        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Tab 1 name");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Tab 1 name");
        host.addTab(spec);
        //Tab 2
        spec = host.newTabSpec("Tab 2 name");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Tab 2 name");
        host.addTab(spec);
        //Tab 3
        spec = host.newTabSpec("Tab 3 name");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Tab 3 name");
        host.addTab(spec);


    }
}