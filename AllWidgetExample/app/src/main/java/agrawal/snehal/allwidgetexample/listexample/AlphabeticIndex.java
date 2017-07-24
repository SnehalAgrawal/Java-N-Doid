package agrawal.snehal.allwidgetexample.listexample;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
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


public class AlphabeticIndex extends AppCompatActivity implements OnClickListener {
    Map<String, Integer> mapIndex;
    ListView fruitList;
    LinearLayout indexLayout;
    List<String> ll = new ArrayList<>();
    TextView tvletter;
    RelativeLayout rlview;
    String stack = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_alphabetic_index);
        tvletter = ((TextView) findViewById(R.id.tvletter));
        rlview = ((RelativeLayout) findViewById(R.id.rlview));
        final String[] fruits = getResources().getStringArray(R.array.fruits_array);
        Arrays.asList(fruits);
        fruitList = ((ListView) findViewById(R.id.list_fruits));
        fruitList.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, fruits));
        getIndexList(fruits);
        displayIndex();
        fruitList.setOnScrollListener(new OnScrollListener() {
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                String index = fruits[firstVisibleItem].substring(0, 1);
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
        settext(selectedIndex.getText().toString());
    }

    private void getIndexList(String[] fruits) {
        mapIndex = new LinkedHashMap<>();
        for (int i = 0; i < fruits.length; i++) {
            String fruit = fruits[i];
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

    void settext(String s) {
        tvletter.setText(s);
        rlview.setVisibility(View.VISIBLE);
        Thread thread = new Thread() {
            public void run() {
                try {
                    sleep(500L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Handler handler = new Handler(getMainLooper());
                    handler.post(new Runnable() {
                        public void run() {
                            rlview.setVisibility(View.GONE);
                        }
                    });
                }
            }
        };
        thread.start();
    }

    void setcolor(View view) {
        for (int i = 0; i < indexLayout.getChildCount(); i++) {
            indexLayout.getChildAt(i).setBackgroundColor(ContextCompat.getColor(this, R.color.grey_dark));
        }
        view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
    }
}
