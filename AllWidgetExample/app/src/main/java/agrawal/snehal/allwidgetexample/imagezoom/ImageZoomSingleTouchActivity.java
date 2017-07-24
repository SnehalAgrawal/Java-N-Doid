package agrawal.snehal.allwidgetexample.imagezoom;

import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.text.DecimalFormat;

import agrawal.snehal.allwidgetexample.R;

public class ImageZoomSingleTouchActivity extends AppCompatActivity {
    private TouchImageView image;
    private DecimalFormat df;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_zoom_single_touch);
        df = new DecimalFormat("#.##");
        image = ((TouchImageView) findViewById(R.id.img));

        image.setOnTouchImageViewListener(new TouchImageView.OnTouchImageViewListener() {
            public void onMove() {
                PointF point = image.getScrollPosition();
                RectF rect = image.getZoomedRect();
                float currentZoom = image.getCurrentZoom();
                boolean isZoomed = image.isZoomed();
            }
        });
    }
}
