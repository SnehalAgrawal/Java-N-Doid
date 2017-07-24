package agrawal.snehal.allwidgetexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RadioButtonExample extends AppCompatActivity {
    private RadioGroup radioCityGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_button_example);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        radioCityGroup = (RadioGroup) findViewById(R.id.radiocity);
        Button bcheck = (Button) findViewById(R.id.bcheck);
        bcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioCityGroup.getCheckedRadioButtonId();
                RadioButton radioCityButton = (RadioButton) findViewById(selectedId);
                String var = radioCityButton.getText().toString();
                if (var.equalsIgnoreCase("Indore")) {
                    Toast.makeText(RadioButtonExample.this, "Correct answer", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RadioButtonExample.this, "Sorry wrong answer", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}