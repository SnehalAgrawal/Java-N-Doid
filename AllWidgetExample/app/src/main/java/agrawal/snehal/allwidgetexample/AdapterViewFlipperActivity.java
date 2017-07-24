package agrawal.snehal.allwidgetexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.AdapterViewFlipper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterViewFlipperActivity extends AppCompatActivity {

    private AdapterViewFlipper simpleAdapterViewFlipper;
    int[] fruitImages = {R.drawable.jellybean, R.drawable.kitkat, R.drawable.lollipop, R.drawable.marshmellow, R.drawable.nougat};     // array of images
    String fruitNames[] = {"jellybean", "kitkat", "lollipop", "marshmellow", "nougat"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adapterviewflipper);
        simpleAdapterViewFlipper = (AdapterViewFlipper) findViewById(R.id.simpleAdapterViewFlipper); // get the reference of AdapterViewFlipper
        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), fruitNames, fruitImages);
        simpleAdapterViewFlipper.setAdapter(customAdapter); // set adapter for AdapterViewFlipper
        simpleAdapterViewFlipper.setFlipInterval(3000);
        simpleAdapterViewFlipper.setAutoStart(true);
    }

    public class CustomAdapter extends BaseAdapter {
        Context context;
        int[] fruitImages;
        String[] fruitNames;
        LayoutInflater inflter;

        public CustomAdapter(Context applicationContext, String[] fruitNames, int[] fruitImages) {
            this.context = applicationContext;
            this.fruitImages = fruitImages;
            this.fruitNames = fruitNames;
            inflter = (LayoutInflater.from(applicationContext));

        }

        @Override
        public int getCount() {
            return fruitNames.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            view = inflter.inflate(R.layout.activity_adapterviewflipper_item, null);
            TextView fruitName = (TextView) view.findViewById(R.id.fruitName);
            ImageView fruitImage = (ImageView) view.findViewById(R.id.fruitImage);
            fruitName.setText(fruitNames[position]);
            fruitImage.setImageResource(fruitImages[position]);
            return view;
        }
    }
}