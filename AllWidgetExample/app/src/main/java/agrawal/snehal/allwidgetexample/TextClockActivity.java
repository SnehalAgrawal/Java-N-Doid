package agrawal.snehal.allwidgetexample;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextClock;
import android.widget.Toast;

public class TextClockActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textclock);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            TextClock textClock = (TextClock) findViewById(R.id.textClock);
            Toast.makeText(this, "" + textClock.getFormat12Hour(), Toast.LENGTH_SHORT).show();
        }
    }
}