package agrawal.snehal.allwidgetexample;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.Toast;

public class CalenderViewActivity extends AppCompatActivity {

    CalendarView simpleCalendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calenderview);
        simpleCalendarView = (CalendarView) findViewById(R.id.simpleCalendarView);

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            simpleCalendarView.setFocusedMonthDateColor(Color.RED); // set the red color for the dates of  focused month
            simpleCalendarView.setUnfocusedMonthDateColor(Color.BLUE); // set the yellow color for the dates of an unfocused month
            simpleCalendarView.setSelectedWeekBackgroundColor(Color.RED); // red color for the selected week's background
            simpleCalendarView.setWeekSeparatorLineColor(Color.GREEN); // green color for the week separator line
        }*/

        simpleCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Toast.makeText(getApplicationContext(), dayOfMonth + "/" + month + "/" + year, Toast.LENGTH_LONG).show();
            }
        });
    }


}