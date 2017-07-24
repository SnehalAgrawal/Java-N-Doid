package agrawal.snehal.allwidgetexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import agrawal.snehal.allwidgetexample.animation.BlinkActivity;
import agrawal.snehal.allwidgetexample.animation.BounceActivity;
import agrawal.snehal.allwidgetexample.animation.CrossfadeActivity;
import agrawal.snehal.allwidgetexample.animation.FadeInActivity;
import agrawal.snehal.allwidgetexample.animation.FadeOutActivity;
import agrawal.snehal.allwidgetexample.animation.MoveActivity;
import agrawal.snehal.allwidgetexample.animation.RotateActivity;
import agrawal.snehal.allwidgetexample.animation.SequentialActivity;
import agrawal.snehal.allwidgetexample.animation.SlideDownActivity;
import agrawal.snehal.allwidgetexample.animation.SlideUpActivity;
import agrawal.snehal.allwidgetexample.animation.TogetherActivity;
import agrawal.snehal.allwidgetexample.animation.ZoomInActivity;
import agrawal.snehal.allwidgetexample.animation.ZoomOutActivity;


public class AnimationActivity extends AppCompatActivity {

    Button btnFadeIn, btnFadeOut, btnCrossFade, btnBlink, btnZoomIn,
            btnZoomOut, btnRotate, btnMove, btnSlideUp, btnSlideDown,
            btnBounce, btnSequential, btnTogether;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        btnFadeIn = (Button) findViewById(R.id.btnFadeIn);
        btnFadeOut = (Button) findViewById(R.id.btnFadeOut);
        btnCrossFade = (Button) findViewById(R.id.btnCrossFade);
        btnBlink = (Button) findViewById(R.id.btnBlink);
        btnZoomIn = (Button) findViewById(R.id.btnZoomIn);
        btnZoomOut = (Button) findViewById(R.id.btnZoomOut);
        btnRotate = (Button) findViewById(R.id.btnRotate);
        btnMove = (Button) findViewById(R.id.btnMove);
        btnSlideUp = (Button) findViewById(R.id.btnSlideUp);
        btnSlideDown = (Button) findViewById(R.id.btnSlideDown);
        btnBounce = (Button) findViewById(R.id.btnBounce);
        btnSequential = (Button) findViewById(R.id.btnSequential);
        btnTogether = (Button) findViewById(R.id.btnTogether);
        // fade in
        btnFadeIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AnimationActivity.this, FadeInActivity.class);
                startActivity(i);
            }
        });

        // fade out
        btnFadeOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AnimationActivity.this, FadeOutActivity.class);
                startActivity(i);
            }
        });

        // cross fade
        btnCrossFade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AnimationActivity.this, CrossfadeActivity.class);
                startActivity(i);
            }
        });

        // blink
        btnBlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AnimationActivity.this, BlinkActivity.class);
                startActivity(i);
            }
        });

        // Zoom In
        btnZoomIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AnimationActivity.this, ZoomInActivity.class);
                startActivity(i);
            }
        });

        // Zoom Out
        btnZoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AnimationActivity.this, ZoomOutActivity.class);
                startActivity(i);
            }
        });

        // Rotate
        btnRotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AnimationActivity.this, RotateActivity.class);
                startActivity(i);
            }
        });

        // Move
        btnMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AnimationActivity.this, MoveActivity.class);
                startActivity(i);
            }
        });

        // Slide Up
        btnSlideUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AnimationActivity.this, SlideUpActivity.class);
                startActivity(i);
            }
        });

        // Slide Down
        btnSlideDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AnimationActivity.this, SlideDownActivity.class);
                startActivity(i);
            }
        });

        // Slide Down
        btnBounce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AnimationActivity.this, BounceActivity.class);
                startActivity(i);
            }
        });

        // Sequential
        btnSequential.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AnimationActivity.this, SequentialActivity.class);
                startActivity(i);
            }
        });


        // Together
        btnTogether.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AnimationActivity.this, TogetherActivity.class);
                startActivity(i);
            }
        });
    }
}
