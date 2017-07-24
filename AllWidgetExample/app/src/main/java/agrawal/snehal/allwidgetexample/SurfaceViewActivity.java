package agrawal.snehal.allwidgetexample;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class SurfaceViewActivity extends AppCompatActivity implements SurfaceHolder.Callback {
    TextView testView;
    Camera camera;
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;
    PictureCallback rawCallback;
    ShutterCallback shutterCallback;
    PictureCallback jpegCallback;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surfaceview);
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        jpegCallback = new PictureCallback() {
            @SuppressLint("DefaultLocale")
            public void onPictureTaken(byte[] data, Camera camera) {
                FileOutputStream outStream = null;
                try {
                    outStream = new FileOutputStream(String.format("/sdcard/%d.jpg", System.currentTimeMillis()));
                    outStream.write(data);
                    outStream.close();
                    Log.d("Log", "onPictureTaken - wrote bytes: " + data.length);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(), "Picture Saved", Toast.LENGTH_LONG).show();
                refreshCamera();
            }
        };
    }

    public void captureImage(View v) throws IOException {
        camera.takePicture(null, null, jpegCallback);
    }

    public void refreshCamera() {
        if (surfaceHolder.getSurface() == null) {
            return;
        }
        try {
            camera.stopPreview();
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        refreshCamera();
    }

    public void surfaceCreated(SurfaceHolder holder) {
        try {
            camera = Camera.open();
        } catch (RuntimeException e) {
            e.printStackTrace();
            return;
        }
        Camera.Parameters param;
        param = camera.getParameters();
        param.setPreviewSize(352, 288);
        camera.setParameters(param);
        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        camera.stopPreview();
        camera.release();
        camera = null;
    }

}