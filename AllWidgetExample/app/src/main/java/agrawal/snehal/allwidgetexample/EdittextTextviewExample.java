package agrawal.snehal.allwidgetexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EdittextTextviewExample extends AppCompatActivity {
    EditText etsecretecode;
    Button bcheck;
    TextView tvreplica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edittext_textview_example);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        etsecretecode = (EditText) findViewById(R.id.etsecretecode);
        bcheck = (Button) findViewById(R.id.bcheck);
        tvreplica = (TextView) findViewById(R.id.tvreplica);
        bcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkinput(etsecretecode)) {
                    String str = etsecretecode.getText().toString().trim();
                    if (str.equalsIgnoreCase("android")) {
                        Toast.makeText(EdittextTextviewExample.this, "Correct", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(EdittextTextviewExample.this, "Sorry wrong code", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        etsecretecode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = etsecretecode.getText().toString();
                tvreplica.setText(str);
            }

            @Override
            public void afterTextChanged(Editable s) {
                etsecretecode.setError(null);
            }
        });
    }

    private boolean checkinput(EditText et) {
        if (TextUtils.isEmpty(et.getText().toString().trim())) {
            et.setError("This field is required");
            return false;
        } else {
            return true;
        }
    }
}
