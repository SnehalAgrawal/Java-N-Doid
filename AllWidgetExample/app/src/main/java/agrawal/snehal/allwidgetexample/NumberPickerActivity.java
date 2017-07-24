package agrawal.snehal.allwidgetexample;

import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.NumberPicker;


class NumberPickerActivity extends AppCompatActivity {
    TextView tv;
    NumberPicker np;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numberpicker);

        tv = (TextView) findViewById(R.id.tv);
        np = (NumberPicker) findViewById(R.id.np);

        /*tv.setTextColor(Color.parseColor("#ffd32b3b"));
        np.setMinValue(0);
        np.setMaxValue(10);
        np.setWrapSelectorWheel(true);
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                tv.setText("Selected Number : " + newVal);
            }
        });*/

        tv.setTextColor(Color.parseColor("#FF2C834F"));
        final String[] values = {"Red", "Green", "Blue", "Yellow", "Magenta"};
        np.setMinValue(0);
        np.setMaxValue(values.length - 1);
        np.setDisplayedValues(values);
        np.setWrapSelectorWheel(true);
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                tv.setText("Selected value : " + values[newVal]);
            }
        });
    }
}