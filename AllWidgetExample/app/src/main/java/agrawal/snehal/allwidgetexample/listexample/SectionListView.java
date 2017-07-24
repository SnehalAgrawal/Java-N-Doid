package agrawal.snehal.allwidgetexample.listexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.Arrays;

import agrawal.snehal.allwidgetexample.R;

public class SectionListView extends AppCompatActivity {
    ListView list_section;
    String stack = "";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        list_section = ((ListView) findViewById(R.id.listview));
        String[] fruits = getResources().getStringArray(R.array.fruits_array);
        Arrays.asList(fruits);
        CustomAdapter mAdapter = new CustomAdapter(this);
        for (int i = 1; i < fruits.length; i++) {
            String index = fruits[i].substring(0, 1);
            if (!stack.equalsIgnoreCase(index)) {
                mAdapter.addSectionHeaderItem(index.toUpperCase());
                stack = index;
            }
            mAdapter.addItem(fruits[i]);
        }
        list_section.setAdapter(mAdapter);
    }
}
