package agrawal.snehal.allwidgetexample;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.Cursor;
import android.os.Environment;
import android.os.Parcelable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

import agrawal.snehal.allwidgetexample.csvexport.CSVReader;
import agrawal.snehal.allwidgetexample.csvexport.CSVWriter;
import agrawal.snehal.allwidgetexample.datahandler.DataHandlerCSV;

public class ExportCSV extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exportcsv);
        ListView listView = (ListView) findViewById(R.id.listView);
        Button bexport = (Button) findViewById(R.id.bexport);
        ItemArrayAdapter itemArrayAdapter = new ItemArrayAdapter(getApplicationContext(), R.layout.activity_eportcsv_item);

        Parcelable state = listView.onSaveInstanceState();
        listView.setAdapter(itemArrayAdapter);
        listView.onRestoreInstanceState(state);

        InputStream inputStream = getResources().openRawResource(R.raw.stats);
        CSVReader csvFile = new CSVReader(inputStream);
        List<String[]> scoreList = csvFile.read();
        DataHandlerCSV handlerCSV = new DataHandlerCSV(ExportCSV.this);
        handlerCSV.open();
        handlerCSV.delete_all();
        for (String[] scoreData : scoreList) {
            itemArrayAdapter.add(scoreData);
            handlerCSV.insertData(scoreData[0], scoreData[1]);
        }
        handlerCSV.close();
        bexport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exportDB();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void exportDB() {

        File exportDir = new File(Environment.getExternalStorageDirectory(), getString(R.string.app_name));
        if (!exportDir.exists()) {
            if (!exportDir.mkdirs()) {
                Log.d("eportcsv", "exportDB: dir not created");
            }
        }
        @SuppressLint("SimpleDateFormat") String formattedDate = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(Calendar.getInstance().getTime());
        File file = new File(exportDir, formattedDate + ".csv");
        try {
            if (!file.createNewFile()) {
                Log.d("eportcsv", "exportDB: file not created");
            }
            CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
            csvWrite.writeNext(new String[]{"Id", "name", "score"});

            DataHandlerCSV handlerCSV = new DataHandlerCSV(ExportCSV.this);
            handlerCSV.open();
            Cursor cc = handlerCSV.returnAllData();
            if (cc.moveToFirst()) {
                do {
                    csvWrite.writeNext(new String[]{cc.getString(0), cc.getString(1), cc.getString(2)});
                } while (cc.moveToNext());
            }
            handlerCSV.close();
            csvWrite.close();
            Toast.makeText(this, "Done on : " + file, Toast.LENGTH_SHORT).show();
        } catch (Exception sqlEx) {
            Log.e("MainActivity", sqlEx.getMessage(), sqlEx);
        }
    }

    private class ItemArrayAdapter extends ArrayAdapter<String[]> {
        private List<String[]> scoreList = new ArrayList<>();

        private class ItemViewHolder {
            TextView name;
            TextView score;
        }

        ItemArrayAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        @Override
        public void add(String[] object) {
            scoreList.add(object);
            super.add(object);
        }

        @Override
        public int getCount() {
            return this.scoreList.size();
        }

        @Override
        public String[] getItem(int index) {
            return this.scoreList.get(index);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            View row = convertView;
            ItemViewHolder viewHolder;
            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.activity_eportcsv_item, parent, false);
                viewHolder = new ItemViewHolder();
                viewHolder.name = (TextView) row.findViewById(R.id.name);
                viewHolder.score = (TextView) row.findViewById(R.id.score);
                row.setTag(viewHolder);
            } else {
                viewHolder = (ItemViewHolder) row.getTag();
            }
            String[] stat = getItem(position);
            if (stat != null) {
                viewHolder.name.setText(stat[0]);
                viewHolder.score.setText(stat[1]);
            }
            return row;
        }
    }
}