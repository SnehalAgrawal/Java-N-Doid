package agrawal.snehal.allwidgetexample;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class CheckBoxExample extends AppCompatActivity implements View.OnClickListener {
    CheckBox checkBox1, checkBox2, checkBox3;
    Button bchecktosee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkbox_example);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        checkBox1 = (CheckBox) findViewById(R.id.checkBox1);
        checkBox2 = (CheckBox) findViewById(R.id.checkBox2);
        checkBox3 = (CheckBox) findViewById(R.id.checkBox3);
        bchecktosee = (Button) findViewById(R.id.bchecktosee);

        checkBox1.setOnClickListener(this);
        checkBox2.setOnClickListener(this);
        checkBox3.setOnClickListener(this);

        bchecktosee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBox1.isChecked())
                    Toast.makeText(CheckBoxExample.this, checkBox1.getText(), Toast.LENGTH_SHORT).show();
                if (checkBox2.isChecked())
                    Toast.makeText(CheckBoxExample.this, checkBox1.getText(), Toast.LENGTH_SHORT).show();
                if (checkBox3.isChecked())
                    Toast.makeText(CheckBoxExample.this, checkBox1.getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void toggel(CheckBox cb) {
        if (cb.isChecked())
            cb.setBackgroundColor(ContextCompat.getColor(CheckBoxExample.this, R.color.colorAccent));
        else
            cb.setBackgroundColor(ContextCompat.getColor(CheckBoxExample.this, R.color.white));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.checkBox1:
                toggel(checkBox1);
                break;
            case R.id.checkBox2:
                toggel(checkBox2);
                break;
            case R.id.checkBox3:
                toggel(checkBox3);
                break;
        }
    }
}
