package agrawal.snehal.allwidgetexample.listexample;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.TreeSet;

import agrawal.snehal.allwidgetexample.R;

class CustomAdapter extends BaseAdapter {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SEPARATOR = 1;
    private ArrayList<String> mData = new ArrayList<>();
    private TreeSet<Integer> sectionHeader = new TreeSet<>();
    private LayoutInflater mInflater;

    CustomAdapter(Context context) {
        this.mInflater = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
    }

    void addItem(String item) {
        mData.add(item);
        notifyDataSetChanged();
    }

    void addSectionHeaderItem(String item) {
        mData.add(item);
        sectionHeader.add(mData.size() - 1);
        notifyDataSetChanged();
    }

    public int getItemViewType(int position) {
        return sectionHeader.contains(position) ? TYPE_SEPARATOR : TYPE_ITEM;
    }

    public int getViewTypeCount() {
        return 2;
    }

    public int getCount() {
        return mData.size();
    }

    public String getItem(int position) {
        return mData.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"Assert", "InflateParams"})
    public View getView(int position, View convertView, ViewGroup parent) {
        int rowType = getItemViewType(position);
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            switch (rowType) {
                case 0:
                    convertView = mInflater.inflate(R.layout.snippet_item1, null);
                    holder.textView = ((TextView) convertView.findViewById(R.id.text));
                    break;
                case 1:
                    convertView = mInflater.inflate(R.layout.snippet_item2, null);
                    holder.textView = ((TextView) convertView.findViewById(R.id.textSeparator));
            }
            assert (convertView != null);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(mData.get(position));
        return convertView;
    }

    private static class ViewHolder {
        TextView textView;
    }
}
