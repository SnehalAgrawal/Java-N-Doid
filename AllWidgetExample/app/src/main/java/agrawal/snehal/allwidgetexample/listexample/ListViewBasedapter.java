package agrawal.snehal.allwidgetexample.listexample;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import agrawal.snehal.allwidgetexample.R;

/**
 * Created by Admin on 7/18/2017.
 */

public class ListViewBasedapter extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ArrayList<Item> itemsArrayList = generateItemsList();
        CustomListAdapter adapter = new CustomListAdapter(this, itemsArrayList);
        ListView itemsListView = (ListView) findViewById(R.id.listview);
        itemsListView.setAdapter(adapter);

    }

    private ArrayList<Item> generateItemsList() {
        ArrayList<Item> x = new ArrayList<>();
        ArrayList<String> values = new ArrayList<>(Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "O", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"));
        for (int i = 0; i < values.size(); i++)
            x.add(new Item(values.get(i), "dis " + values.get(i)));
        return x;
    }

    private class Item {
        private String itemName;
        private String itemDescription;

        Item(String name, String description) {
            this.itemName = name;
            this.itemDescription = description;
        }

        String getItemName() {
            return this.itemName;
        }

        String getItemDescription() {
            return itemDescription;
        }
    }

    private class ViewHolder {
        TextView tvheading;
        TextView tvsubheading;

        ViewHolder(View view) {
            tvheading = (TextView) view.findViewById(R.id.tvheading);
            tvsubheading = (TextView) view.findViewById(R.id.tvsubheading);
        }
    }

    private class CustomListAdapter extends BaseAdapter {
        private Context context;
        private ArrayList<Item> items;

        CustomListAdapter(Context context, ArrayList<Item> items) {
            this.context = context;
            this.items = items;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.activity_list_single_row, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            Item currentItem = (Item) getItem(position);
            viewHolder.tvheading.setText(currentItem.getItemName());
            viewHolder.tvsubheading.setText(currentItem.getItemDescription());
            return convertView;
        }
    }
}