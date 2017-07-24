package agrawal.snehal.allwidgetexample;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class CameraAPI extends Activity {
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    private static final String IMAGE_DIRECTORY_NAME = "Hello Camera";
    private Uri fileUri;
    private ImageView imgPreview;
    private VideoView videoPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cameraapi);
        imgPreview = (ImageView) findViewById(R.id.imgPreview);
        videoPreview = (VideoView) findViewById(R.id.videoPreview);
        Button btnCapturePicture = (Button) findViewById(R.id.btnCapturePicture);
        Button btnRecordVideo = (Button) findViewById(R.id.btnRecordVideo);
        btnCapturePicture.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                captureImage();
            }
        });
        btnRecordVideo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                recordVideo();
            }
        });
        if (!isDeviceSupportCamera()) {
            Toast.makeText(getApplicationContext(), "Sorry! Your device doesn't support camera", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private boolean isDeviceSupportCamera() {
        return getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("file_uri", fileUri);
        Log.d("onSaveInstanceState", "onSaveInstanceState: " + fileUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        fileUri = savedInstanceState.getParcelable("file_uri");
        Log.d("onRestoreInstanceState", "onSaveInstanceState: " + fileUri);
    }

    private void recordVideo() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file
        startActivityForResult(intent, CAMERA_CAPTURE_VIDEO_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                previewCapturedImage();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "User cancelled image capture", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Sorry! Failed to capture image", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == CAMERA_CAPTURE_VIDEO_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                previewVideo();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "User cancelled video recording", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Sorry! Failed to record video", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void previewCapturedImage() {
        try {
            videoPreview.setVisibility(View.GONE);
            imgPreview.setVisibility(View.VISIBLE);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;
            final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(), options);
            imgPreview.setImageBitmap(bitmap);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }


    private void previewVideo() {
        try {
            imgPreview.setVisibility(View.GONE);
            videoPreview.setVisibility(View.VISIBLE);
            videoPreview.setVideoPath(fileUri.getPath());
            videoPreview.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private static File getOutputMediaFile(int type) {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), IMAGE_DIRECTORY_NAME);
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create " + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }
        return mediaFile;
    }
}
