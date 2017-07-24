package agrawal.snehal.allwidgetexample;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import agrawal.snehal.allwidgetexample.filepicker.FileChooser;
import agrawal.snehal.allwidgetexample.filepicker.PhotoSelectGallery;


public class FilePickerActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = "FilepickerActivity";
    public TextView tvattachment;
    ImageButton battachment;
    static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 123;
    static final int CHOOSE_IMAGE_ACTIVITY_REQUEST_CODE = 234;
    private static final String IMAGE_DIRECTORY_NAME = "FilepickerActivity";
    private Uri fileUri;
    CharSequence[] items = new CharSequence[]{"Take Photo", "Choose From Files", "Choose From Gallery", "Cancel"};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filepicker);
        tvattachment = ((TextView) findViewById(R.id.tvattachment));
        battachment = ((ImageButton) findViewById(R.id.battachment));
        battachment.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.battachment:
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
                builder.setTitle("Attach Photo!");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals("Take Photo"))
                            cameraIntent();
                        else if (items[item].equals("Choose From Files")) {
                            FileChooser f = new FileChooser(FilePickerActivity.this).setFileListener(new FileChooser.FileSelectedListener() {
                                @Override
                                public void fileSelected(File file) {
                                    showfile(Uri.fromFile(file));
                                }
                            });
                            f.showDialog();
                        } else if (items[item].equals("Choose From Gallery"))
                            startActivityForResult(new Intent(FilePickerActivity.this, PhotoSelectGallery.class), CHOOSE_IMAGE_ACTIVITY_REQUEST_CODE);
                        else if (items[item].equals("Cancel"))
                            dialog.dismiss();
                    }
                });
                builder.show();
                break;
        }
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

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = Uri.fromFile(getOutputMediaFile());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    private static File getOutputMediaFile() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), IMAGE_DIRECTORY_NAME);
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create " + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
    }

    @SuppressLint({"SetTextI18n"})
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            showfile(fileUri);
        } else if (requestCode == CHOOSE_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            showfile(Uri.fromFile(new File(data.getStringExtra("filelink"))));
        } else
            Toast.makeText(getApplicationContext(), "File Not Selected", Toast.LENGTH_LONG).show();
    }

    @SuppressLint("SetTextI18n")
    private void showfile(Uri uri) {
        Log.d(TAG, "showfile: uri " + uri);
        String path = uri.getPath();
        Log.d(TAG, "showfile: path " + path);
        if (!new File(path).exists())
            Toast.makeText(getApplicationContext(), "File not exist..", Toast.LENGTH_LONG).show();
        else
            tvattachment.setText("" + path);
    }
}
