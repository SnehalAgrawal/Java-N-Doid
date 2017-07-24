package agrawal.snehal.allwidgetexample;

import android.app.ActionBar.LayoutParams;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewSwitcher.ViewFactory;

public class ImageSwitcherActivity extends AppCompatActivity {
    private ImageSwitcher sw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageswitcher);
        Button b1 = (Button) findViewById(R.id.button);
        Button b2 = (Button) findViewById(R.id.button2);
        sw = (ImageSwitcher) findViewById(R.id.imageSwitcher);
        sw.setFactory(new ViewFactory() {
            @Override
            public View makeView() {
                ImageView myView = new ImageView(getApplicationContext());
                myView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                myView.setLayoutParams(new ImageSwitcher.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                return myView;
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "previous Image", Toast.LENGTH_LONG).show();
                sw.setImageResource(R.drawable.jellybean);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Next Image", Toast.LENGTH_LONG).show();
                sw.setImageResource(R.drawable.kitkat);
            }
        });
    }
}
