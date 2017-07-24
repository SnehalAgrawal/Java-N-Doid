package agrawal.snehal.allwidgetexample;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import agrawal.snehal.allwidgetexample.datahandler.DataHandlerDownloadManager;
import agrawal.snehal.allwidgetexample.filedownload.AppStatus;
import agrawal.snehal.allwidgetexample.filedownload.FileOpen;
import agrawal.snehal.allwidgetexample.filedownload.PdfDownload;


public class DownloadDoc extends AppCompatActivity {
    private static final String TAG = "Doc download";
    Button bdefaultdownload, bstartdownload;
    EditText etlink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dowloaddoc);
        etlink = (EditText) findViewById(R.id.etlink);
        bstartdownload = (Button) findViewById(R.id.bstartdownload);
        bdefaultdownload = (Button) findViewById(R.id.bdefaultdownload);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        final String currentDate = sdf.format(new Date());
        bstartdownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = etlink.getText().toString().trim();
                if (str.length() > 0) startdownload(DownloadDoc.this, currentDate, str);
            }
        });
        bdefaultdownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = "http://www.pdf995.com/samples/pdf.pdf";
                startdownload(DownloadDoc.this, currentDate, str);
            }
        });
    }

    private void startdownload(Context context, String post_date, String attachment) {
        String local = getlocallink(context, post_date, attachment);
        if (new File(local).exists()) {
            Log.d(TAG, "onClick: Exist " + local);
            try {
                FileOpen.openFile(context, new File(local));
            } catch (IOException e) {
                deletelocallink(context, local);
                e.printStackTrace();
            }
        } else {
            deletelocallink(context, local);
            Log.d(TAG, "onClick: Not Exist " + local);
            downloadfile(context, "Event download..", "Start download ..?", attachment, post_date);
        }
    }

    public static String getlocallink(Context context, String date, String link) {
        String local_link = "";
        DataHandlerDownloadManager handler_download_manager = new DataHandlerDownloadManager(context);
        handler_download_manager.open();
        Cursor c = handler_download_manager.returnDataAccDateLink(date, link);
        if (c.moveToFirst()) {
            local_link = c.getString(0);
        }
        handler_download_manager.close();
        return local_link;
    }

    public static void deletelocallink(Context context, String local) {
        DataHandlerDownloadManager handler_download_manager = new DataHandlerDownloadManager(context);
        handler_download_manager.open();
        handler_download_manager.deleteRow(local);
        handler_download_manager.close();
    }

    public static void downloadfile(final Context context, String title, String msg, final String link, final String date) {
        android.support.v7.app.AlertDialog.Builder builder_timetable = new android.support.v7.app.AlertDialog.Builder(context);
        builder_timetable.setTitle(title);
        builder_timetable.setMessage(msg);
        builder_timetable.setIcon(R.mipmap.ic_launcher);
        builder_timetable.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (AppStatus.isConnected(context)) {
                    Intent i = new Intent(context, PdfDownload.class);
                    i.putExtra("download_link", link);
                    i.putExtra("dowanload_date", date);
                    context.startActivity(i);
                } else {
                    Toast.makeText(context, "Please put your device online..", Toast.LENGTH_LONG).show();
                }
            }
        });

        builder_timetable.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        android.support.v7.app.AlertDialog alert_timetable = builder_timetable.create();
        alert_timetable.show();
    }

}