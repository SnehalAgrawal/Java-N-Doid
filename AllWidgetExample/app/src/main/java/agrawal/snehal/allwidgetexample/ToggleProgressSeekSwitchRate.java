package agrawal.snehal.allwidgetexample;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

public class ToggleProgressSeekSwitchRate extends AppCompatActivity {
    ProgressBar progressBar;
    SeekBar seekBar;
    RatingBar ratingBar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toggle_progress_seek_switch_rate);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);


        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                Toast.makeText(ToggleProgressSeekSwitchRate.this, "Rating " + v, Toast.LENGTH_SHORT).show();
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Log.d("Seekbar", "onProgressChanged: " + i);
                progressBar.setProgress(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public void onToggleClicked(View view) {
        switch (view.getId()) {
            case R.id.toggleButton:
                boolean on = ((ToggleButton) view).isChecked();
                if (on) {
                    Toast.makeText(this, "Vibration on", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Vibration off", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.switch1:
                boolean status = ((Switch) view).isChecked();
                if (status) {
                    ((Switch) view).setText("on");
                } else {
                    ((Switch) view).setText("off");
                }
                break;
        }
    }

}