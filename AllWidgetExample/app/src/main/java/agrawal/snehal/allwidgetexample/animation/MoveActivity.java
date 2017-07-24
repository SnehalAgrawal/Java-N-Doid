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

public class MoveActivity extends AppCompatActivity implements AnimationListener {

    ImageView imgLogo;
    Button btnStart;
    Animation animMove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animation_move);
        imgLogo = (ImageView) findViewById(R.id.imgLogo);
        btnStart = (Button) findViewById(R.id.btnStart);
        animMove = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move);
        animMove.setAnimationListener(this);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgLogo.startAnimation(animMove);
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