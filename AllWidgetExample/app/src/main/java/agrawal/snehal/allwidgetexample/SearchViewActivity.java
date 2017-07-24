package agrawal.snehal.allwidgetexample;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import android.widget.ListView;
import android.widget.SearchView;

import android.widget.BaseAdapter;

import java.util.Locale;

public class SearchViewActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    ListView list;
    ListViewAdapter adapter;
    SearchView editsearch;
    String[] animalNameList;
    ArrayList<AnimalNames> arraylist = new ArrayList<AnimalNames>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        animalNameList = new String[]{"Lion", "Tiger", "Dog", "Cat", "Tortoise", "Rat", "Elephant", "Fox", "Cow", "Donkey", "Monkey"};
        list = (ListView) findViewById(R.id.listview);
        for (String anAnimalNameList : animalNameList) {
            AnimalNames animalNames = new AnimalNames(anAnimalNameList);
            arraylist.add(animalNames);
        }
        adapter = new ListViewAdapter(this, arraylist);
        list.setAdapter(adapter);
        editsearch = (SearchView) findViewById(R.id.search);
        editsearch.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.filter(newText);
        return false;
    }

    private class AnimalNames {
        private String animalName;

        AnimalNames(String animalName) {
            this.animalName = animalName;
        }

        String getAnimalName() {
            return this.animalName;
        }
    }

    private class ListViewAdapter extends BaseAdapter {
        Context mContext;
        LayoutInflater inflater;
        private List<AnimalNames> animalNamesList = null;
        private ArrayList<AnimalNames> arraylist;

        ListViewAdapter(Context context, List<AnimalNames> animalNamesList) {
            mContext = context;
            this.animalNamesList = animalNamesList;
            inflater = LayoutInflater.from(mContext);
            this.arraylist = new ArrayList<AnimalNames>();
            this.arraylist.addAll(animalNamesList);
        }

        class ViewHolder {
            TextView name;
        }

        @Override
        public int getCount() {
            return animalNamesList.size();
        }

        @Override
        public AnimalNames getItem(int position) {
            return animalNamesList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View view, ViewGroup parent) {
            final ViewHolder holder;
            if (view == null) {
                holder = new ViewHolder();
                view = inflater.inflate(R.layout.searchview_item, null);
                holder.name = (TextView) view.findViewById(R.id.name);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            holder.name.setText(animalNamesList.get(position).getAnimalName());
            return view;
        }

        void filter(String charText) {
            charText = charText.toLowerCase(Locale.getDefault());
            animalNamesList.clear();
            if (charText.length() == 0) {
                animalNamesList.addAll(arraylist);
            } else {
                for (AnimalNames wp : arraylist) {
                    if (wp.getAnimalName().toLowerCase(Locale.getDefault()).contains(charText)) {
                        animalNamesList.add(wp);
                    }
                }
            }
            notifyDataSetChanged();
        }
    }
}