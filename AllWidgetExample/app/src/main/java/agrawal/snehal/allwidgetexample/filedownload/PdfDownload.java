package agrawal.snehal.allwidgetexample.filedownload;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import agrawal.snehal.allwidgetexample.R;
import agrawal.snehal.allwidgetexample.datahandler.DataHandlerDownloadManager;

public class PdfDownload extends Activity {
    private static final String TAG = "Pdf Download";
    TextView tv_loading;
    LinearLayout ll_loading;
    ProgressBar progress_loading;

    int downloadedSize = 0;
    int totalsize;
    String download_file_url;
    String download_type;
    String dowanload_date;
    float per = 0.0F;
    private static final int PERMISSION_REQUEST_CODE = 1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv_loading = new TextView(this);
        progress_loading = new ProgressBar(this);
        ll_loading = new LinearLayout(this);
        ll_loading.setOrientation(LinearLayout.VERTICAL);
        ll_loading.addView(progress_loading);
        ll_loading.addView(tv_loading);
        ll_loading.setGravity(17);
        tv_loading.setGravity(17);
        tv_loading.setTypeface(null, 1);
        setContentView(ll_loading);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            download_file_url = extras.getString("download_link");
            dowanload_date = extras.getString("dowanload_date");
        }
        Log.d("here link pdf", "onClick: " + download_file_url);
        if ((download_file_url.contains(".3gp")) || (download_file_url.contains(".mpg")) || (download_file_url.contains(".mpeg")) || (download_file_url.contains(".mpe")) || (download_file_url.contains(".mp4")) || (download_file_url.contains(".avi")) || (download_file_url.contains(".rtf")) || (download_file_url.contains(".wav")) || (download_file_url.contains(".mp3")) || (download_file_url.contains(".gif"))) {
            setTextError("Failed to download file. File type does not supported.", Color.RED);
            progress_loading.setVisibility(View.GONE);
        } else if (!checkPermission()) {
            requestPermission();
        } else {
            downloadPDF(this);
        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE");
        return result == 0;
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.WRITE_EXTERNAL_STORAGE")) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, PERMISSION_REQUEST_CODE);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, PERMISSION_REQUEST_CODE);
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if ((grantResults.length > 0) && (grantResults[0] == 0)) {
                    downloadPDF(this);
                } else {
                    finish();
                }
                break;
        }
    }

    void downloadPDF(final Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                downloadFile(context, download_file_url);
            }
        }).start();
    }

    File downloadFile(Context context, String dwnload_file_path) {
        File file = null;
        try {
            URL url = new URL(dwnload_file_path);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extStorageDirectory, getString(R.string.app_name));
            if (folder.mkdir()) {
                Log.d(TAG, "New Folder created");
            } else {
                Log.d(TAG, "No new folder");
            }
            file = new File(folder, getoutputfilename());
            Log.d(TAG, "file path: " + file);
            FileOutputStream fileOutput = new FileOutputStream(file);

            int status = urlConnection.getResponseCode();
            Log.d(TAG, "" + status);
            if (status != 200) {
                setTextError("No File Exist on server ...", -65536);
            } else {
                InputStream inputStream = urlConnection.getInputStream();
                totalsize = urlConnection.getContentLength();
                setText("Starting PDF download...");
                Log.d(TAG, "Starting PDF download..." + totalsize);
                byte[] buffer = new byte[1048576];
                int bufferLength;
                while ((bufferLength = inputStream.read(buffer)) > 0) {
                    fileOutput.write(buffer, 0, bufferLength);
                    downloadedSize += bufferLength;
                    per = (downloadedSize / totalsize * 100.0F);
                    setText("Total PDF File size  : " + totalsize / 1024 + " KB\n\nDownloading PDF " + (int) per + "% complete");
                    Log.d(TAG, "Total PDF File size  : " + totalsize / 1024 + " KB\n\nDownloading PDF " + (int) per + "% complete");
                }
                fileOutput.close();
                setText("Download Complete.Please wait.. ");
                setlocalpath(this, download_file_url, dowanload_date, String.valueOf(file));
                Log.d(TAG, "onClick: Exist " + download_file_url);
                try {
                    finish();
                    FileOpen.openFile(context, file);
                } catch (IOException e) {
                    deletelocallink(context, String.valueOf(file));
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            setTextError("Failed to download image. Please check your internet connection or try again.", Color.RED);
        }
        return file;
    }

    void setTextError(final String message, final int color) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv_loading.setTextColor(color);
                tv_loading.setText(message);
            }
        });
    }

    void setText(final String txt) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv_loading.setText(txt);
            }
        });
    }

    public String getoutputfilename() {
        String destination_file_name = "Downloadedfile";
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String currentDateandTime = sdf.format(new Date());
        if (download_file_url.contains(".doc")) {
            destination_file_name = destination_file_name + "_" + currentDateandTime + ".doc";
        } else if (download_file_url.contains(".JPG")) {
            destination_file_name = destination_file_name + "_" + currentDateandTime + ".JPG";
        } else if (download_file_url.contains(".docx")) {
            destination_file_name = destination_file_name + "_" + currentDateandTime + ".docx";
        } else if (download_file_url.contains(".pdf")) {
            destination_file_name = destination_file_name + "_" + currentDateandTime + ".pdf";
        } else if (download_file_url.contains(".ppt")) {
            destination_file_name = destination_file_name + "_" + currentDateandTime + ".ppt";
        } else if (download_file_url.contains(".pptx")) {
            destination_file_name = destination_file_name + "_" + currentDateandTime + ".pptx";
        } else if (download_file_url.contains(".xls")) {
            destination_file_name = destination_file_name + "_" + currentDateandTime + ".xls";
        } else if (download_file_url.contains(".xlsx")) {
            destination_file_name = destination_file_name + "_" + currentDateandTime + ".xlsx";
        } else if (download_file_url.contains(".jpg")) {
            destination_file_name = destination_file_name + "_" + currentDateandTime + ".jpg";
        } else if (download_file_url.contains(".jpeg")) {
            destination_file_name = destination_file_name + "_" + currentDateandTime + ".jpeg";
        } else if (download_file_url.contains(".png")) {
            destination_file_name = destination_file_name + "_" + currentDateandTime + ".png";
        } else if (download_file_url.contains(".txt")) {
            destination_file_name = destination_file_name + "_" + currentDateandTime + ".txt";
        }
        return destination_file_name;
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    void setlocalpath(Context context, String download_file_url, String dowanload_date, String file) {
        DataHandlerDownloadManager handler_download_manager = new DataHandlerDownloadManager(context);
        handler_download_manager.open();
        handler_download_manager.insertData(download_file_url, dowanload_date, file);
        handler_download_manager.close();
    }

    void deletelocallink(Context context, String local) {
        DataHandlerDownloadManager handler_download_manager = new DataHandlerDownloadManager(context);
        handler_download_manager.open();
        handler_download_manager.deleteRow(local);
        handler_download_manager.close();
    }

}
