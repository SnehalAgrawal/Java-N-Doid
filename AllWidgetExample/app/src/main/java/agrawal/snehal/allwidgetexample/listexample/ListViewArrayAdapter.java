package agrawal.snehal.allwidgetexample.listexample;

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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import agrawal.snehal.allwidgetexample.R;

public class ListViewArrayAdapter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final ListView listview = (ListView) findViewById(R.id.listview);
        String[] values = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "O", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

        final ArrayList<String> list = new ArrayList<>();
        Collections.addAll(list, values);

        ArrayList<Map<String, String>> list1 = buildData();
        String[] from = {"name", "purpose"};
        int[] to = {R.id.tvheading, R.id.tvsubheading};

        //final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values);
        //final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.activity_list_single_row, R.id.tvheading, values);
        //final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, list1, R.layout.activity_list_single_row, from, to);
        //final MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(this, list);
        //final MyPerformanceArrayAdapter adapter = new MyPerformanceArrayAdapter(this, values);    //better performance
        final StableArrayAdapter adapter = new StableArrayAdapter(this, android.R.layout.simple_list_item_1, list);

        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                ViewCompat.animate(view).setDuration(2000).alpha(0).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        list.remove(item);
                        adapter.notifyDataSetChanged();
                        view.setAlpha(1);
                    }
                }).start();
            }

        });
    }

    private ArrayList<Map<String, String>> buildData() {
        ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
        list.add(putData("Android", "Mobile"));
        list.add(putData("Windows7", "Windows7"));
        list.add(putData("iPhone", "iPhone"));
        return list;
    }

    private HashMap<String, String> putData(String name, String purpose) {
        HashMap<String, String> item = new HashMap<String, String>();
        item.put("name", name);
        item.put("purpose", purpose);
        return item;
    }

    private class StableArrayAdapter extends ArrayAdapter<String> {
        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        StableArrayAdapter(Context context, int textViewResourceId, List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }

    private class MySimpleArrayAdapter extends ArrayAdapter<String> {
        private final Context context;
        private final ArrayList<String> values;

        MySimpleArrayAdapter(Context context, ArrayList<String> values) {
            super(context, -1, values);
            this.context = context;
            this.values = values;
        }

        @SuppressLint("SetTextI18n")
        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.activity_list_single_row, parent, false);
            TextView tvheading = (TextView) rowView.findViewById(R.id.tvheading);
            TextView tvsubheading = (TextView) rowView.findViewById(R.id.tvsubheading);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
            tvheading.setText(values.get(position));
            tvsubheading.setText("sub " + values.get(position));
            return rowView;
        }
    }

    public class MyPerformanceArrayAdapter extends ArrayAdapter<String> {
        private final Activity context;
        private final String[] names;

        class ViewHolder {
            TextView heading;
            TextView subheading;
        }

        public MyPerformanceArrayAdapter(Activity context, String[] names) {
            super(context, R.layout.activity_list_single_row, names);
            this.context = context;
            this.names = names;
        }

        @SuppressLint("SetTextI18n")
        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            View rowView = convertView;
            if (rowView == null) {
                LayoutInflater inflater = context.getLayoutInflater();
                rowView = inflater.inflate(R.layout.activity_list_single_row, null);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.heading = (TextView) rowView.findViewById(R.id.tvheading);
                viewHolder.subheading = (TextView) rowView.findViewById(R.id.tvsubheading);
                rowView.setTag(viewHolder);
            }
            ViewHolder holder = (ViewHolder) rowView.getTag();
            String s = names[position];
            holder.heading.setText(s);
            holder.subheading.setText("Sub " + s);
            return rowView;
        }
    }
}