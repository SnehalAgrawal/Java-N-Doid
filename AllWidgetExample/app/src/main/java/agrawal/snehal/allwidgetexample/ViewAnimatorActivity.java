package agrawal.snehal.allwidgetexample;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewAnimator;

public class ViewAnimatorActivity extends AppCompatActivity {

    private ViewAnimator simpleViewAnimator;
    Button btnNext;
    int[] images = {R.drawable.jellybean, R.drawable.kitkat, R.drawable.lollipop, R.drawable.marshmellow, R.drawable.nougat};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewanimator);
        btnNext = (Button) findViewById(R.id.buttonNext);
        simpleViewAnimator = (ViewAnimator) findViewById(R.id.simpleViewAnimator); // get the reference of ViewAnimator
        for (int i = 0; i < images.length; i++) {
            ImageView imageView = new ImageView(getApplicationContext()); // create a new object  for ImageView
            imageView.setImageResource(images[i]); // set image resource for ImageView
            simpleViewAnimator.addView(imageView); // add child view in ViewAnimator
        }
        Animation in = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
        simpleViewAnimator.setInAnimation(in);
        simpleViewAnimator.setOutAnimation(out);
        simpleViewAnimator.setAnimateFirstView(false); // set false value for setAnimateFirstView
        btnNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                simpleViewAnimator.showNext();
            }
        });
    }
}
