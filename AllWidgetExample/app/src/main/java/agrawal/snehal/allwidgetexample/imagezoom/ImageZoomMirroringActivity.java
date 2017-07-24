package agrawal.snehal.allwidgetexample.imagezoom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import agrawal.snehal.allwidgetexample.R;

public class ImageZoomMirroringActivity extends AppCompatActivity {
    TouchImageView topImage;
    TouchImageView bottomImage;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_zoom_mirroring);
        topImage = ((TouchImageView) findViewById(R.id.topImage));
        bottomImage = ((TouchImageView) findViewById(R.id.bottomImage));

        topImage.setOnTouchImageViewListener(new TouchImageView.OnTouchImageViewListener() {
            public void onMove() {
                bottomImage.setZoom(topImage);
            }
        });
        bottomImage.setOnTouchImageViewListener(new TouchImageView.OnTouchImageViewListener() {
            public void onMove() {
                topImage.setZoom(bottomImage);
            }
        });
    }
}
