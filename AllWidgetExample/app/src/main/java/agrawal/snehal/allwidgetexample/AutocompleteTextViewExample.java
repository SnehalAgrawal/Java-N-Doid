package agrawal.snehal.allwidgetexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AutocompleteTextViewExample extends AppCompatActivity {
    AutoCompleteTextView actvcomplete;
    MultiAutoCompleteTextView mactvcomplete;
    ArrayAdapter<String> dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autocomplete_example);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        actvcomplete = (AutoCompleteTextView) findViewById(R.id.actvcomplete);
        mactvcomplete = (MultiAutoCompleteTextView) findViewById(R.id.mactvcomplete);

        final List<String> list = new ArrayList<>();
        for (int i = 0; i <= 100; i++)
            list.add("Item " + i);

        dataAdapter = new ArrayAdapter<>(AutocompleteTextViewExample.this, android.R.layout.simple_dropdown_item_1line, list);
        actvcomplete.setAdapter(dataAdapter);
        dataAdapter = new ArrayAdapter<>(AutocompleteTextViewExample.this, android.R.layout.simple_dropdown_item_1line, list);
        mactvcomplete.setAdapter(dataAdapter);
        mactvcomplete.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
    }
}
