package agrawal.snehal.badgemenu;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView itemMessagesBadgeTextView;
    RelativeLayout badgeLayout;
    int count = 0;
    TextView txtViewCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        final View notificaitons = menu.findItem(R.id.actionNotifications).getActionView();
        txtViewCount = (TextView) notificaitons.findViewById(R.id.txtCount);
        updateHotCount(++count);
        txtViewCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateHotCount(++count);
            }
        });
        notificaitons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateHotCount(++count);
            }
        });
        return true;
    }


    public void updateHotCount(final int new_hot_number) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                    txtViewCount.setVisibility(View.VISIBLE);
                    txtViewCount.setText(Integer.toString(new_hot_number));
            }
        });
    }

}
