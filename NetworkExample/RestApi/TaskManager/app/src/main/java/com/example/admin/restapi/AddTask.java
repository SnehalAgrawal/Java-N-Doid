package com.example.admin.restapi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.admin.restapi.support.InternetAccess;
import com.example.admin.restapi.support.Util;

public class AddTask extends AppCompatActivity {


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        final EditText ettask = (EditText) findViewById(R.id.ettask);
        Button baddtask = (Button) findViewById(R.id.baddtask);
        if (getIntent().getExtras() != null) {
            final String task = getIntent().getExtras().getString("task");
            final String status = getIntent().getExtras().getString("status");
            final String id = getIntent().getExtras().getString("id");
            ettask.setText(task);
            baddtask.setText("Update Task");
            baddtask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isEmpty(ettask))
                        InternetAccess.upatetask(AddTask.this, InternetAccess.URL_TASK + "/" + id, ettask.getText().toString().trim(), status);
                }
            });
        } else {
            baddtask.setText("Add Task");
            baddtask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isEmpty(ettask))
                        InternetAccess.addtask(AddTask.this, InternetAccess.URL_TASK, ettask.getText().toString().trim());
                }
            });
        }
    }

    private boolean isEmpty(EditText editText) {
        if (TextUtils.isEmpty(editText.getText().toString().trim()) || editText.getText().toString().trim().length() == 0) {
            editText.setError("Please fill detail");
            return false;
        } else {
            return true;
        }
    }


}
