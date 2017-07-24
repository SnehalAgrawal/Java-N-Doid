package agrawal.snehal.allwidgetexample.listexample;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import agrawal.snehal.allwidgetexample.R;

public class AlphabeticIndexWithGroupHeader extends AppCompatActivity implements OnClickListener {
    Map<String, Integer> mapIndex;
    ListView fruitList;
    LinearLayout indexLayout;
    List<String> ll = new ArrayList<>();
    List<String> fruits = new ArrayList<>();
    TextView tvletter;
    RelativeLayout rlview;
    String stack = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_alphabetic_index);
        tvletter = ((TextView) findViewById(R.id.tvletter));
        rlview = ((RelativeLayout) findViewById(R.id.rlview));
        String[] fruits_array = getResources().getStringArray(R.array.fruits_array);
        Arrays.asList(fruits_array);
        fruitList = ((ListView) findViewById(R.id.list_fruits));

        CustomAdapter mAdapter = new CustomAdapter(this);
        for (int i = 1; i < fruits_array.length; i++) {
            String index = fruits_array[i].substring(0, 1);
            if (!stack.equalsIgnoreCase(index)) {
                fruits.add(index.toUpperCase());
                mAdapter.addSectionHeaderItem(index.toUpperCase());
                stack = index;
            }
            fruits.add(fruits_array[i]);
            mAdapter.addItem(fruits_array[i]);
        }
        fruitList.setAdapter(mAdapter);

        getIndexList(fruits);
        displayIndex();
        fruitList.setOnScrollListener(new OnScrollListener() {
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                String index = fruits.get(firstVisibleItem).substring(0, 1);
                View v = indexLayout.getChildAt(ll.indexOf(index));
                if (!stack.equalsIgnoreCase(index)) {
                    setcolor(v);
                    stack = index;
                }
            }
        });
    }

    public void onClick(View view) {
        TextView selectedIndex = (TextView) view;
        fruitList.setSelection(mapIndex.get(selectedIndex.getText().toString()));
    }

    private void getIndexList(List<String> fruits) {
        mapIndex = new LinkedHashMap<>();
        for (int i = 0; i < fruits.size(); i++) {
            String fruit = fruits.get(i);
            String index = fruit.substring(0, 1);
            if (!ll.contains(index)) {
                ll.add(index);
            }
            if (mapIndex.get(index) == null) {
                mapIndex.put(index, i);
            }
        }
    }

    @SuppressLint({"InflateParams"})
    private void displayIndex() {
        indexLayout = ((LinearLayout) findViewById(R.id.side_index));
        List<String> indexList = new ArrayList<>(mapIndex.keySet());
        for (String index : indexList) {
            TextView textView = (TextView) getLayoutInflater().inflate(R.layout.side_index_item, null);
            textView.setText(index);
            textView.setOnClickListener(this);
            if (indexLayout != null) {
                indexLayout.addView(textView);
            }
        }
    }


    void setcolor(View view) {
        for (int i = 0; i < indexLayout.getChildCount(); i++) {
            indexLayout.getChildAt(i).setBackgroundColor(ContextCompat.getColor(this, R.color.grey_dark));
        }
        view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
    }
}
