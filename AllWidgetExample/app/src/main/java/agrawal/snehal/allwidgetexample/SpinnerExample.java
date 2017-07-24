package agrawal.snehal.allwidgetexample;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SpinnerExample extends AppCompatActivity {
    Spinner spfromstringarray, spfromjava, spcustomadapter;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner_example);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        spfromstringarray = (Spinner) findViewById(R.id.spfromstringarray);

        spfromjava = (Spinner) findViewById(R.id.spfromjava);
        final List<String> list = new ArrayList<>();
        for (int i = 0; i <= 100; i++)
            list.add("list " + i);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(SpinnerExample.this, android.R.layout.simple_spinner_dropdown_item, list);
        //dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spfromjava.setAdapter(dataAdapter);
        spfromjava.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(SpinnerExample.this, "" + spfromjava.getSelectedItem(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sand1 = String.valueOf(spfromstringarray.getSelectedItem());
                String sand2 = String.valueOf(spfromjava.getSelectedItem());
                Toast.makeText(SpinnerExample.this, "Selected items are : " + "\nList 1 : " + sand1 + "\nList 2 : " + sand2, Toast.LENGTH_SHORT).show();
            }

        });


        spcustomadapter = (Spinner) findViewById(R.id.spcustomadapter);
        CustomSpinnerAdapter mSpinnerAdapter = new CustomSpinnerAdapter(this, getResources().getStringArray(R.array.country_arrays), getResources().getStringArray(R.array.country_code));
        spcustomadapter.setAdapter(mSpinnerAdapter);
        spcustomadapter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(SpinnerExample.this, "" + spcustomadapter.getSelectedItem(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private class CustomSpinnerAdapter extends ArrayAdapter<String> {
        Context mContext;
        String[] mObjects;
        String[] mShortNameObjects;

        CustomSpinnerAdapter(Context context, String[] objects, String[] shortNameObjects) {
            super(context, R.layout.activity_spinner_item, objects);
            mContext = context;
            mObjects = objects;
            mShortNameObjects = shortNameObjects;
        }

        @Override
        public View getDropDownView(final int position, View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = (View) inflater.inflate(R.layout.activity_spinner_item_featured, parent, false);
            TextView tvoption = (TextView) row.findViewById(R.id.tvoption);
            tvoption.setText(mObjects[position]);
            return row;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            @SuppressLint("ViewHolder") View row = (View) inflater.inflate(R.layout.activity_spinner_item, parent, false);
            TextView tvoption = (TextView) row.findViewById(R.id.tvoption);
            tvoption.setText(mShortNameObjects[position]);
            return row;
        }
    }
}
