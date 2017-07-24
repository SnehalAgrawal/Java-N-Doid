package agrawal.snehal.allwidgetexample;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import android.view.View;
import android.widget.FrameLayout;

import java.io.IOException;

public class TaxtureViewActivity extends AppCompatActivity implements SurfaceTextureListener {
    private TextureView myTexture;
    private Camera mCamera;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taxtureview);
        myTexture = new TextureView(TaxtureViewActivity.this);
        myTexture.setSurfaceTextureListener(TaxtureViewActivity.this);
        setContentView(myTexture);
    }


    @SuppressLint("NewApi")
    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture arg0, int arg1, int arg2) {
        mCamera = Camera.open();
        Camera.Size previewSize = mCamera.getParameters().getPreviewSize();
        myTexture.setLayoutParams(new FrameLayout.LayoutParams(previewSize.width, previewSize.height, Gravity.CENTER));
        try {
            mCamera.setPreviewTexture(arg0);
        } catch (IOException t) {
            t.printStackTrace();
        }
        mCamera.startPreview();
        myTexture.setAlpha(1.0f);
        myTexture.setRotation(90.0f);
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture arg0) {
        mCamera.stopPreview();
        mCamera.release();
        return true;
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture arg0) {
        // TODO Auto-generated method stub
    }
}