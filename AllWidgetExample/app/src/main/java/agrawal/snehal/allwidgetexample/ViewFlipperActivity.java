package agrawal.snehal.allwidgetexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ViewFlipper;

//ViewFlipper supports more than two views, ViewSwitcher only supports 2.
public class ViewFlipperActivity extends AppCompatActivity {

    private ViewFlipper simpleViewFlipper;
    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewflipper);
        btnNext = (Button) findViewById(R.id.buttonNext);
        simpleViewFlipper = (ViewFlipper) findViewById(R.id.simpleViewFlipper); // get the reference of ViewFlipper
        Animation in = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
        simpleViewFlipper.setInAnimation(in);
        simpleViewFlipper.setOutAnimation(out);
        btnNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                simpleViewFlipper.showNext();
            }
        });
    }
}