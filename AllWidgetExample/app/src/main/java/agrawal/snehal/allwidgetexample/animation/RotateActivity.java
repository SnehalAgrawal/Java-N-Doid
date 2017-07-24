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

public class RotateActivity extends AppCompatActivity implements AnimationListener {

    ImageView imgLogo;
    Button btnStart;
    Animation animRotate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animation_rotate);
        imgLogo = (ImageView) findViewById(R.id.imgLogo);
        btnStart = (Button) findViewById(R.id.btnStart);
        animRotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        animRotate.setAnimationListener(this);
        btnStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                imgLogo.setVisibility(View.VISIBLE);
                imgLogo.startAnimation(animRotate);
            }
        });
    }

    @Override
    public void onAnimationEnd(Animation animation) {
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onAnimationStart(Animation animation) {
        // TODO Auto-generated method stub
    }

}