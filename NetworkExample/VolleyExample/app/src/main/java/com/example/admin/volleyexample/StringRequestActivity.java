package com.example.admin.volleyexample;

import com.example.admin.volleyexample.app.AppController;
import com.example.admin.volleyexample.utils.Const;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

public class StringRequestActivity extends AppCompatActivity {

    private String TAG = StringRequestActivity.class.getSimpleName();
    private TextView msgResponse;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_string);
        Button btnStringReq = (Button) findViewById(R.id.btnStringReq);
        msgResponse = (TextView) findViewById(R.id.msgResponse);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        btnStringReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeStringReq();
            }
        });
    }

    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }

    private void makeStringReq() {
        showProgressDialog();
        StringRequest strReq = new StringRequest(Method.GET, Const.URL_STRING_REQ, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response);
                msgResponse.setText(Html.fromHtml(response));
                hideProgressDialog();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hideProgressDialog();
            }
        });
        String tag_string_req = "string_req";
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}