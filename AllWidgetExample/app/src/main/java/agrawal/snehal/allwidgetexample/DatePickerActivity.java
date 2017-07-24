package agrawal.snehal.allwidgetexample;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Admin on 7/18/2017.
 */

public class DatePickerActivity extends AppCompatActivity {
    DatePicker picker;
    Button bview, bdialog;
    TextView tvdate;
    private int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datepicker);
        tvdate = (TextView) findViewById(R.id.tvdate);
        picker = (DatePicker) findViewById(R.id.datePicker1);
        bview = (Button) findViewById(R.id.bview);
        bdialog = (Button) findViewById(R.id.bdialog);
        tvdate.setText(getCurrentDate());
        bview.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                tvdate.setText(getCurrentDate());
            }
        });


        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month + 1, day);
        bdialog.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(DatePickerActivity.this, new
                        DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
                                showDate(arg1, arg2 + 1, arg3);
                            }
                        }, year, month, day);
                mDatePicker.setTitle("Select Date");
                mDatePicker.show();
            }
        });
    }

    public String getCurrentDate() {
        return "" + picker.getDayOfMonth() + "/" + (picker.getMonth() + 1) + "/" + picker.getYear();
    }

    private void showDate(int year, int month, int day) {
        tvdate.setText(new StringBuilder().append(day).append("/").append(month).append("/").append(year));
    }
}
