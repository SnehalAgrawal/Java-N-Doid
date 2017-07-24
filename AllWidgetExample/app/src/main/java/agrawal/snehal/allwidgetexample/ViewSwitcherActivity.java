package agrawal.snehal.allwidgetexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ViewSwitcher;

//ViewFlipper supports more than two views, ViewSwitcher only supports 2.
public class ViewSwitcherActivity extends AppCompatActivity {

    private ViewSwitcher simpleViewSwitcher;
    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_viewswitcher);
        btnNext = (Button) findViewById(R.id.buttonNext);
        simpleViewSwitcher = (ViewSwitcher) findViewById(R.id.simpleViewSwitcher);
        Animation in = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
        simpleViewSwitcher.setInAnimation(in);
        simpleViewSwitcher.setOutAnimation(out);
        btnNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                simpleViewSwitcher.showNext();
            }
        });
    }
}