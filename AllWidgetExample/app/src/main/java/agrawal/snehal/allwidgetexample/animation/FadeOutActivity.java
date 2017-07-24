package agrawal.snehal.allwidgetexample.animation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import agrawal.snehal.allwidgetexample.R;

public class FadeOutActivity extends AppCompatActivity {

    TextView txtMessage;
    Button btnStart;
    Animation animFadeOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animation_fadeout);
        txtMessage = (TextView) findViewById(R.id.txtMessage);
        btnStart = (Button) findViewById(R.id.btnStart);
        animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
        animFadeOut.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Toast.makeText(getApplicationContext(), "Animation start", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (animation == animFadeOut) {
                    Toast.makeText(getApplicationContext(), "Animation Stopped", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtMessage.startAnimation(animFadeOut);
            }
        });

    }

}