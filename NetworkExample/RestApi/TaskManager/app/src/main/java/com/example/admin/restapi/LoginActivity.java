package com.example.admin.restapi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.admin.restapi.support.InternetAccess;
import com.example.admin.restapi.support.Util;

public class LoginActivity extends AppCompatActivity {
    private EditText etpassword, etemail;
    @SuppressLint("StaticFieldLeak")
    public static Activity fa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        fa = this;
        if (Util.getauthkey(LoginActivity.this).trim().length() == 0) {
            etemail = (EditText) findViewById(R.id.etemail);
            etpassword = (EditText) findViewById(R.id.etpassword);
            findViewById(R.id.blogin).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isEmpty(etemail))
                        if (isEmpty(etpassword))
                            InternetAccess.login(LoginActivity.this, InternetAccess.URL_LOGIN, etemail.getText().toString().trim(), etpassword.getText().toString().trim());
                }
            });

            findViewById(R.id.tvregister).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                }
            });
        } else {
            startActivity(new Intent(LoginActivity.this, ShowDetail.class));
            finish();
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

    @Override
    public void onBackPressed() {
        //  super.onBackPressed();
        Util.exit(LoginActivity.this);
    }
    /*Instead of start developing a fresh REST framework from scratch, it is better go with a already proven framework. Then I came across Slim framework and selected it for the following reasons.
     *  1. It is very light weight, clean and a beginner can easily understand the framework.
     *  2. Supports all HTTP methods GET, POST, PUT and DELETE which are necessary for a REST API.
     *  3. More importantly it provides a middle layer architecture which will be useful to filter the requests. In our case we can use it for verifying the API Key.
     *  Other framework for the development in different languages.
     *  Ruby Sinatra(micro framework)/rails
     *  NodeJS ExpressJS/Loopback/Restify
     *  PHP Slim
     *  .NET Nancy
     *  Python Django(Ful framework) /Flask(micro framework) AWS API Gateway
     *  Java on Spring
     */
}
