package agrawal.snehal.allwidgetexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.StackView;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


public class StackViewActivity extends AppCompatActivity {

    private static final Integer[] icons = {R.drawable.jellybean, R.drawable.kitkat, R.drawable.lollipop, R.drawable.marshmellow, R.drawable.nougat};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stackview);
        StackView stackView = (StackView) findViewById(R.id.stack_view);

        ArrayList<StackItems> list = new ArrayList<>();
        for (Integer icon : icons) {
            list.add(new StackItems(icon));
        }
        StackAdapter adapter = new StackAdapter(StackViewActivity.this, list);
        stackView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private class StackItems {
        Integer image;

        StackItems(Integer image) {
            this.image = image;
        }

        int getImage() {
            return image;
        }
    }

    private class StackAdapter extends BaseAdapter {
        ArrayList<StackItems> arrayList;
        LayoutInflater inflater;
        ViewHolder holder = null;

        StackAdapter(Context context, ArrayList<StackItems> arrayList) {
            this.arrayList = arrayList;
            this.inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public StackItems getItem(int pos) {
            return arrayList.get(pos);
        }

        @Override
        public long getItemId(int pos) {
            return pos;
        }

        @Override
        public View getView(int pos, View view, ViewGroup parent) {
            if (view == null) {
                view = inflater.inflate(R.layout.activity_stackview_item, parent, false);
                holder = new ViewHolder();
                holder.image = (ImageView) view.findViewById(R.id.image);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            holder.image.setBackgroundResource(arrayList.get(pos).getImage());
            return view;
        }

        class ViewHolder {
            ImageView image;
        }
    }
}