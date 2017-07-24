package agrawal.snehal.allwidgetexample;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ExternalFont extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_externalfont);
        String fontPath = "fonts/Face Your Fears.ttf";
        String fontPath1 = "fonts/DS-DIGIT.TTF";
        String fontPath2 = "fonts/CircleD_Font_by_CrazyForMusic.ttf";
        TextView txtGhost = (TextView) findViewById(R.id.ghost);
        TextView txtGhost1 = (TextView) findViewById(R.id.ghost1);
        TextView txtGhost2 = (TextView) findViewById(R.id.ghost2);
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        Typeface tf1 = Typeface.createFromAsset(getAssets(), fontPath1);
        Typeface tf2 = Typeface.createFromAsset(getAssets(), fontPath2);
        txtGhost.setTypeface(tf);
        txtGhost1.setTypeface(tf1);
        txtGhost2.setTypeface(tf2);
    }
}