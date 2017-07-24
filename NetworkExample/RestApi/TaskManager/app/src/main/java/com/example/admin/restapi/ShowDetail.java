package com.example.admin.restapi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.restapi.support.FileChooser;
import com.example.admin.restapi.support.InternetAccess;
import com.example.admin.restapi.support.Util;

import java.io.File;

public class ShowDetail extends AppCompatActivity {
    private ListView lvtask;
    private TextView tvattach;
    String selectedFilePath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showdetail);
        TextView tvname = (TextView) findViewById(R.id.tvname);
        TextView tvemail = (TextView) findViewById(R.id.tvemail);
        TextView tvapi = (TextView) findViewById(R.id.tvapi);
        TextView tvcreatedat = (TextView) findViewById(R.id.tvcreatedat);
        tvattach = (TextView) findViewById(R.id.tvattach);
        lvtask = (ListView) findViewById(R.id.lvtask);
        tvname.setText(Util.getname(ShowDetail.this));
        tvemail.setText(Util.getemail(ShowDetail.this));
        tvapi.setText(Util.getauthkey(ShowDetail.this));
        tvcreatedat.setText(Util.getcreatedat(ShowDetail.this));
        findViewById(R.id.bshowtask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InternetAccess.getalltask(ShowDetail.this, InternetAccess.URL_TASK, lvtask);
            }
        });
        findViewById(R.id.baddtask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShowDetail.this, AddTask.class));
            }
        });
        tvattach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (selectedFilePath != null)
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            InternetAccess.uploadFile(ShowDetail.this, selectedFilePath, InternetAccess.URL_FILE_UPLOAD, tvattach);
                        }
                    }).start();
                else
                    Toast.makeText(ShowDetail.this, "Sorry no file", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.blogout) {
            Util.logout(ShowDetail.this);
            finish();
        } else if (item.getItemId() == R.id.battach) {
            FileChooser f = new FileChooser(ShowDetail.this).setFileListener(new FileChooser.FileSelectedListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void fileSelected(File file) {
                    selectedFilePath = file.getPath();
                    tvattach.setText("" + selectedFilePath);
                }
            });
            f.showDialog();
        } else if (item.getItemId() == R.id.bview) {
            startActivity(new Intent(ShowDetail.this, ViewImage.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Util.exit(ShowDetail.this);
    }
}
