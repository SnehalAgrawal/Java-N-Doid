package agrawal.snehal.allwidgetexample;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import agrawal.snehal.allwidgetexample.listexample.ListViewArrayAdapter;

/**
 * Created by Admin on 7/18/2017.
 */

public class GridViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gridview);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        GridView gridview = (GridView) findViewById(R.id.gridview);
        GridAdapter adapter = new GridAdapter(this);
        gridview.setAdapter(adapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(GridViewActivity.this, "position " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class GridAdapter extends BaseAdapter {
        private Context mContext;

        GridAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return mThumbIds.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView;
            if (convertView == null) {
                textView = new TextView(mContext);
                textView.setLayoutParams(new GridView.LayoutParams(85, 85));
                //textView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                textView.setPadding(8, 8, 8, 8);
            } else {
                textView = (TextView) convertView;
            }
            //textView.setImageResource(mThumbIds[position]);
            textView.setText(mThumbIds[position]);
            return textView;
        }

        String[] mThumbIds = {"sample_0", "sample_1", "sample_2", "sample_3", "sample_4", "sample_5", "sample_6", "sample_7",
                "sample_8", "sample_9", "sample_10", "sample_11", "sample_12", "sample_13", "sample_14", "sample_15",
                "sample_16", "sample_17", "sample_18", "sample_19", "sample_20", "sample_21"};
    }
}