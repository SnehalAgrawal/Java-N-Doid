package com.example.admin.volleyexample;

import com.example.admin.volleyexample.app.AppController;
import com.example.admin.volleyexample.utils.Const;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

public class JsonRequestActivity extends AppCompatActivity implements OnClickListener {

    private String TAG = JsonRequestActivity.class.getSimpleName();
    private TextView msgResponse;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);
        Button btnJsonObj = (Button) findViewById(R.id.btnJsonObj);
        Button btnJsonArray = (Button) findViewById(R.id.btnJsonArray);
        msgResponse = (TextView) findViewById(R.id.msgResponse);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        btnJsonObj.setOnClickListener(this);
        btnJsonArray.setOnClickListener(this);
    }

    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }

    private void makeJsonObjReq() {
        showProgressDialog();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET,
                Const.URL_JSON_OBJECT, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        msgResponse.setText(response.toString());
                        hideProgressDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hideProgressDialog();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name", "Androidhive");
                params.put("email", "abc@androidhive.info");
                params.put("pass", "password123");
                return params;
            }
        };
        String tag_json_obj = "jobj_req";
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }

    private void makeJsonArryReq() {
        showProgressDialog();
        JsonArrayRequest req = new JsonArrayRequest(Const.URL_JSON_ARRAY,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        msgResponse.setText(response.toString());
                        hideProgressDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hideProgressDialog();
            }
        });
        String tag_json_arry = "jarray_req";
        AppController.getInstance().addToRequestQueue(req, tag_json_arry);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnJsonObj:
                makeJsonObjReq();
                break;
            case R.id.btnJsonArray:
                makeJsonArryReq();
                break;
        }
    }
}
