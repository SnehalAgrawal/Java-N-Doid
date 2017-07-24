package agrawal.snehal.allwidgetexample.listexample;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import agrawal.snehal.allwidgetexample.R;


public class ListViewSimpleAdapter extends ListActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<Map<String, String>> list = buildData();
        String[] from = {"name", "purpose"};
        int[] to = {R.id.tvheading, R.id.tvsubheading};

        SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.activity_list_single_row, from, to);
        setListAdapter(adapter);
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
}