package agrawal.snehal.allwidgetexample.listexample;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import agrawal.snehal.allwidgetexample.R;


public class ListViewInfinite extends AppCompatActivity {
    CustomItemCardAdapter custom_item_card_adapter;
    boolean loading = false;
    boolean loadingMore = true;
    View footerView;
    ListView listView;
    int line_no = 0;
    ArrayList<ItemCard> itemcarditem = new ArrayList<>();
    ArrayList<String> data = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        for (int i = 0; i <= 1000; i++)
            data.add("data " + i);
        listView = (ListView) findViewById(R.id.listview);
        footerView = ((LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.loading_view, null, false);
        this.listView.addFooterView(footerView);

        custom_item_card_adapter = new CustomItemCardAdapter(this, itemcarditem);
        listView.setAdapter(custom_item_card_adapter);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int lastInScreen = firstVisibleItem + visibleItemCount;
                if ((lastInScreen == totalItemCount) && !(loading) && (loadingMore)) {
                    Thread thread = new Thread(null, loadMoreListItems);
                    loading = true;
                    thread.start();
                }
            }
        });
    }

    private Runnable loadMoreListItems = new Runnable() {
        @Override
        public void run() {
            itemcarditem = new ArrayList<>();
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                for (int i = 0; i <= 100; i++) {
                    if (line_no != data.size()) {
                        itemcarditem.add(new ItemCard(data.get(line_no), "Sub " + data.get(line_no)));
                        line_no++;
                    } else {
                        runOnUiThread(removefooter);
                        break;
                    }
                }
                runOnUiThread(returnRes);
            }
        }

        private Runnable returnRes = new Runnable() {
            @Override
            public void run() {
                if (itemcarditem != null && itemcarditem.size() > 0) {
                    for (int i = 0; i < itemcarditem.size(); i++)
                        custom_item_card_adapter.add(itemcarditem.get(i));
                }
                custom_item_card_adapter.notifyDataSetChanged();
                loading = false;
            }
        };
        private Runnable removefooter = new Runnable() {
            @Override
            public void run() {
                listView.removeFooterView(footerView);
            }
        };
    };

    class ItemCard {
        String heading;
        String subheading;

        ItemCard(String heading, String subheading) {
            this.heading = heading;
            this.subheading = subheading;
        }
    }

    private class CustomItemCardAdapter extends ArrayAdapter<ItemCard> {
        CustomItemCardAdapter(Context context, ArrayList<ItemCard> users) {
            super(context, 0, users);
        }

        @NonNull
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            ItemCard itemCard = (ItemCard) getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_list_single_row, parent, false);
            }
            TextView tvheading = (TextView) convertView.findViewById(R.id.tvheading);
            TextView tvsubheading = (TextView) convertView.findViewById(R.id.tvsubheading);
            if (itemCard != null) {
                tvheading.setText(itemCard.heading);
                tvsubheading.setText(itemCard.subheading);
            }

            return convertView;
        }
    }

}
