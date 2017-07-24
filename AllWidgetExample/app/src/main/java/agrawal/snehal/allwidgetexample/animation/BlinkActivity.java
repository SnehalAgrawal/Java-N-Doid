package agrawal.snehal.allwidgetexample.animation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import agrawal.snehal.allwidgetexample.R;

public class BlinkActivity extends AppCompatActivity implements AnimationListener {

    TextView txtMessage;
    Button btnStart;
    Animation animBlink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animation_blink);
        txtMessage = (TextView) findViewById(R.id.txtMessage);
        btnStart = (Button) findViewById(R.id.btnStart);
        animBlink = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        animBlink.setAnimationListener(this);
        btnStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                txtMessage.setVisibility(View.VISIBLE);
                txtMessage.startAnimation(animBlink);
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