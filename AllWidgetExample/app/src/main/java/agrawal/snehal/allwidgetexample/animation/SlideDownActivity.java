package agrawal.snehal.allwidgetexample.animation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import agrawal.snehal.allwidgetexample.R;

public class SlideDownActivity extends AppCompatActivity implements AnimationListener {

    ImageView imgPoster;
    Button btnStart;
    Animation animSideDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animation_slide_down);
        imgPoster = (ImageView) findViewById(R.id.imgLogo);
        btnStart = (Button) findViewById(R.id.btnStart);
        animSideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
        animSideDown.setAnimationListener(this);
        btnStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                imgPoster.setVisibility(View.VISIBLE);
                imgPoster.startAnimation(animSideDown);
            }
        });
    }

    @Override
    public void onAnimationEnd(Animation animation) {
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
    }

    @Override
    public void onAnimationStart(Animation animation) {
    }
}
