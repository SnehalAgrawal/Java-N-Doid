package agrawal.snehal.allwidgetexample;

import android.app.TimePickerDialog;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;


public class TimePickerActivity extends AppCompatActivity {
    private TimePicker timePicker1;
    private TextView time;
    Button bdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timepicker);
        timePicker1 = (TimePicker) findViewById(R.id.timePicker1);
        time = (TextView) findViewById(R.id.textView1);
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        showTime(hour, min);

        bdialog = (Button) findViewById(R.id.bdialog);
        bdialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(TimePickerActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        showTime(selectedHour, selectedMinute);
                    }
                }, hour, minute, false);    //true for 24 hr formate and false for 12 hr
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
    }

    public void setTime(View view) {
        int hour, min;
        if (Build.VERSION.SDK_INT >= 23)
            hour = timePicker1.getHour();
        else
            hour = timePicker1.getCurrentHour();
        if (Build.VERSION.SDK_INT >= 23)
            min = timePicker1.getHour();
        else
            min = timePicker1.getCurrentMinute();
        showTime(hour, min);
    }

    public void showTime(int hour, int min) {
        String format;
        if (hour == 0) {
            hour += 12;
            format = "AM";
        } else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }
        time.setText(new StringBuilder().append(hour).append(" : ").append(min).append(" ").append(format));
    }
}